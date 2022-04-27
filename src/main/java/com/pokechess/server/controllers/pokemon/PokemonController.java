package com.pokechess.server.controllers.pokemon;

import com.pokechess.server.controllers.pokemon.dto.PokemonPageDTO;
import com.pokechess.server.controllers.pokemon.mapper.PokemonDTOMapper;
import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import com.pokechess.server.repositories.loader.LoaderRepository;
import com.pokechess.server.repositories.pokemon.PokemonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pokechess.server.filter.security.JwtRequestFilter.*;

@RestController
public class PokemonController {
    private final PokemonRepository pokemonRepository;
    private final LoaderRepository loaderRepository;
    private final List<String> adminUsernameList;

    public PokemonController(PokemonRepository pokemonRepository, LoaderRepository loaderRepository,
                             @Value("#{'${list.admin.username}'.split(',')}") List<String> adminUsernameList) {
        this.pokemonRepository = pokemonRepository;
        this.loaderRepository = loaderRepository;
        this.adminUsernameList = adminUsernameList;
    }

    @GetMapping(value = PAGINATED_POKEMON_END_POINT)
    public ResponseEntity<PokemonPageDTO> getPaginatedPokemon(
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "4", required = false) int size,
            @RequestParam(name = "name", required = false) String name
    ) {
        if (page < 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Page number must be greater than 0");
        }
        if (size < 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Page size must be greater than 0");
        }
        Page<Pokemon> pokemonPage = this.pokemonRepository.getPaginatedPokemon(page, size, name);
        if (pokemonPage.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(PokemonDTOMapper.mapPokemonPageToPokemonPageDTO(pokemonPage));
    }

    @PostMapping(value = LOAD_DATA_END_POINT)
    public void loadAllData(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username) {
        if (!adminUsernameList.contains(username))
            throw new ApiException(HttpStatus.UNAUTHORIZED);
        this.loaderRepository.loadAllPokemon();
        this.loaderRepository.loadAllPokemonDrawPercentage();
    }
}

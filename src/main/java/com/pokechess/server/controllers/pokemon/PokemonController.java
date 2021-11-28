package com.pokechess.server.controllers.pokemon;

import com.pokechess.server.exceptions.ApiException;
import com.pokechess.server.repositories.loader.LoaderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.pokechess.server.filter.security.JwtRequestFilter.LOAD_DATA_END_POINT;
import static com.pokechess.server.filter.security.JwtRequestFilter.REQUEST_USERNAME_ATTRIBUTE;

@RestController
public class PokemonController {
    private final LoaderRepository loaderRepository;
    private final List<String> adminUsernameList;

    public PokemonController(LoaderRepository loaderRepository, @Value("#{'${list.admin.username}'.split(',')}") List<String> adminUsernameList) {
        this.loaderRepository = loaderRepository;
        this.adminUsernameList = adminUsernameList;
    }

    @PostMapping(value = LOAD_DATA_END_POINT)
    public void loadAllData(@RequestAttribute(REQUEST_USERNAME_ATTRIBUTE) String username) {
        if (!adminUsernameList.contains(username))
            throw new ApiException(HttpStatus.UNAUTHORIZED);
        this.loaderRepository.loadAllPokemon();
    }
}

package com.pokechess.server.controllers.pokemon.mapper;

import com.pokechess.server.controllers.pokemon.dto.AttackDTO;
import com.pokechess.server.controllers.pokemon.dto.EvolutionDTO;
import com.pokechess.server.controllers.pokemon.dto.PokemonDTO;
import com.pokechess.server.controllers.pokemon.dto.PokemonPageDTO;
import com.pokechess.server.models.enumerations.Type;
import com.pokechess.server.models.globals.game.actions.Attack;
import com.pokechess.server.models.globals.game.actions.Evolution;
import com.pokechess.server.models.globals.game.cards.Pokemon;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.pokechess.server.controllers.pokemon.dto.EvolutionDTO.DEFAULT_EVOLUTION_TYPE;

public class PokemonDTOMapper {
    public static PokemonPageDTO mapPokemonPageToPokemonPageDTO(Page<Pokemon> page) {
        PokemonPageDTO dto = new PokemonPageDTO();
        dto.setItems(page.getContent().stream().map(PokemonDTOMapper::mapPokemonToPokemonDTO).collect(Collectors.toList()));
        dto.setCurrentPage(page.getNumber() + 1);
        dto.setSize(page.getNumberOfElements());
        dto.setLastPage(page.getTotalPages());
        dto.setTotal(page.getTotalElements());
        return dto;
    }

    private static PokemonDTO mapPokemonToPokemonDTO(Pokemon pokemon) {
        PokemonDTO dto = new PokemonDTO();
        dto.setPokemonId(pokemon.getPokemonId());
        dto.setName(pokemon.getName());
        dto.setGeneration(pokemon.getGeneration().name());
        dto.setLevel(pokemon.getLevel());
        dto.setLifePoint(pokemon.getLifePoint());
        dto.setBaseSpeed(pokemon.getBaseSpeed());
        dto.setSize(pokemon.getSize());
        dto.setWeight(pokemon.getWeight());
        dto.setType(pokemon.getType().getTypeName());
        dto.setType2(pokemon.getType2().getTypeName());
        dto.setOffensiveAttack(mapAttackToAttackDTO(pokemon.getOffensiveAttack()));
        dto.setDefensiveAttack(mapAttackToAttackDTO(pokemon.getDefensiveAttack()));
        dto.setWeaknesses(Type.getWeaknesses(pokemon.getType(), pokemon.getType2()).stream().map(Type::getTypeName).collect(Collectors.toList()));
        dto.setResistances(Type.getResistances(pokemon.getType(), pokemon.getType2()).stream().map(Type::getTypeName).collect(Collectors.toList()));
        List<EvolutionDTO> evolutionDTOS = pokemon.getEvolutions().stream().map(PokemonDTOMapper::mapEvolutionToEvolutionDTO).collect(Collectors.toList());
        if (!evolutionDTOS.isEmpty()) {
            dto.setEvolutions(evolutionDTOS);
        }
        return dto;
    }

    private static AttackDTO mapAttackToAttackDTO(Attack attack) {
        AttackDTO dto = new AttackDTO();
        dto.setName(attack.getName());
        dto.setDescription(attack.getDescription());
        dto.setType(attack.getType().getTypeName());
        dto.setPower(attack.getPower());
        return dto;
    }

    private static EvolutionDTO mapEvolutionToEvolutionDTO(Evolution evolution) {
        EvolutionDTO dto = new EvolutionDTO();
        dto.setEvolutionType(DEFAULT_EVOLUTION_TYPE);
        dto.setDescription(evolution.getDescription());
        dto.setPokemonId(String.format("C_%s", evolution.getPokemon().getPokemonId()));
        dto.setName(evolution.getPokemon().getName());
        return dto;
    }
}

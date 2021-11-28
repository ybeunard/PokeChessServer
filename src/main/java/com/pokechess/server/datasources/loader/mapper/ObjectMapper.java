package com.pokechess.server.datasources.loader.mapper;

import com.pokechess.server.datasources.loader.dto.BerryDTO;
import com.pokechess.server.datasources.loader.dto.PokemonTrainerDTO;
import com.pokechess.server.datasources.loader.dto.TrainerObjectDTO;
import com.pokechess.server.datasources.loader.dto.WeatherDTO;
import com.pokechess.server.models.globals.game.cards.Berry;
import com.pokechess.server.models.globals.game.cards.PokemonTrainer;
import com.pokechess.server.models.globals.game.cards.TrainerObject;
import com.pokechess.server.models.globals.game.cards.Weather;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapper {

    public static List<Berry> mapBerryListFromBerryDTOList(List<BerryDTO> dtoList) {
        return dtoList.stream().map(ObjectMapper::mapBerryFromBerryDTO).collect(Collectors.toList());
    }

    public static Berry mapBerryFromBerryDTO(BerryDTO dto) {
        Berry.BerryBuilder builder = Berry.builder().id(dto.getId());
        return builder.build();
    }

    public static List<TrainerObject> mapTrainerObjectListFromTrainerObjectDTOList(List<TrainerObjectDTO> dtoList) {
        return dtoList.stream().map(ObjectMapper::mapTrainerObjectFromTrainerObjectDTO).collect(Collectors.toList());
    }

    public static TrainerObject mapTrainerObjectFromTrainerObjectDTO(TrainerObjectDTO dto) {
        TrainerObject.TrainerObjectBuilder builder = TrainerObject.builder().id(dto.getId());
        return builder.build();
    }

    public static List<PokemonTrainer> mapPokemonTrainerListFromPokemonTrainerDTOList(List<PokemonTrainerDTO> dtoList) {
        return dtoList.stream().map(ObjectMapper::mapPokemonTrainerFromPokemonTrainerDTO).collect(Collectors.toList());
    }

    public static PokemonTrainer mapPokemonTrainerFromPokemonTrainerDTO(PokemonTrainerDTO dto) {
        PokemonTrainer.PokemonTrainerBuilder builder = PokemonTrainer.builder().id(dto.getId());
        return builder.build();
    }

    public static List<Weather> mapWeatherListFromWeatherDTOList(List<WeatherDTO> dtoList) {
        return dtoList.stream().map(ObjectMapper::mapWeatherFromWeatherDTO).collect(Collectors.toList());
    }

    public static Weather mapWeatherFromWeatherDTO(WeatherDTO dto) {
        Weather.WeatherBuilder builder = Weather.builder().id(dto.getId()).name(dto.getName())
                .bonus(dto.getBonus()).malus(dto.getMalus());
        return builder.build();
    }
}

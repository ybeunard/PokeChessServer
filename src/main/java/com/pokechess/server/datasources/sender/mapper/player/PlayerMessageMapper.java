package com.pokechess.server.datasources.sender.mapper.player;

import com.pokechess.server.controllers.pokemon.mapper.PokemonDTOMapper;
import com.pokechess.server.datasources.sender.dto.player.PlayerExperienceMessageDTO;
import com.pokechess.server.datasources.sender.dto.player.PlayerGoldMessageDTO;
import com.pokechess.server.datasources.sender.dto.player.PlayerHandLockMessageDTO;
import com.pokechess.server.datasources.sender.dto.player.PlayerHandMessageDTO;
import com.pokechess.server.models.party.Player;

public class PlayerMessageMapper {
    public static PlayerExperienceMessageDTO mapPlayerToPlayerExperienceMessageDTO(Player player) {
        PlayerExperienceMessageDTO dto = new PlayerExperienceMessageDTO();
        dto.setExperience(player.getExperiencePoint());
        return dto;
    }

    public static PlayerGoldMessageDTO mapPlayerToPlayerGoldMessageDTO(Player player) {
        PlayerGoldMessageDTO dto = new PlayerGoldMessageDTO();
        dto.setGold(player.getMoney());
        return dto;
    }

    public static PlayerHandMessageDTO mapPlayerToPlayerHandMessageDTO(Player player) {
        PlayerHandMessageDTO dto = new PlayerHandMessageDTO();
        dto.setPokemonHand(PokemonDTOMapper.mapPokemonListToPokemonDTOList(player.getHand()));
        return dto;
    }

    public static PlayerHandLockMessageDTO mapPlayerToPlayerHandLockMessageDTO(Player player) {
        PlayerHandLockMessageDTO dto = new PlayerHandLockMessageDTO();
        dto.setHandLock(player.isLock());
        return dto;
    }
}

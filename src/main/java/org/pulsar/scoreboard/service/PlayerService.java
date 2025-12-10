package org.pulsar.scoreboard.service;

import org.pulsar.scoreboard.model.Player;
import org.pulsar.scoreboard.repository.PlayerRepository;

import java.util.Optional;

public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findOrCreate(String playerName) {
        Optional<Player> player = playerRepository.findByName(playerName);
        if (player.isPresent()) {
            return player.get();
        }

        Player newPlayer = new Player(null, playerName);
        playerRepository.save(newPlayer);
        return newPlayer;
    }
}

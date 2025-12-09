package org.pulsar.scoreboard.repository;

import org.pulsar.scoreboard.model.MatchScore;
import redis.clients.jedis.Jedis;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

public class MatchStorage {

    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public MatchStorage(Jedis jedis, ObjectMapper objectMapper) {
        this.jedis = jedis;
        this.objectMapper = objectMapper;
    }

    public void set(UUID matchId, MatchScore matchScore) {
        if (matchId == null || matchScore == null) {
            throw new IllegalArgumentException();
        }

        String jsonMatch = objectMapper.writeValueAsString(matchScore);
        jedis.set(matchId.toString(), jsonMatch);
    }
}

package org.pulsar.scoreboard.repository;

import redis.clients.jedis.Jedis;

public class MatchStorage {

    private final Jedis jedis;

    public MatchStorage(Jedis jedis) {
        this.jedis = jedis;
    }
}

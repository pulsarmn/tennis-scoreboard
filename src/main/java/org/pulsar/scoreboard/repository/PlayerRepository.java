package org.pulsar.scoreboard.repository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pulsar.scoreboard.exception.DatabaseException;
import org.pulsar.scoreboard.model.Player;

import java.util.Optional;

@Slf4j
public class PlayerRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_BY_NAME = "SELECT p FROM Player p WHERE p.name = :playerName";

    public PlayerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Player> findByName(String playerName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            log.info("Finding Player with name '{}'", playerName);
            Optional<Player> player = session.createQuery(FIND_BY_NAME, Player.class)
                    .setParameter("playerName", playerName)
                    .uniqueResultOptional();

            if (player.isPresent()) {
                log.info("Player with name '{}' has been found", playerName);
            } else {
                log.info("Player with name '{}' hasn't been found", playerName);
            }

            return player;
        } catch (HibernateException e) {
            log.error("Error while finding player with name '{}'", playerName);
            throw new DatabaseException(e);
        }
    }
}

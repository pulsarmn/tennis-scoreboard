package org.pulsar.scoreboard.service;

import org.pulsar.scoreboard.dto.MatchCreateRequest;
import org.pulsar.scoreboard.model.Match;
import org.pulsar.scoreboard.model.MatchScore;
import org.pulsar.scoreboard.model.Player;
import org.pulsar.scoreboard.model.PlayerScore;
import org.pulsar.scoreboard.repository.MatchStorage;
import org.pulsar.scoreboard.util.StringUtils;

import java.util.UUID;

public class MatchScoreService {

    private final MatchStorage matchStorage;
    private final PlayerService playerService;

    public MatchScoreService(MatchStorage matchStorage, PlayerService playerService) {
        this.matchStorage = matchStorage;
        this.playerService = playerService;
    }

    public UUID create(MatchCreateRequest createRequest) {
        validate(createRequest);

        Player firstPlayer = playerService.findOrCreate(createRequest.firstPlayerName());
        Player secondPlayer = playerService.findOrCreate(createRequest.secondPlayerName());

        MatchScore matchScore = createMatchScore(firstPlayer, secondPlayer);
        UUID matchId = matchScore.getMatch().getId();
        matchStorage.set(matchId, matchScore);

        return matchId;
    }

    private void validate(MatchCreateRequest createRequest) {
        if (StringUtils.isNullOrBlank(createRequest.firstPlayerName())
                || StringUtils.isNullOrBlank(createRequest.secondPlayerName())) {
            throw new IllegalArgumentException();
        }
    }

    private MatchScore createMatchScore(Player firstPlayer, Player secondPlayer) {
        return MatchScore.builder()
                .match(createMatch(firstPlayer, secondPlayer))
                .firstPlayerScore(new PlayerScore())
                .secondPlayerScore(new PlayerScore())
                .build();
    }

    private Match createMatch(Player firstPlayer, Player secondPlayer) {
        return Match.builder()
                .id(UUID.randomUUID())
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .build();
    }
}

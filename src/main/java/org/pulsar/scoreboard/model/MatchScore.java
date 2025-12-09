package org.pulsar.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchScore {

    private Match match;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
}

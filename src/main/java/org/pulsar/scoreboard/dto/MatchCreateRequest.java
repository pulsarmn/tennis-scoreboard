package org.pulsar.scoreboard.dto;


import lombok.Builder;

@Builder
public record MatchCreateRequest(String firstPlayerName,
                                 String secondPlayerName) {
}

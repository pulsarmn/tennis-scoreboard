package org.pulsar.scoreboard.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "first_player_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Player firstPlayer;

    @JoinColumn(name = "second_player_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Player secondPlayer;
}

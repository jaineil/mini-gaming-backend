package com.cmpe275.lab02.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class OpponentId implements Serializable {
    private long playerId;
    private long opponentId;

    public OpponentId(long playerId, long opponentId) {
        this.playerId = playerId;
        this.opponentId = opponentId;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(long opponentId) {
        this.opponentId = opponentId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}

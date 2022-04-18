package com.cmpe275.lab02.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OPPONENT")
public class Opponent implements Serializable {
    @EmbeddedId
    private OpponentId compositeOpponentId;
}

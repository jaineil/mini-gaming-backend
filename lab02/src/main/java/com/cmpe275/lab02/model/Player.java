package com.cmpe275.lab02.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PLAYER")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "STREET")),
            @AttributeOverride( name = "city", column = @Column(name = "CITY")),
            @AttributeOverride( name = "zip", column = @Column(name = "ZIP")),
            @AttributeOverride( name = "state", column = @Column(name = "STATE"))
            })
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    @JsonIgnoreProperties({"players", "address"})
    private Team team;

    @ManyToMany
    // @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name="OPPONENT",
            joinColumns=@JoinColumn(name="playerId"),
            inverseJoinColumns=@JoinColumn(name="opponentId")
    )
    @JsonIgnoreProperties({"address", "team", "opponents"})
    private List<Player> opponents;

}


package com.cmpe275.lab02.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

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

    // TODO: figure out all relationship mappings annotation
    @OneToMany(mappedBy = "team", fetch=FetchType.EAGER)
    @JsonIgnoreProperties({"address", "team", "opponents"})
    private List<Player> players;
}

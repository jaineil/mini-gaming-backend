package com.cmpe275.lab02.controller;

import com.cmpe275.lab02.model.Address;
import com.cmpe275.lab02.model.Player;
import com.cmpe275.lab02.model.Team;
import com.cmpe275.lab02.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

// /player/apis/post/insert/{...}
// /team/apis/update/{...}

@RestController
@RequestMapping("player") //
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    // build create player REST API
    @PostMapping()
    public ResponseEntity<Player> savePlayer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam (required = false) String street,
            @RequestParam (required = false) String city,
            @RequestParam (required = false) String state,
            @RequestParam (required = false) String zip,
            @RequestParam (required = false) long teamId
    ) {

        // Player player = new Player(....); -> (params.name, params.age..)
        // public Player (args1, args2..) {} ;

        Player player = Player.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .address(
                        Address.builder()
                                .street(street)
                                .city(city)
                                .state(state)
                                .zip(zip)
                                .build()
                )
                .team(Team.builder().id(teamId).build())
                .build();

        long playerId = playerService.insert(player);
        return new ResponseEntity<Player>(playerService.fetch(playerId), HttpStatus.CREATED);
    }

    // get player by ID
    @GetMapping("/{playerId}")
    @ResponseBody
    public ResponseEntity<Player> getPlayer(
            @PathVariable long playerId
    ) {
        return new ResponseEntity<Player>(playerService.fetch(playerId), HttpStatus.OK);
    }

    // update player info
    @PutMapping("/{playerId}")
    public ResponseEntity<Player> updatePlayer(
            @PathVariable("playerId") long playerId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam (required = false) String street,
            @RequestParam (required = false) String city,
            @RequestParam (required = false) String state,
            @RequestParam (required = false) String zip,
            @RequestParam (required = false) long teamId
    ) {
        Player player = Player.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .address(
                        Address.builder()
                                .street(street)
                                .city(city)
                                .state(state)
                                .zip(zip)
                                .build()
                )
                .team(Team.builder().id(teamId).build())
                .build();

        return new ResponseEntity<Player>(playerService.update(playerId, player), HttpStatus.OK);
    }
}

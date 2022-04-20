package com.cmpe275.lab02.controller;

import com.cmpe275.lab02.model.Address;
import com.cmpe275.lab02.model.Player;
import com.cmpe275.lab02.model.Team;
import com.cmpe275.lab02.service.OpponentService;
import com.cmpe275.lab02.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cmpe275.lab02.service.TeamService;

@RestController
@RequestMapping("player") //
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private OpponentService opponentService;

    // create player
    @PostMapping()
    public ResponseEntity<?> savePlayer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam (required = false) String description,
            @RequestParam (required = false) String street,
            @RequestParam (required = false) String city,
            @RequestParam (required = false) String state,
            @RequestParam (required = false) String zip,
            @RequestParam (required = false) Long teamId
    ) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            return new ResponseEntity<>("Compulsory parameters missing", HttpStatus.BAD_REQUEST);
        }

        if (playerService.isEmailExists(email)) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        if (teamId == null) {
            Player player = Player.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .description(description)
                    .address(
                            Address.builder()
                                    .street(street)
                                    .city(city)
                                    .state(state)
                                    .zip(zip)
                                    .build()
                    )
                    .build();

            long playerId = playerService.insert(player);
            Player newPlayer = playerService.fetch(playerId);
            return new ResponseEntity<Player>(newPlayer, HttpStatus.OK);

        } else {
            if (!teamService.isTeam(teamId)) {
                return new ResponseEntity<>("No team with given team id exists", HttpStatus.BAD_REQUEST);
            }
        }

        Player player = Player.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .description(description)
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
        Player newPlayer = playerService.fetch(playerId);
        Team newPlayerTeam = teamService.fetch(teamId);
        newPlayer.setTeam(newPlayerTeam);
        return new ResponseEntity<Player>(newPlayer, HttpStatus.OK);
    }

    // get player by ID
    @GetMapping("/{playerId}")
    @ResponseBody
    public ResponseEntity<?> getPlayer(
            @PathVariable long playerId
    ) {
        if (!playerService.isPlayer(playerId)) {
            return new ResponseEntity<>("No player with given player id exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Player>(playerService.fetch(playerId), HttpStatus.OK);
    }

    // update player info
    @PutMapping("/{playerId}")
    public ResponseEntity<?> updatePlayer(
            @PathVariable("playerId") long playerId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam (required = false) String description,
            @RequestParam (required = false) String street,
            @RequestParam (required = false) String city,
            @RequestParam (required = false) String state,
            @RequestParam (required = false) String zip,
            @RequestParam (required = false) Long teamId
    ) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            return new ResponseEntity<>("Compulsory parameters missing", HttpStatus.BAD_REQUEST);
        }

        if (!playerService.isPlayer(playerId)) {
            return new ResponseEntity<>("No player with given player id exists", HttpStatus.NOT_FOUND);
        }

        if (!teamService.isTeam(teamId)) {
            return new ResponseEntity<>("No team with given team id exists", HttpStatus.BAD_REQUEST);
        }

        if (teamId == null) {
            Player player = Player.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .description(description)
                    .address(
                            Address.builder()
                                    .street(street)
                                    .city(city)
                                    .state(state)
                                    .zip(zip)
                                    .build()
                    )
                    .build();

            return new ResponseEntity<Player>(playerService.update(playerId, player), HttpStatus.OK);
        }

        Player player = Player.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .description(description)
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

    //delete by ID
    @DeleteMapping("/{playerId}")
    public  ResponseEntity<?> deletePlayer(
            @PathVariable long playerId
    ) {
        if (!playerService.isPlayer(playerId)) {
            return new ResponseEntity<>("No player with given player id exists", HttpStatus.NOT_FOUND);
        }
        Player deletedPlayer = playerService.fetch(playerId);
        playerService.delete(playerId);
        return new ResponseEntity<Player>(deletedPlayer, HttpStatus.OK);
    }
}

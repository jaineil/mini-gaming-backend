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

import javax.xml.ws.Response;

// /player/apis/post/insert/{...}
// /team/apis/update/{...}

@RestController
@RequestMapping("player") //
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private OpponentService opponentService;

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
        Player newPlayer = playerService.fetch(playerId);
        Team newPlayerTeam = teamService.fetch(teamId);
        newPlayer.setTeam(newPlayerTeam);
        return new ResponseEntity<Player>(newPlayer, HttpStatus.CREATED);
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

    //delete by ID
    @DeleteMapping("/{playerId}")
    public  ResponseEntity<String> deletePlayer(
            @PathVariable long playerId
    ){
        try{
            opponentService.removeAllOpponents(playerId);
            playerService.delete(playerId);
        } catch(Exception e){
            System.err.println(e);
        }

        return new ResponseEntity<String>("Deleted player with ID: "+playerId+" successfully", HttpStatus.OK);
    }

    @DeleteMapping("/test/{teamId}")
    public  ResponseEntity<String> updatePlayersOfTeam(
            @PathVariable long teamId
    ){
        try{
            playerService.updatePlayersOfTeam(teamId);
        } catch(Exception e){
            System.err.println(e);
        }

        return new ResponseEntity<String>("Set team=null for all players of team with ID: "+teamId+" successfully", HttpStatus.OK);
    }
}

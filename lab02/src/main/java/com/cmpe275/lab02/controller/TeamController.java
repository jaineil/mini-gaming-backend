package com.cmpe275.lab02.controller;

import com.cmpe275.lab02.model.Address;
import com.cmpe275.lab02.model.Team;
import com.cmpe275.lab02.service.PlayerService;
import com.cmpe275.lab02.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("team")
public class TeamController {

        @Autowired
        private TeamService teamService;

        @Autowired
        private PlayerService playerService;

        // build create player REST API
        @PostMapping()
        public ResponseEntity<Team> saveTeam(
                @RequestParam String name,
                @RequestParam String description,
                @RequestParam(required = false) String street,
                @RequestParam(required = false) String city,
                @RequestParam(required = false) String state,
                @RequestParam(required = false) String zip
        ) {

            Team team = Team.builder()
                    .name(name)
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
            return new ResponseEntity<Team>(teamService.insert(team), HttpStatus.CREATED);
        }

        @GetMapping("/{teamId}")
        @ResponseBody
        public ResponseEntity<Team> getTeam(@PathVariable long teamId) {
            return new ResponseEntity<Team>(teamService.fetch(teamId), HttpStatus.OK);
        }

        @PutMapping("/{teamId}")
        public ResponseEntity<Team> updateTeam(
                @PathVariable("teamId") long teamId,
                @RequestParam String name,
                @RequestParam String description,
                @RequestParam (required = false) String street,
                @RequestParam (required = false) String city,
                @RequestParam (required = false) String state,
                @RequestParam (required = false) String zip
        ) {
            Team team = Team.builder()
                    .name(name)
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
            return new ResponseEntity<Team>(teamService.update(teamId, team), HttpStatus.OK);
        }

        //delete by id
        @DeleteMapping("/{teamId}")
        public  ResponseEntity<String> deleteTeam(
                @PathVariable long teamId
        ){
            try{
                playerService.updatePlayersOfTeam(teamId);
                teamService.delete(teamId);
            } catch(Exception e){
                System.err.println(e);
            }

            return new ResponseEntity<String>("Deleted team with ID: "+teamId+" successfully", HttpStatus.OK);
        }
    }


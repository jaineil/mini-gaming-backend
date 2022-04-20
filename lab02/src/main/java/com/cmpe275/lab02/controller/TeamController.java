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
        public ResponseEntity<?> saveTeam(
                @RequestParam String name,
                @RequestParam String description,
                @RequestParam(required = false) String street,
                @RequestParam(required = false) String city,
                @RequestParam(required = false) String state,
                @RequestParam(required = false) String zip
        ) {

            if(name.isEmpty() || name ==null)
            {
                return new ResponseEntity<>("Name field is required!", HttpStatus.BAD_REQUEST);
            }

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



            try{
                return new ResponseEntity<Team>(teamService.insert(team), HttpStatus.OK);
            }
            catch(Exception e){
                return new ResponseEntity<String>( e.getMessage(), HttpStatus.BAD_REQUEST);
            }

        }

        @GetMapping("/{teamId}")
        @ResponseBody
        public ResponseEntity<?> getTeam(@PathVariable long teamId) {
//            if(!teamService.find(teamId))
//                return new ResponseEntity<>("")
            try{
                Team fetchedTeam  = teamService.fetch(teamId);
                System.out.println("Fetching team with Id "+fetchedTeam.getId());
                return new ResponseEntity<>(fetchedTeam, HttpStatus.OK);
            }catch ( Exception e){

                return new ResponseEntity<>("Team ID does not exist!", HttpStatus.NOT_FOUND);
            }
        }

        @PutMapping("/{teamId}")
        public ResponseEntity<?> updateTeam(
                @PathVariable("teamId") long teamId,
                @RequestParam String name,
                @RequestParam (required = false) String description,
                @RequestParam (required = false) String street,
                @RequestParam (required = false) String city,
                @RequestParam (required = false) String state,
                @RequestParam (required = false) String zip
        ) {

            if( name.isEmpty() || name ==null)
            {
                return new ResponseEntity<>("Name field is required!", HttpStatus.BAD_REQUEST);
            }
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

            try{
                Team fetchedTeam  = teamService.fetch(teamId);
                System.out.println("Updating team with Id "+fetchedTeam.getId());
                return new ResponseEntity<Team>(teamService.update(teamId, team), HttpStatus.OK);
            }catch ( Exception e){
                return new ResponseEntity<>("Team ID does not exist!", HttpStatus.NOT_FOUND);
            }
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

        }
    }


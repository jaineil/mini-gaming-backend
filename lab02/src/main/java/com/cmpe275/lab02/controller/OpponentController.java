package com.cmpe275.lab02.controller;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import com.cmpe275.lab02.service.OpponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("opponents")
public class OpponentController {
    @Autowired
    private OpponentService opponentService;

    @PostMapping("/{playerId}/{opponentId}")
    public ResponseEntity<String> saveOpponent(
            @PathVariable long playerId,
            @PathVariable long opponentId
    ) {

        Opponent opponent = new Opponent(new OpponentId(playerId, opponentId));
        Opponent reverseOpponent = new Opponent(new OpponentId(opponentId, playerId));
        try {
            opponentService.insert(opponent, reverseOpponent);
        } catch (Exception e) {
            System.err.println(e);
        }

        return new ResponseEntity<String>("Recorded opponent", HttpStatus.OK);
    }

    @DeleteMapping("/{playerId}/{opponentId}")
    public ResponseEntity<String> removeOpponent(
            @PathVariable long playerId,
            @PathVariable long opponentId

    ) {
        OpponentId opponent = new OpponentId(playerId, opponentId);
        opponentService.removeOpponent(opponent);

        OpponentId reverseOpponent = new OpponentId(opponentId, playerId);
        opponentService.removeOpponent(reverseOpponent);

        return new ResponseEntity<String>("Removed opponent successfully", HttpStatus.OK);
    }

    @DeleteMapping("/test/{playerId}")
    public ResponseEntity<String> removeAllOpponentsForPlayer(
            @PathVariable long playerId
    ) {
        opponentService.removeAllOpponents(playerId);
        return new ResponseEntity<String>("Removed all opponent", HttpStatus.OK);
    }
}

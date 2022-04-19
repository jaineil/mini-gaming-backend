package com.cmpe275.lab02.controller;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import com.cmpe275.lab02.model.Player;
import com.cmpe275.lab02.service.OpponentService;
import com.cmpe275.lab02.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("opponents")
public class OpponentController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private OpponentService opponentService;

    @PostMapping("/{playerId}/{opponentId}")
    public ResponseEntity<String> saveOpponent(
            @PathVariable long playerId,
            @PathVariable long opponentId
    ) {
        if (playerId == opponentId) {
            return new ResponseEntity<String>("Player and opponent cannot be same", HttpStatus.BAD_REQUEST);
        }

        try {
            Player playerInfo = playerService.fetch(playerId);
            playerInfo.getId();
            Player opponentInfo = playerService.fetch(opponentId);
            opponentInfo.getId();
        } catch(Exception e) {
            return new ResponseEntity<String>("One or more players don't exist", HttpStatus.NOT_FOUND);
        }
        OpponentId compositeOpponentId = new OpponentId(playerId, opponentId);
        OpponentId compositeReverseOpponentId = new OpponentId(opponentId, playerId);

        if ((opponentService.checkIfExists(compositeOpponentId)) || (opponentService.checkIfExists(compositeReverseOpponentId))) {
            return new ResponseEntity<String>("Two players are already opponents of each other", HttpStatus.BAD_REQUEST);
        }

        try {
            Opponent opponent = new Opponent(compositeOpponentId);
            Opponent reverseOpponent = new Opponent(compositeReverseOpponentId);
            opponentService.insert(opponent, reverseOpponent);
            return new ResponseEntity<String>("Recorded opponent", HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<String>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

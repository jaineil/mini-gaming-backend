package com.cmpe275.lab02.controller;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import com.cmpe275.lab02.service.OpponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.cmpe275.lab02.service;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import com.cmpe275.lab02.repository.OpponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpponentService {
    @Autowired
    private OpponentRepository opponentRepository;

    public void insert(Opponent opponent, Opponent reverseOpponent) {
        Opponent firstInsert = opponentRepository.save(opponent);
        Opponent secondInsert = opponentRepository.save(reverseOpponent);
        return;
    }
}

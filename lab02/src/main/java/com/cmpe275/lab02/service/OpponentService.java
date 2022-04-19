package com.cmpe275.lab02.service;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import com.cmpe275.lab02.repository.OpponentRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OpponentService {
    @Autowired
    private OpponentRepository opponentRepository;

    public void insert(Opponent opponent, Opponent reverseOpponent) {
        Opponent firstInsert = opponentRepository.save(opponent);
        Opponent secondInsert = opponentRepository.save(reverseOpponent);
        return;
    }

    @Transactional
    public void removeOpponent(OpponentId compositeOpponentId) {
        try {
            Opponent opponent = opponentRepository.getOpponentByCompositeOpponentId(compositeOpponentId);
            opponent.getCompositeOpponentId().getPlayerId();
            opponent.getCompositeOpponentId().getOpponentId();
            opponentRepository.removeOpponentByCompositeOpponentId(compositeOpponentId);
        } catch (Exception e) {
            System.out.println("These two players are not opponents of each other");
        }
    }

    @Transactional
    public void removeAllOpponents(long playerId) {
        opponentRepository.removeAllOpponentsForPlayer(playerId);
    }
}

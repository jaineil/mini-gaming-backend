package com.cmpe275.lab02.service;

import com.cmpe275.lab02.model.Player;
import com.cmpe275.lab02.repository.OpponentRepository;
import com.cmpe275.lab02.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

// this annotation handles a singleton bean creation of the following class, enabling DI
@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public long insert(Player player) {
        long id = playerRepository.save(player).getId();
        System.out.println("The insert ID of player is -> " + id);
        return id;
    }

    public Player fetch(long playerId) {
        return playerRepository.getPlayerById(playerId);
    }

    public Player update(long playerId, Player player) {
        Player existingPlayer = playerRepository.getPlayerById(playerId);
        existingPlayer.setFirstName(player.getFirstName());
        existingPlayer.setLastName(player.getLastName());
        existingPlayer.setEmail(player.getEmail());
        existingPlayer.setAddress(player.getAddress());
        playerRepository.save(existingPlayer);
        return existingPlayer;
    }

    @Transactional
    public void delete(long playerId){
//        Player existingPlayer = playerRepository.getPlayerById(playerId);
//        // delete player first dissolves the inverse relationship
//        for (Player opponent: existingPlayer.getOpponents())
//        {
//            opponent.getOpponents().remove(existingPlayer);
//            playerRepository.save(opponent);
//        }
//        // then cascade deletes the straight relationship
        playerRepository.removePlayerById(playerId);

    }

    @Transactional
    public void updatePlayersOfTeam(long teamId) {
        playerRepository.updateAllPlayersOfTeam(teamId);
    }

}

package com.cmpe275.lab02.service;

import com.cmpe275.lab02.model.Player;
import com.cmpe275.lab02.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

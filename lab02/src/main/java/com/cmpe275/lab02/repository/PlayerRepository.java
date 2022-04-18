package com.cmpe275.lab02.repository;

import com.cmpe275.lab02.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long>{
    Player getPlayerById(long id);
//    public Player removePlayerById(long id);
}

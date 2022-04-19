package com.cmpe275.lab02.repository;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import com.cmpe275.lab02.model.Player;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player getPlayerById(long id);

    void removePlayerById(long id);

    @Modifying
    @Query(value = "UPDATE player SET team_id = NULL WHERE team_id = ?1", nativeQuery = true)
    void updateAllPlayersOfTeam(long teamId);
}

package com.cmpe275.lab02.repository;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpponentRepository extends CrudRepository<Opponent, OpponentId> {
    Opponent getOpponentByCompositeOpponentId(OpponentId compositeOpponentId);

    void removeOpponentByCompositeOpponentId(OpponentId compositeOpponentId);

    @Modifying
    @Query(value = "DELETE FROM opponent WHERE player_id = ?1 OR opponent_id = ?1", nativeQuery = true)
    void removeAllOpponentsForPlayer(long playerId);

}

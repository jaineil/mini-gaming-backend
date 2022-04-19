package com.cmpe275.lab02.repository;

import com.cmpe275.lab02.model.Opponent;
import com.cmpe275.lab02.model.OpponentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpponentRepository extends CrudRepository<Opponent, OpponentId> {
    Opponent getOpponentByCompositeOpponentId(OpponentId compositeOpponentId);
    void removeOpponentByCompositeOpponentId(OpponentId compositeOpponentId);
}

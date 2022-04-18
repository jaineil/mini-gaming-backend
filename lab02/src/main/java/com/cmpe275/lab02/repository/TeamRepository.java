package com.cmpe275.lab02.repository;

import com.cmpe275.lab02.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    public Team getTeamById(long id);
}

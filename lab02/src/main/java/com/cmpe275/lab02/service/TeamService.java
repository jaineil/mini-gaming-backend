package com.cmpe275.lab02.service;

import com.cmpe275.lab02.model.Team;
import com.cmpe275.lab02.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team insert(Team team) {
        long id = teamRepository.save(team).getId();
        System.out.println("The insert ID of team is -> " + id);
        return teamRepository.getTeamById(id);
    }

    public Team fetch(long teamId) {
        return teamRepository.getTeamById(teamId);
    }
}

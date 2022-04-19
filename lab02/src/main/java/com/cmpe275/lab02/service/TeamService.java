package com.cmpe275.lab02.service;

import com.cmpe275.lab02.model.Player;
import com.cmpe275.lab02.model.Team;
import com.cmpe275.lab02.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Team update(long teamId, Team team) {
        Team existingTeam = teamRepository.getTeamById(teamId);
        existingTeam.setName(team.getName());
        existingTeam.setDescription(team.getDescription());
        existingTeam.setAddress(team.getAddress());
        teamRepository.save(existingTeam);
        return existingTeam;
    }

    @Transactional
    public void delete(long teamId) {

        teamRepository.removeTeamById(teamId);
    }
}

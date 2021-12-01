package com.myfrei.qa.platform.service.statistics.impl;

import com.myfrei.qa.platform.dao.abstracts.model.ReputationDAO;
import com.myfrei.qa.platform.models.dto.UserStatisticDto;
import com.myfrei.qa.platform.service.statistics.abstracts.Tabs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("reputation")
public class UsersReputation implements Tabs {
    private final ReputationDAO reputationDAO;

    @Autowired
    public UsersReputation(ReputationDAO reputationDAO) {
        this.reputationDAO = reputationDAO;
    }

    @Override
    public UserStatisticDto getList(String sortType, Long userId, Integer page) {
        return UserStatisticDto.builder()
                .userReputation(reputationDAO.getReputationByUserId(userId))
                .build();
    }
}


package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.entity.user.Reputation;
import com.myfrei.qa.platform.models.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface ReputationDAO extends ReadWriteDAO<Reputation, Long> {
    Optional<Reputation> findByUserIdAndDate(User user);

    List<Reputation> getReputationByUserId(Long userId);

    Long getSumOfUserReputation(Long userId);
}


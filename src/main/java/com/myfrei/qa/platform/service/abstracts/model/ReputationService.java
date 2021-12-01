package com.myfrei.qa.platform.service.abstracts.model;

import com.myfrei.qa.platform.models.entity.user.Reputation;
import com.myfrei.qa.platform.models.entity.user.User;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
    void updateOrInsert(User user, int count);
}

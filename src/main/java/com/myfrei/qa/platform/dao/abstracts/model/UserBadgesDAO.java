package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.entity.user.User;
import com.myfrei.qa.platform.models.entity.user.UserBadges;

import java.util.List;

public interface UserBadgesDAO extends ReadWriteDAO<UserBadges, Long> {
    List<UserBadges> getUserBadges(User user);
}


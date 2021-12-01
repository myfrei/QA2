package com.myfrei.qa.platform.service.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.BadgesDAO;
import com.myfrei.qa.platform.models.entity.Badges;
import com.myfrei.qa.platform.service.abstracts.model.BadgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgesServiceImpl extends ReadWriteServiceImpl<Badges, Long> implements BadgesService {

    private final BadgesDAO badgesDAO;

    @Autowired
    public BadgesServiceImpl(BadgesDAO badgesDAO) {
        super(badgesDAO);
        this.badgesDAO = badgesDAO;
    }

}

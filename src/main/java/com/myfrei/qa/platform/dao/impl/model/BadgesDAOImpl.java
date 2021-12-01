package com.myfrei.qa.platform.dao.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.BadgesDAO;
import com.myfrei.qa.platform.models.entity.Badges;
import org.springframework.stereotype.Repository;

@Repository
public class BadgesDAOImpl extends ReadWriteDAOImpl<Badges, Long> implements BadgesDAO {
}

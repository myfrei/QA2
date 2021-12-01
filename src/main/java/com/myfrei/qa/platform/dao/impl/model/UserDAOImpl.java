package com.myfrei.qa.platform.dao.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.UserDAO;
import com.myfrei.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends ReadWriteDAOImpl<User, Long> implements UserDAO {
}
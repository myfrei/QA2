package com.myfrei.qa.platform.service.impl.model;


import com.myfrei.qa.platform.dao.abstracts.model.UserDAO;
import com.myfrei.qa.platform.models.entity.user.User;
import com.myfrei.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        super(userDAO);
        this.userDAO = userDAO;
    }
}

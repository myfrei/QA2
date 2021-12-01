package com.myfrei.qa.platform.service.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.UserFavoriteQuestionDAO;
import com.myfrei.qa.platform.models.entity.user.UserFavoriteQuestion;
import com.myfrei.qa.platform.service.abstracts.model.UserFavoriteQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFavoriteQuestionServiceImpl extends ReadWriteServiceImpl<UserFavoriteQuestion, Long> implements UserFavoriteQuestionService {

    private final UserFavoriteQuestionDAO userFavoriteQuestionDAO;

    @Autowired
    public UserFavoriteQuestionServiceImpl(UserFavoriteQuestionDAO userFavoriteQuestionDAO) {
        super(userFavoriteQuestionDAO);
        this.userFavoriteQuestionDAO = userFavoriteQuestionDAO;
    }
}

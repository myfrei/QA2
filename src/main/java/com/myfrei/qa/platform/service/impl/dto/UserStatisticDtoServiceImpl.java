package com.myfrei.qa.platform.service.impl.dto;

import com.myfrei.qa.platform.dao.abstracts.dto.AnswerDtoDAO;
import com.myfrei.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.myfrei.qa.platform.dao.abstracts.dto.UserDtoDAO;
import com.myfrei.qa.platform.dao.abstracts.model.ReputationDAO;
import com.myfrei.qa.platform.models.dto.UserDto;
import com.myfrei.qa.platform.models.dto.UserStatisticDto;
import com.myfrei.qa.platform.service.abstracts.dto.UserStatisticDtoService;
import com.myfrei.qa.platform.service.statistics.main.MainUserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatisticDtoServiceImpl implements UserStatisticDtoService {

    private final AnswerDtoDAO answerDtoDAO;
    private final QuestionDtoDao questionDtoDao;
    private final UserDtoDAO userDtoDAO;
    private final ReputationDAO reputationDAO;
    private final MainUserStatistics mainUserStatistics;

    @Autowired
    public UserStatisticDtoServiceImpl(AnswerDtoDAO answerDtoDAO,
                                       QuestionDtoDao questionDtoDao,
                                       UserDtoDAO userDtoDAO,
                                       ReputationDAO reputationDAO,
                                       MainUserStatistics mainUserStatistics) {
        this.answerDtoDAO = answerDtoDAO;
        this.questionDtoDao = questionDtoDao;
        this.userDtoDAO = userDtoDAO;
        this.reputationDAO = reputationDAO;
        this.mainUserStatistics = mainUserStatistics;
    }

    @Override
    public UserStatisticDto getUserStatistic(UserDto user, String tab, String sort, Integer page) {
        if (page < 1) {
            page = 1;
        }
        UserStatisticDto userStatisticDto = mainUserStatistics.getResult(tab.toLowerCase(),sort.toLowerCase(), user.getId(), page);

        userStatisticDto.setTotalUserAnswers(answerDtoDAO.getAnswerCountByUserId(user.getId()));
        userStatisticDto.setTotalUserQuestions(questionDtoDao.getQuestionCountByUserId(user.getId()));
        userStatisticDto.setTotalUserReputation(reputationDAO.getSumOfUserReputation(user.getId()));
        userStatisticDto.setAllViews(userDtoDAO.getAllViews(user.getId()));
        userStatisticDto.setUserDto(user);
        return userStatisticDto;
    }
}


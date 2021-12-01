package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.dto.QuestionDto;
import com.myfrei.qa.platform.models.entity.user.UserFavoriteQuestion;

import java.util.List;

public interface UserFavoriteQuestionDAO extends ReadWriteDAO<UserFavoriteQuestion, Long> {
    Long getCountOfUserFavoriteQuestion(Long userId);

    List<QuestionDto> getUserFavoriteSortByDate(Long userId, Integer page);

    List<QuestionDto> getUserFavoriteSortByViews(Long userId, Integer page);

    List<QuestionDto> getUserFavoriteSortByVotes(Long userId, Integer page);
}

package com.myfrei.qa.platform.dao.abstracts.dto;

import com.myfrei.qa.platform.models.dto.AnswerDto;

import java.util.List;

public interface AnswerDtoDAO {

    List<AnswerDto> getAnswersDtoByQuestionIdSortNew(Long questionId, Long userId);

    List<AnswerDto> getAnswersDtoByQuestionIdSortCount(Long questionId, Long userId);

    List<AnswerDto> getAnswersDtoByQuestionIdSortDate(Long questionId, Long userId);

    Boolean isUserNotAnswered(Long questionId, Long userId);

    //    new methods
    Long getAnswerCountByUserId(Long user_id);

    List<AnswerDto> getAnswerDtoByUserIdSortByDate(Long userId, Integer page);

    List<AnswerDto> getAnswerDtoByUserIdSortByViews(Long userId, Integer page);

    List<AnswerDto> getAnswerDtoByUserIdSortByVotes(Long userId, Integer page);
}

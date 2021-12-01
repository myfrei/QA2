package com.myfrei.qa.platform.dao.abstracts.dto;

import com.myfrei.qa.platform.models.dto.QuestionDto;
import com.myfrei.qa.platform.models.dto.TagDto;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoDao {

    List<QuestionDto> getQuestionDtoList();

    Optional<QuestionDto> getQuestionDtoById(Long id);

    Optional<QuestionDto> getQuestionDtoById(Long questionId, Long userId);

    List<QuestionDto> getQuestionDtoListByUserId(Long userId);

    Optional<QuestionDto> hasQuestionAnswer(Long questionId);

    List<QuestionDto> getQuestionList(int page, int size);

    List<TagDto> getTagList(long q_id);

    Long getCount();

    Integer getCountValuableWithFalse(Long questionId);

    Integer getCountValuableWithTrue(Long questionId);

    Integer getCountValuable(Long questionId);

    Optional<QuestionDto> getCountValuableQuestionWithUserVote(Long questionId, Long userId);

    Integer sumVotesUserByVote(Long questionId, Long userId);


//    new methods

    Long getQuestionCountByUserId(Long userId);

    List<QuestionDto> getQuestionDtoByUserIdSortByDate(Long userId, Integer page);

    List<QuestionDto> getQuestionDtoByUserIdSortByVotes(Long userId, Integer page);

    List<QuestionDto> getQuestionDtoByUserIdSortByViews(Long userId, Integer page);


    List<QuestionDto> getQuestionsByTagId(Long tagId);

    List<QuestionDto> getUnansweredQuestions();
}
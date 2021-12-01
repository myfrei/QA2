package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.entity.question.answer.AnswerVote;

public interface AnswerVoteDAO extends ReadWriteDAO<AnswerVote, Long> {
    Integer getAllVotesByAnswerId(Long answerId);
    Integer getVotesOfUserByAnswer(Long answerId, Long userId);
}

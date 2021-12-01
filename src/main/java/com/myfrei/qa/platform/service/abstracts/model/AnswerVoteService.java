package com.myfrei.qa.platform.service.abstracts.model;

import com.myfrei.qa.platform.models.entity.question.answer.AnswerVote;

public interface AnswerVoteService extends ReadWriteService<AnswerVote, Long> {
    Boolean addAnswerVotePlus(Long questionId, Long answerId, Long userId);

    Boolean addAnswerVoteMinus(Long questionId, Long answerId, Long userId);

    Integer getVotesOfAnswer(Long answerId);
}

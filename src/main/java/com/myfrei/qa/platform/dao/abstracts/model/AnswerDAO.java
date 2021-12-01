package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.entity.question.answer.Answer;

import java.util.Optional;

public interface AnswerDAO extends ReadWriteDAO<Answer, Long> {
    Optional<Answer> getHelpfulAnswerByQuestionId(Long questionId);
}

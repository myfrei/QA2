package com.myfrei.qa.platform.dao.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.VoteQuestionDAO;
import com.myfrei.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

@Repository
public class VoteQuestionDAOImpl extends ReadWriteDAOImpl<VoteQuestion, VoteQuestion.VoteQuestionPK> implements VoteQuestionDAO {
}

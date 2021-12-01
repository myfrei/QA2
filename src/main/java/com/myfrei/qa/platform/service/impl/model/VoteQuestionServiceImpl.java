package com.myfrei.qa.platform.service.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.VoteQuestionDAO;
import com.myfrei.qa.platform.models.entity.question.VoteQuestion;
import com.myfrei.qa.platform.service.abstracts.model.VoteQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, VoteQuestion.VoteQuestionPK> implements VoteQuestionService {

    private final VoteQuestionDAO voteQuestionDAO;

    @Autowired
    public VoteQuestionServiceImpl(VoteQuestionDAO voteQuestionDAO) {
        super(voteQuestionDAO);
        this.voteQuestionDAO = voteQuestionDAO;
    }
}

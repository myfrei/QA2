package com.myfrei.qa.platform.service.impl.model.comment;

import com.myfrei.qa.platform.dao.abstracts.model.comment.CommentQuestionDAO;
import com.myfrei.qa.platform.models.entity.question.CommentQuestion;
import com.myfrei.qa.platform.service.abstracts.model.comment.CommentQuestionService;
import com.myfrei.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentQuestionServiceImpl extends ReadWriteServiceImpl<CommentQuestion, Long> implements CommentQuestionService {

    private final CommentQuestionDAO commentQuestionDAO;

    @Autowired
    public CommentQuestionServiceImpl(CommentQuestionDAO commentQuestionDAO) {
        super(commentQuestionDAO);
        this.commentQuestionDAO = commentQuestionDAO;
    }
}

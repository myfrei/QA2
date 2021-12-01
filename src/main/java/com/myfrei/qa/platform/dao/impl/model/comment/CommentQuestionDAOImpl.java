package com.myfrei.qa.platform.dao.impl.model.comment;

import com.myfrei.qa.platform.dao.abstracts.model.comment.CommentQuestionDAO;
import com.myfrei.qa.platform.dao.impl.model.ReadWriteDAOImpl;
import com.myfrei.qa.platform.models.entity.question.CommentQuestion;
import org.springframework.stereotype.Repository;

@Repository
public class CommentQuestionDAOImpl extends ReadWriteDAOImpl<CommentQuestion, Long> implements CommentQuestionDAO {
}

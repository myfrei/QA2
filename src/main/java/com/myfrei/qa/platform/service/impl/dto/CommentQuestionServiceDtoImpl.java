package com.myfrei.qa.platform.service.impl.dto;

import com.myfrei.qa.platform.dao.abstracts.dto.CommentQuestionDtoDAO;
import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;
import com.myfrei.qa.platform.service.abstracts.dto.CommentQuestionServiceDto;
import com.myfrei.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentQuestionServiceDtoImpl extends ReadWriteServiceImpl<Comment, Long> implements CommentQuestionServiceDto {

    private final CommentQuestionDtoDAO commentQuestionDtoDao;

    @Autowired
    public CommentQuestionServiceDtoImpl(CommentQuestionDtoDAO commentQuestionDtoDao) {
        super(commentQuestionDtoDao);
        this.commentQuestionDtoDao = commentQuestionDtoDao;
    }

    @Override
    public List<CommentDto> getCommentsToQuestion(Long questionId) {
        return commentQuestionDtoDao.getCommentsToQuestion(questionId);
    }

    @Override
    public boolean hasUserToCommentQuestion(Long questionId, Long userId) {
        return commentQuestionDtoDao.hasUserCommentQuestion(questionId, userId);
    }
}

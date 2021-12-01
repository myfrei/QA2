package com.myfrei.qa.platform.service.impl.dto;

import com.myfrei.qa.platform.dao.abstracts.dto.CommentAnswerDtoDAO;
import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;
import com.myfrei.qa.platform.service.abstracts.dto.CommentAnswerServiceDto;
import com.myfrei.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentAnswerServiceDtoImpl extends ReadWriteServiceImpl<Comment, Long> implements CommentAnswerServiceDto {

    private final CommentAnswerDtoDAO commentAnswerDtoDao;

    @Autowired
    public CommentAnswerServiceDtoImpl(CommentAnswerDtoDAO commentAnswerDtoDao) {
        super(commentAnswerDtoDao);
        this.commentAnswerDtoDao = commentAnswerDtoDao;
    }

    @Override
    public List<CommentDto> getCommentsToAnswer(Long answerId) {
        return commentAnswerDtoDao.getCommentsToAnswer(answerId);
    }

    @Override
    public boolean hasUserToCommentAnswer(Long answerId, Long userId) {
        return commentAnswerDtoDao.hasUserCommentAnswer(answerId, userId);
    }
}

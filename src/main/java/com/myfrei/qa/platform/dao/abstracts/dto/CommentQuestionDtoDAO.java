package com.myfrei.qa.platform.dao.abstracts.dto;

import com.myfrei.qa.platform.dao.abstracts.model.ReadWriteDAO;
import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;

import java.util.List;

public interface CommentQuestionDtoDAO extends ReadWriteDAO<Comment, Long> {

    List<CommentDto> getCommentsToQuestion(Long questionId);

    Boolean hasUserCommentQuestion (Long questionId, Long userId);
}

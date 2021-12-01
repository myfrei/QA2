package com.myfrei.qa.platform.service.abstracts.dto;

import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;
import com.myfrei.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.List;

public interface CommentQuestionServiceDto extends ReadWriteService<Comment, Long> {

    List<CommentDto> getCommentsToQuestion(Long questionId);

    boolean hasUserToCommentQuestion(Long questionId, Long userId);
}

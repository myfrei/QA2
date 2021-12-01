package com.myfrei.qa.platform.service.abstracts.dto;

import com.myfrei.qa.platform.models.dto.CommentDto;
import com.myfrei.qa.platform.models.entity.Comment;
import com.myfrei.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.List;

public interface CommentAnswerServiceDto extends ReadWriteService<Comment, Long> {

    List<CommentDto> getCommentsToAnswer(Long answerId);

    boolean hasUserToCommentAnswer(Long answerId, Long userId);
}

package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.dto.UserTagsDto;
import com.myfrei.qa.platform.models.entity.question.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends ReadWriteDAO<Tag, Long> {
    Optional<Long> getTagIdByName(String name);
    List<UserTagsDto> getUserTags(Long userId, Integer page);
    Long getCountOfUserTags(Long userId);
    Tag getTagByName(String tagName);
}

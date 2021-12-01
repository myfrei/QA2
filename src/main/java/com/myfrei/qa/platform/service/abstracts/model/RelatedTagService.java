package com.myfrei.qa.platform.service.abstracts.model;

import com.myfrei.qa.platform.models.entity.question.RelatedTag;

public interface RelatedTagService extends ReadWriteService<RelatedTag, Long> {
    void deleteRelTagsByTagId(Long tagId);
}

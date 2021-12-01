package com.myfrei.qa.platform.dao.abstracts.model;

import com.myfrei.qa.platform.models.entity.question.RelatedTag;

public interface RelatedTagDAO extends ReadWriteDAO<RelatedTag, Long> {
    void deleteRelTagsByTagId(Long tagId);
}

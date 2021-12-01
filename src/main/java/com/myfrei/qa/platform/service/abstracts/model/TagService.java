package com.myfrei.qa.platform.service.abstracts.model;

import com.myfrei.qa.platform.models.entity.question.Tag;

public interface TagService extends ReadWriteService<Tag, Long> {
    void persistChildTag(Tag tag, Long mainTagId);

}

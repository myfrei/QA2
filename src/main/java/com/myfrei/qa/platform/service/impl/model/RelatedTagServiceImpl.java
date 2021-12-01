package com.myfrei.qa.platform.service.impl.model;

import com.myfrei.qa.platform.dao.abstracts.model.RelatedTagDAO;
import com.myfrei.qa.platform.models.entity.question.RelatedTag;
import com.myfrei.qa.platform.service.abstracts.model.RelatedTagService;
import org.springframework.stereotype.Service;

@Service
public class RelatedTagServiceImpl extends ReadWriteServiceImpl<RelatedTag, Long> implements RelatedTagService {

    private final RelatedTagDAO relatedTagDAO;

    public RelatedTagServiceImpl(RelatedTagDAO relatedTagDAO) {
        super(relatedTagDAO);
        this.relatedTagDAO = relatedTagDAO;
    }

    @Override
    public void deleteRelTagsByTagId(Long tagId) {
        relatedTagDAO.deleteRelTagsByTagId(tagId);
    }
}

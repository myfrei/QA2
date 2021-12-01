package com.myfrei.qa.platform.service.statistics.impl;

import com.myfrei.qa.platform.dao.abstracts.model.TagDAO;
import com.myfrei.qa.platform.models.dto.UserStatisticDto;
import com.myfrei.qa.platform.service.statistics.abstracts.Tabs;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tags")
public class UsersTagsDto implements Tabs {
    private final TagDAO tagDAO;

    @Autowired
    public UsersTagsDto(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public UserStatisticDto getList(String sortType, Long userId, Integer page) {

        return UserStatisticDto.builder()
                .tagDtoList(new Pair<>(tagDAO.getCountOfUserTags(userId), tagDAO.getUserTags(userId, page)))
                .build();
    }
}


package com.myfrei.qa.platform.service.statistics.abstracts;

import com.myfrei.qa.platform.models.dto.UserStatisticDto;


public interface Tabs {
    UserStatisticDto getList(String sortType, Long userId, Integer page);
}


package com.myfrei.qa.platform.service.abstracts.dto;

import com.myfrei.qa.platform.models.dto.UserDto;
import com.myfrei.qa.platform.models.dto.UserStatisticDto;

public interface UserStatisticDtoService {
    UserStatisticDto getUserStatistic(UserDto user, String tab, String sort, Integer page);
}


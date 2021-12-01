package com.myfrei.qa.platform.webapp.converter;

import com.myfrei.qa.platform.models.dto.TagDto;
import com.myfrei.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TagConverter {

    public abstract TagDto tagToDto (Tag tag);

    public abstract Tag dtoToTag (TagDto tagDto);
}

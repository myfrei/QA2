package com.myfrei.qa.platform.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserTagsDto {

    private Long tagId;

    private String tagName;

    private String tagDescription;

    private Long countOfTag;
}

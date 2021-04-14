package com.jinseong.soft.modules.link.dto;

import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 링크 생성, 수정 요청 정보
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponseData {
    private Long id;

    private String title;

    private String linkURL;

    private String description;

    private String category;

    private String type;

    private String user;

    private List<String> tags;

    public static LinkResponseData convertLinkToLinkData(Link link) {
        List<String> tags = link.getTags()
                .stream()
                .map(Tag::getTitle)
                .collect(Collectors.toList());

        return LinkResponseData.builder()
                .id(link.getId())
                .title(link.getTitle())
                .linkURL(link.getLinkURL())
                .description(link.getDescription())
                .category(link.getCategory().getTitle())
                .type(link.getType().getTitle())
                .user(link.getCreatedUser().getName())
                .tags(tags)
                .build();
    }
}

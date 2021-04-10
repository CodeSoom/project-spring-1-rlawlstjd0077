package com.jinseong.soft.dto;

import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.Tag;
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
public class LinkData {
    private Long id;

    private String title;

    private String linkURL;

    private String description;

    private String category;

    private String type;

    private List<String> tags;

    public static LinkData convertLinkToLinkData(Link link) {
        List<String> tags = link.getTags()
                .stream()
                .map(Tag::getTitle)
                .collect(Collectors.toList());

        return LinkData.builder()
                .id(link.getId())
                .title(link.getTitle())
                .linkURL(link.getLinkURL())
                .description(link.getDescription())
                .category(link.getCategory().getTitle())
                .type(link.getType().getTitle())
                .tags(tags)
                .build();
    }
}

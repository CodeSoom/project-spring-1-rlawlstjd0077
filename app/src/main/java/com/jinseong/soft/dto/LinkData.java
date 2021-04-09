package com.jinseong.soft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 링크 생성, 수정 요청 정보
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkData {
    private String title;

    private String linkURL;

    private String description;

    private String category;

    private String type;

    private List<String> tags;
}

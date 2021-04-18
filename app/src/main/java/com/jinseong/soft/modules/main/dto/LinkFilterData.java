package com.jinseong.soft.modules.main.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkFilterData {
    private List<String> categories = new ArrayList<>();

    private List<String> types = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

    private List<String> users = new ArrayList<>();
}

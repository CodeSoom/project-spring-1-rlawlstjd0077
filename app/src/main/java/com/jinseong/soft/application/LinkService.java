package com.jinseong.soft.application;

import com.jinseong.soft.domain.Link;
import java.util.ArrayList;
import java.util.List;

public class LinkService {
    private List<Link> links = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }
}

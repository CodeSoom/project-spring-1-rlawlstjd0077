package com.jinseong.soft.application;

import com.jinseong.soft.domain.Link;
import com.jinseong.soft.errors.LinkNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LinkService {
    private Map<Long, Link> links = new HashMap<>();
    private long idCounter = 0L;

    public List<Link> getLinks() {
        return new ArrayList<>(links.values());
    }

    public Link createLink(Link link) {
        links.put(idCounter++, link);
        return link;
    }

    public Link updateLink(Long id, Link updateSource) {
        Link link = findLink(id);
        link.changeWith(updateSource);
        return link;
    }

    public Link getLink(Long id) {
        Link link = findLink(id);
        return link;
    }

    private Link findLink(Long id) {
        return Optional.ofNullable(links.get(id))
                .orElseThrow(() -> new LinkNotFoundException(id));
    }

    public Link deleteLink(Long id) {
        Link link = findLink(id);
        links.remove(id);
        return link;
    }
}

package com.jinseong.soft.application;

import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.LinkRepository;
import com.jinseong.soft.domain.Tag;
import com.jinseong.soft.dto.LinkRequestData;
import com.jinseong.soft.errors.LinkNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LinkService {
    private final LinkRepository linkRepository;
    private final TagService tagService;

    public LinkService(LinkRepository linkRepository, TagService tagService) {
        this.linkRepository = linkRepository;
        this.tagService = tagService;
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public Link createLink(LinkRequestData linkRequestData) {
        Link link = convertRequestDataToLink(linkRequestData);
        return linkRepository.save(link);
    }

    public Link updateLink(Long id, LinkRequestData linkRequestData) {
        Link link = findLink(id);
        Link updateSource = convertRequestDataToLink(linkRequestData);

        link.changeWith(updateSource);
        return link;
    }

    public Link getLink(Long id) {
        return findLink(id);
    }

    public Link deleteLink(Long id) {
        Link link = findLink(id);
        linkRepository.delete(link);
        return link;
    }

    private Link convertRequestDataToLink(LinkRequestData requestData) {
        Set<Tag> tags = requestData.getTags()
                .stream()
                .map(tagService::getOrCreateTag)
                .collect(Collectors.toSet());

        return Link.builder()
                .title(requestData.getTitle())
                .tags(tags)
                .build();
    }

    private Link findLink(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }
}

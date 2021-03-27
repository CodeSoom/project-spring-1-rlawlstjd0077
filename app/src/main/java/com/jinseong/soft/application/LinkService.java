package com.jinseong.soft.application;

import com.jinseong.soft.domain.Link;
import com.jinseong.soft.domain.LinkRepository;
import com.jinseong.soft.errors.LinkNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public Link createLink(Link link) {
        return linkRepository.save(link);
    }

    public Link updateLink(Long id, Link updateSource) {
        Link link = findLink(id);
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

    private Link findLink(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }
}

package com.jinseong.soft.application;

import com.jinseong.soft.domain.*;
import com.jinseong.soft.dto.LinkRequestData;
import com.jinseong.soft.errors.LinkNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LinkService {
    private final LinkRepository linkRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final TagService tagService;
    private final LikeRepository likeRepository;

    public LinkService(LinkRepository linkRepository,
                       CategoryService categoryService,
                       TypeService typeService,
                       TagService tagService,
                       LikeRepository likeRepository) {
        this.linkRepository = linkRepository;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.likeRepository = likeRepository;
    }

    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public Link createLink(LinkRequestData linkRequestData, User user) {
        Link link = convertRequestDataToLink(linkRequestData);
        link.setCreatedUser(user);
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

    public void addLike(Long id, User user) {
        Link link = findLink(id);

        if (!isAlreadyLike(user, link)) {
            Like like = Like.builder()
                    .link(link)
                    .user(user)
                    .build();
            likeRepository.save(like);
            link.addLike(like);
        }
    }

    private boolean isAlreadyLike(User user, Link link) {
        return likeRepository.findByUserAndLink(user, link)
                .isPresent();
    }

    private Link convertRequestDataToLink(LinkRequestData requestData) {
        Category category = categoryService.getOrCreateCategory(
                requestData.getCategory()
        );

        Type type = typeService.getOrCreateType(
                requestData.getType()
        );

        Set<Tag> tags = requestData.getTags()
                .stream()
                .map(tagService::getOrCreateTag)
                .collect(Collectors.toSet());

        return Link.builder()
                .title(requestData.getTitle())
                .linkURL(requestData.getLinkURL())
                .description(requestData.getDescription())
                .category(category)
                .type(type)
                .tags(tags)
                .build();
    }

    private Link findLink(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }
}

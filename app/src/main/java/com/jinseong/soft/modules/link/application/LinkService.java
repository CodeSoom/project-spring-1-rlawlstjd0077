package com.jinseong.soft.modules.link.application;

import com.jinseong.soft.modules.category.application.CategoryService;
import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.like.domain.Like;
import com.jinseong.soft.modules.like.domain.LikeRepository;
import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.link.domain.LinkNotFoundException;
import com.jinseong.soft.modules.link.domain.LinkRepository;
import com.jinseong.soft.modules.link.dto.LinkRequestData;
import com.jinseong.soft.modules.main.dto.LinkFilterData;
import com.jinseong.soft.modules.tag.application.TagService;
import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.type.application.TypeService;
import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.user.domain.User;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 링크에 대한 비즈니스 로직을 제공합니다.
 */
@Service
@Transactional
public class LinkService {
    private final LinkRepository linkRepository;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final TagService tagService;
    private final LikeRepository likeRepository;
    private final LinkFilterProvider linkFilterProvider;

    public LinkService(LinkRepository linkRepository,
                       CategoryService categoryService,
                       TypeService typeService,
                       TagService tagService,
                       LikeRepository likeRepository,
                       LinkFilterProvider linkFilterProvider) {
        this.linkRepository = linkRepository;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.likeRepository = likeRepository;
        this.linkFilterProvider = linkFilterProvider;
    }

    /**
     * 존재하는 모든 링크 목록을 반환합니다.
     *
     * @return 링크 목록
     */
    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public Page<Link> getLinks(
            Pageable pageable,
            LinkFilterData filterData
    ) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Link> links;
        List<Link> allLinks = linkRepository.findAll();

        allLinks = linkFilterProvider.filterLinks(allLinks, filterData);

        if (allLinks.size() < startItem) {
            links = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allLinks.size());
            links = allLinks.subList(startItem, toIndex);
        }

        return new PageImpl<>(links, PageRequest.of(currentPage, pageSize), allLinks.size());
    }

    /**
     * 주어진 링크 정보, 링크 생성 유저로 링크를 생성한 뒤 반환합니다.
     *
     * @param linkRequestData 링크 정보
     * @param user            링크 생성 유저
     * @return 생성된 링크
     */
    public Link createLink(LinkRequestData linkRequestData, User user) {
        Link link = convertRequestDataToLink(linkRequestData);
        link.setCreatedUser(user);
        return linkRepository.save(link);
    }

    /**
     * 주어진 링크 식별자와 일치하는 링크를 전달된 링크 정보로 수정한 뒤 반환합니다.
     *
     * @param id              링크 식별자
     * @param linkRequestData 링크 수정 정보
     * @return 수정된 링크
     */
    public Link updateLink(Long id, LinkRequestData linkRequestData) {
        Link link = findLink(id);
        Link updateSource = convertRequestDataToLink(linkRequestData);

        link.changeWith(updateSource);
        return link;
    }

    /**
     * 대응되는 식별자의 링크를 반환합니다.
     *
     * @param id 링크 식별자
     * @return 일치하는 링크
     */
    public Link getLink(Long id) {
        return findLink(id);
    }

    /**
     * 대응되는 식별자의 링크를 삭제한 뒤 반환합니다.
     *
     * @param id 링크 식별자
     * @return 삭제된 링크
     */
    public Link deleteLink(Long id) {
        Link link = findLink(id);
        linkRepository.delete(link);
        return link;
    }

    /**
     * 대응되는 식별자의 링크에 좋아요를 추가합니다.
     *
     * @param id   링크 식별자
     * @param user 좋아요를 누른 유저
     */
    public boolean addLike(Long id, User user) {
        Link link = findLink(id);

        if (isAlreadyLike(user, link)) {
            return false;
        }

        Like like = Like.builder()
                .link(link)
                .user(user)
                .build();
        likeRepository.save(like);
        link.addLike(like);
        return true;
    }

    /**
     * 주어진 유저가 해당 링크에 좋아요를 추가한 적이 있다면 {@code true}, 그렇지 않으면 {@code false}를 반환합니다.
     *
     * @param user 좋아요를 추가한 유저
     * @param link 좋아요 추가 대상 링크
     * @return 좋아요 추가 이력 유무
     */
    private boolean isAlreadyLike(User user, Link link) {
        return likeRepository.findByUserAndLink(user, link)
                .isPresent();
    }

    /**
     * 전달된 링크 생성/수정 요청 정보를 링크 정보로 반환합니다.
     *
     * @param requestData 링크 생성/수정 요청 정보
     * @return 링크 정보
     */
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


    /**
     * 대응되는 식별자와 일치하는 링크를 반환합니다.
     *
     * @param id 링크 식별자
     * @return 링크
     * @throws LinkNotFoundException 식별자와 일치하는 링크를 찾지 못한 경우
     */
    private Link findLink(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));
    }
}

package com.jinseong.soft.controllers;

import com.jinseong.soft.application.CategoryService;
import com.jinseong.soft.application.LinkService;
import com.jinseong.soft.application.TagService;
import com.jinseong.soft.application.TypeService;
import com.jinseong.soft.domain.*;
import com.jinseong.soft.dto.LinkRequestData;
import com.jinseong.soft.dto.LinkResponseData;
import com.jinseong.soft.errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 링크 HTTP 요청 핸들러
 */
@RestController
@RequestMapping("/links")
public class LinkController {
    static final String CATEGORIES = "/categories";
    static final String TYPES = "/types";
    static final String TAGS = "/tags";

    private final LinkService linkService;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final TagService tagService;
    private final UserRepository userRepository;

    public LinkController(LinkService linkService,
                          CategoryService categoryService,
                          TypeService typeService,
                          TagService tagService,
                          UserRepository userRepository) {
        this.linkService = linkService;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.userRepository = userRepository;
    }

    /**
     * 존재하는 모든 링크 목록을 응답합니다.
     *
     * @return 링크 목록
     */
    @GetMapping
    public List<LinkResponseData> getLinks() {
        List<Link> links = linkService.getLinks();
        return links.stream()
                .map(LinkResponseData::convertLinkToLinkData)
                .collect(Collectors.toList());
    }

    /**
     * 대응되는 식별자의 링크를 응답합니다.
     *
     * @param id 링크 식별자
     * @return 링크
     */
    @GetMapping("{id}")
    public LinkResponseData getLink(@PathVariable Long id) {
        Link link = linkService.getLink(id);
        return LinkResponseData.convertLinkToLinkData(link);
    }


    /**
     * 주어진 링크 정보로 새로운 링크를 생성한 뒤 응답합니다.
     *
     * @param source 링크 정보
     * @return 생성된 링크
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinkResponseData createLink(@RequestBody LinkRequestData source) {
        User user = getRequestedUser();

        Link link = linkService.createLink(source, user);
        return LinkResponseData.convertLinkToLinkData(link);
    }

    /**
     * 대응되는 식별자의 링크를 주어진 링크 정보로 수정한 뒤 응답합니다.
     *
     * @param id     링크 식별자
     * @param source 링크 수정 정보
     * @return 수정된 링크
     */
    @PatchMapping("{id}")
    public LinkResponseData updateLink(@PathVariable Long id, @RequestBody LinkRequestData source) {
        Link link = linkService.updateLink(id, source);
        return LinkResponseData.convertLinkToLinkData(link);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
    }

    @GetMapping(CATEGORIES)
    public List<String> getCategories() {
        List<Category> categories = categoryService.getCategories();

        return categories.stream()
                .map(Category::getTitle)
                .collect(Collectors.toList());
    }

    @GetMapping(TYPES)
    public List<String> getTypes() {
        List<Type> types = typeService.getTypes();

        return types.stream()
                .map(Type::getTitle)
                .collect(Collectors.toList());
    }

    @GetMapping(TAGS)
    public List<String> getTags() {
        List<Tag> tags = tagService.getTags();

        return tags.stream()
                .map(Tag::getTitle)
                .collect(Collectors.toList());
    }

    private User getRequestedUser() {
        String email = getEmailFromAuthentication();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private String getEmailFromAuthentication() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}

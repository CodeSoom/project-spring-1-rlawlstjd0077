package com.jinseong.soft.controllers;

import com.jinseong.soft.application.LinkService;
import com.jinseong.soft.domain.Link;
import com.jinseong.soft.dto.LinkData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 링크 HTTP 요청 핸들러
 */
@RestController
@RequestMapping("/links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 존재하는 모든 링크 목록을 응답합니다.
     *
     * @return 링크 목록
     */
    @GetMapping
    public List<Link> getLinks() {
        return linkService.getLinks();
    }

    /**
     * 대응되는 식별자의 링크를 응답합니다.
     *
     * @param id 링크 식별자
     * @return 링크
     */
    @GetMapping("{id}")
    public Link getLink(@PathVariable Long id) {
        return linkService.getLink(id);
    }


    /**
     * 주어진 링크 정보로 새로운 링크를 생성한 뒤 응답합니다.
     *
     * @param link 링크 정보
     * @return 생성된 링크
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Link createLink(@RequestBody LinkData link) {
        return linkService.createLink(link);
    }

    /**
     * 대응되는 식별자의 링크를 주어진 링크 정보로 수정한 뒤 응답합니다.
     *
     * @param id     링크 식별자
     * @param source 링크 수정 정보
     * @return 수정된 링크
     */
    @PatchMapping("{id}")
    public Link updateLink(@PathVariable Long id, @RequestBody LinkData source) {
        return linkService.updateLink(id, source);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
    }
}

package com.jinseong.soft.modules.main;

import com.jinseong.soft.modules.link.application.LinkService;
import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.link.dto.LinkResponseData;
import com.jinseong.soft.modules.main.dto.LinkFilterData;
import com.jinseong.soft.modules.tag.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 메인 Page의 HTTP 요청 핸들러.
 */
@Controller
public class MainController {
    private final LinkService linkService;

    public MainController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public String index(Model model,
                        Principal principal,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam("filter") Optional<LinkFilterData> linkFilterData
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        LinkFilterData filterData = linkFilterData.orElse(new LinkFilterData());

        Page<Link> linksPage = linkService.getLinks(
                PageRequest.of(currentPage - 1, pageSize),
                filterData
        );

        List<LinkResponseData> linkDatas = linksPage
                .stream()
                .map(LinkResponseData::convertLinkToLinkData)
                .collect(Collectors.toList());

        Page<LinkResponseData> linksDataPage = new PageImpl<>(
                linkDatas,
                linksPage.getPageable(),
                linksPage.getTotalElements()
        );

        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }

        model.addAttribute("links", linksDataPage);

        int totalPages = linksDataPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("categories", filterData.getCategories());
        model.addAttribute("types", filterData.getTypes());
        model.addAttribute("tags", filterData.getTags());
        model.addAttribute("users", filterData.getUsers());

        return "index";
    }

    @GetMapping("update-link/{id}")
    public String updateLink(@PathVariable Long id, Model model) {
        Link link = linkService.getLink(id);
        String tags = link.getTags()
                .stream()
                .map(Tag::getTitle)
                .collect(Collectors.joining(","));

        model.addAttribute("link", LinkResponseData.convertLinkToLinkData(link));
        model.addAttribute("tags", tags);

        return "update-link";
    }
}

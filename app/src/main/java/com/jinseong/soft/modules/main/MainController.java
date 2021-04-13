package com.jinseong.soft.modules.main;

import com.jinseong.soft.modules.link.application.LinkService;
import com.jinseong.soft.modules.link.dto.LinkData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

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
    public String index(Model model) {
        List<LinkData> links = linkService.getLinks()
                .stream()
                .map(LinkData::convertLinkToLinkData)
                .collect(Collectors.toList());

        model.addAttribute("links", links);
        return "index";
    }

    @GetMapping("create-link")
    public String createLink() {
        return "create-link";
    }

    @GetMapping("update-link/{id}")
    public String updateLink(@PathVariable Long id, Model model) {
        model.addAttribute("link", linkService.getLink(id));
        return "update-link";
    }
}

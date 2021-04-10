package com.jinseong.soft.controllers;

import com.jinseong.soft.application.LinkService;
import com.jinseong.soft.dto.LinkData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    private final LinkService linkService;

    public IndexController(LinkService linkService) {
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

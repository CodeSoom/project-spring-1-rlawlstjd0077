package com.jinseong.soft.controllers;

import com.jinseong.soft.application.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private final LinkService linkService;

    public IndexController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("links", linkService.getLinks());
        return "index";
    }

    @GetMapping("create-link")
    public String createLink() {
        return "create-link";
    }
}

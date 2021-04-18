package com.jinseong.soft.modules.link.application;

import com.jinseong.soft.modules.category.application.CategoryService;
import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.link.domain.Link;
import com.jinseong.soft.modules.main.dto.LinkFilterData;
import com.jinseong.soft.modules.tag.application.TagService;
import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.type.application.TypeService;
import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.user.application.UserService;
import com.jinseong.soft.modules.user.domain.User;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LinkFilterProvider {
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final TagService tagService;
    private final UserService userService;

    public LinkFilterProvider(
            CategoryService categoryService,
            TypeService typeService,
            TagService tagService,
            UserService userService
    ) {
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.userService = userService;
    }

    public List<Link> filterLinks(
            List<Link> links,
            LinkFilterData filterData
    ) {
        links = filterCategoryLinkData(links, filterData);
        links = filterTypesLinkData(links, filterData);
        links = filterTagsLinkData(links, filterData);
        links = filterUsersLinkData(links, filterData);
        return links;
    }

    private List<Link> filterCategoryLinkData(
            List<Link> links,
            LinkFilterData filterData
    ) {
        if (filterData.getCategories().isEmpty()) {
            return links;
        }

        List<Category> categories = filterData.getCategories()
                .stream()
                .map(categoryService::getOrCreateCategory)
                .collect(Collectors.toList());

        Predicate<Link> predicateFilterCategory = link -> categories.contains(link.getCategory());
        List<Link> filteredLinks = collectLinks(links, predicateFilterCategory);

        return filteredLinks;
    }

    private List<Link> filterTypesLinkData(
            List<Link> links,
            LinkFilterData filterData
    ) {
        if (filterData.getTypes().isEmpty()) {
            return links;
        }

        List<Type> types = filterData.getTypes()
                .stream()
                .map(typeService::getOrCreateType)
                .collect(Collectors.toList());

        Predicate<Link> predicateFilterType = link -> types.contains(link.getType());
        List<Link> filteredLinks = collectLinks(links, predicateFilterType);

        return filteredLinks;
    }

    private List<Link> filterTagsLinkData(
            List<Link> links,
            LinkFilterData filterData
    ) {
        if (filterData.getTags().isEmpty()) {
            return links;
        }

        List<Tag> tags = filterData.getTags()
                .stream()
                .map(tagService::getOrCreateTag)
                .collect(Collectors.toList());

        Predicate<Link> predicateFilterTags = link -> {
            tags.retainAll(link.getTags());
            return !tags.isEmpty();
        };

        List<Link> filteredLinks = collectLinks(links, predicateFilterTags);

        return filteredLinks;
    }

    private List<Link> filterUsersLinkData(
            List<Link> links,
            LinkFilterData filterData
    ) {
        if (filterData.getUsers().isEmpty()) {
            return links;
        }

        List<User> users = filterData.getUsers()
                .stream()
                .map(userService::findByUsername)
                .collect(Collectors.toList());

        Predicate<Link> predicateFilterUser = link -> users.contains(link.getCreatedUser());
        List<Link> filteredLinks = collectLinks(links, predicateFilterUser);

        return filteredLinks;
    }

    private List<Link> collectLinks(
            List<Link> links,
            Predicate<Link> predicate
    ) {
        List<Link> filteredLinks = links
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return filteredLinks;
    }
}

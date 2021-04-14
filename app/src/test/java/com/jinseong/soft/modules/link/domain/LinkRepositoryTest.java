package com.jinseong.soft.modules.link.domain;

import com.jinseong.soft.fixtures.LinkTestFixture;
import com.jinseong.soft.fixtures.UserTestFixture;
import com.jinseong.soft.modules.category.domain.Category;
import com.jinseong.soft.modules.category.domain.CategoryRepository;
import com.jinseong.soft.modules.link.dto.LinkRequestData;
import com.jinseong.soft.modules.tag.domain.Tag;
import com.jinseong.soft.modules.tag.domain.TagRepository;
import com.jinseong.soft.modules.type.domain.Type;
import com.jinseong.soft.modules.type.domain.TypeRepository;
import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class LinkRepositoryTest {
    @Autowired
    LinkRepository linkRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void testSaveLinkRepository() {
        LinkRequestData linkRequestData = LinkTestFixture.LINK_REQUEST;

        Category category =
                categoryRepository.save(LinkTestFixture.CATEGORY);
        Type type =
                typeRepository.save(LinkTestFixture.TYPE);
        Tag tag =
                tagRepository.save(LinkTestFixture.TAG);

        User user = userRepository.save(UserTestFixture.EXIST_USER);

        Link source = Link.builder()
                .title(linkRequestData.getTitle())
                .linkURL(linkRequestData.getLinkURL())
                .description(linkRequestData.getDescription())
                .category(category)
                .type(type)
                .tags(Collections.singleton(tag))
                .createdUser(user)
                .build();

        Long id = linkRepository.save(source).getId();

        Link link = linkRepository.findById(id).get();

        assertThat(link.getCategory()).isEqualTo(category);
        assertThat(link.getType()).isEqualTo(type);
        assertThat(link.getTags()).contains(tag);
        assertThat(link.getCreatedUser()).isEqualTo(user);
        System.out.println("====" + link.getCreateAt());
    }
}

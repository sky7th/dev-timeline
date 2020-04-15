package com.sky7th.devtimeline.web.repository.recruitPost;

import com.sky7th.devtimeline.core.domain.linkpost.LinkPost;
import com.sky7th.devtimeline.web.repository.LinkPost.LinkPostWebRepository;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkPostWebRepositoryTest {

    @Autowired
    private LinkPostWebRepository linkPostWebRepository;

    @Test
    public void link_post을_조회한다() {
        //given
        PostSearchForm searchForm = PostSearchForm.builder()
                .offset(0L)
                .limit(10L)
                .build();

        linkPostWebRepository.save(LinkPost.builder()
                .title("test title")
                .content("test content")
                .build());
        //when
        List<LinkPost> linkPosts = linkPostWebRepository.findBySearchForm(searchForm);

        for (LinkPost linkPost : linkPosts) {
            System.out.println(
                    linkPost.getTitle()
                    + " "+ linkPost.getContent()
            );
        }

        //then
        assertThat(linkPosts.size() > 0);
    }


}

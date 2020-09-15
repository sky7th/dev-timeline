//package com.sky7th.devtimeline.api.repository.recruitPost;
//
//import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
//import com.sky7th.devtimeline.core.domain.tag.Tag;
//import com.sky7th.devtimeline.core.domain.tag.TagRepository;
//import com.sky7th.devtimeline.api.repository.LinkPost.LinkPostWebRepository;
//import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class LinkPostWebRepositoryTest {
//
//    @Autowired
//    private LinkPostWebRepository linkPostWebRepository;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    @Test
//    public void link_post을_조회한다() {
//        //given
//        PostSearchForm searchForm = PostSearchForm.builder()
//                .offset(0L)
//                .limit(10L)
//                .build();
//
//        List<Tag> tags = new ArrayList<>();
//        tags.add(Tag.builder().name("tag1").build());
//        tags.add(Tag.builder().name("tag2").build());
//        tags.add(Tag.builder().name("tag3").build());
//
//        List<LinkPost> linkPosts = new ArrayList<>();
//
//        for (int i=0; i<3; i++) {
//            LinkPost linkPost = LinkPost.builder()
//                    .title("test title " + i)
//                    .content("test content " + i)
//                    .build();
//            for (int j=0; j<4; j++) {
//                linkPost.addTags(Tag.builder().name("tag "+ j).build());
//            }
//            linkPosts.add(linkPost);
//        }
//        linkPostWebRepository.saveAll(linkPosts);
//
//        //when
////        List<LinkPost> resultLinkPosts = linkPostWebRepository.findBySearchForm(searchForm);
////
////        for (LinkPost linkPost : resultLinkPosts) {
////            System.out.println(
////                    linkPost.getId()
////                    + " "+ linkPost.getTitle()
////                    + " "+ linkPost.getContent()
////                    + " "+ linkPost.getTags()
////            );
////        }
////
////        //then
////        assertThat(resultLinkPosts.size() > 0);
//    }
//
//
//}

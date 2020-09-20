//package com.sky7th.devtimeline.service;
//
//import com.sky7th.devtimeline.core.domain.post.linkpost.domain.LinkPost;
//import com.sky7th.devtimeline.core.domain.post.linkpost.domain.LinkPostRepository;
//import com.sky7th.devtimeline.core.domain.user.domain.User;
//import com.sky7th.devtimeline.core.domain.user.domain.UserRepository;
//import com.sky7th.devtimeline.core.domain.user.UserRole;
//import com.sky7th.devtimeline.api.security.UserPrincipal;
//import com.sky7th.devtimeline.api.linkpost.service.LinkPostService;
//import com.sky7th.devtimeline.api.service.dto.LinkPostDto;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class LinkPostServiceTest {
//
//    @Autowired
//    private LinkPostService linkPostService;
//
//    @Autowired
//    private LinkPostRepository linkPostRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void link_post_생성하고_수정_후_id로_조회한다() {
//        //given
//        LinkPostDto linkPostDto = new LinkPostDto();
//        linkPostDto.setTitle("title 1");
//        linkPostDto.setTags(new ArrayList<>());
//        linkPostDto.setContent("content 1");
//
//        UserPrincipal userPrincipal = UserPrincipal.builder().id(1L).build();
//
//        //when
//        userRepository.save(User.builder().email("123@test.com").name("test 1").emailVerified(false).userRole(UserRole.USER).build());
//        linkPostService.save(linkPostDto, userPrincipal);
////        LinkPostViewItem item = linkPostService.findOne(1L);
//        LinkPost item1 = linkPostRepository.findById(1L).orElse(null);
//
//        linkPostDto.setTitle("title 2");
//        linkPostDto.setContent("content 2");
//        linkPostService.update(1L, linkPostDto, userPrincipal);
//        LinkPost item2 = linkPostRepository.findById(1L).orElse(null);
//
//        //then
//        System.out.println(item1.getId() + " "+ item1.getTitle() + " "+ item1.getContent() + " "+ item1.getUser().getEmail());
//        System.out.println(item2.getId() + " "+ item2.getTitle() + " "+ item2.getContent() + " "+ item2.getUser().getEmail());
//
//        assertThat(!item1.getTitle().equals(item2.getTitle()));
//
//    }
//
//}

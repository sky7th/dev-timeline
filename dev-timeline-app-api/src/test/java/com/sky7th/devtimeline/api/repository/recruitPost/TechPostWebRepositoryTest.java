//package com.sky7th.devtimeline.api.repository.recruitPost;
//
//import com.sky7th.devtimeline.api.techpost.repository.TechPostWebRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TechPostWebRepositoryTest {
//
//    @Autowired
//    private TechPostWebRepository techPostWebRepository;
//
//    @Test
//    public void tech_post을_desc으로_정렬하여_조회한다() {
//        //given
//
//        //when
////        List<TechPost> recruitPosts = techPostWebRepository.findAllLimitDesc(0, 10);
////
////        for (TechPost recruitPost : recruitPosts) {
////            System.out.println(
////                    recruitPost.getCompanyUrl().getCompany().getCompanyType().getName()
////                    + " "+ recruitPost.getCompanyUrl().getCompanyUrlType()
////                    + " "+ recruitPost.getTitle()
////                    + " "+ recruitPost.getAuthor()
////                    + " "+ recruitPost.getThumbnailUrl()
////                    + " "+ recruitPost.getContentUrl()
////                    + " "+ recruitPost.getDate()
////            );
////        }
////
////        //then
////        assertThat(recruitPosts.size() > 0);
//    }
//
//
//}

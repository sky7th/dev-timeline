//package com.sky7th.devtimeline.api.repository.recruitPost;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.sky7th.devtimeline.core.domain.post.recruitpost.domain.RecruitPost;
//import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static com.sky7th.devtimeline.core.domain.post.linkpost.QLinkPost.linkPost;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RecruitPostWebRepositoryTest {
//
//    @Autowired
//    private RecruitPostWebRepository recruitPostWebRepository;
//
//    @Autowired
//    JPAQueryFactory queryFactory;
//
//    @Test
//    public void fetchJoin_썼을_때_offset_limit_작동하는지_확인() {
//
//        PostSearchForm searchForm = PostSearchForm.builder()
//                .offset(0L)
//                .limit(10L)
//                .build();
//
//        queryFactory
//                .selectFrom(linkPost)
//                .leftJoin(linkPost.user).fetchJoin()
//                .leftJoin(linkPost.tags).fetchJoin()
//                .offset(searchForm.getOffset())
//                .limit(searchForm.getLimit())
//                .orderBy(linkPost.createdDate.desc())
//                .fetch();
//    }
//
//    @Test
//    public void recruit_post을_desc으로_정렬하여_조회한다() {
//        //given
//        PostSearchForm searchForm = PostSearchForm.builder()
//                .offset(0L)
//                .limit(10L)
//                .build();
//        //when
//        List<RecruitPost> recruitPosts = recruitPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(searchForm);
//
//        for (RecruitPost recruitPost : recruitPosts) {
//            System.out.println(
//                    recruitPost.getCompanyUrl().getCompany().getCompanyType().getName()
//                    + " "+ recruitPost.getCompanyUrl().getCompanyUrlType()
//                    + " "+ recruitPost.getTitle()
//                    + " "+ recruitPost.getContentUrl()
//                    + " "+ recruitPost.getClosingDate()
//            );
//        }
//
//        //then
//        assertThat(recruitPosts.size() > 0);
//    }
//
//
//}

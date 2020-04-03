package com.sky7th.devtimeline.web.repository.recruitPost;

import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecruitPostWebRepositoryTest {

    @Autowired
    private RecruitPostWebRepository recruitPostWebRepository;

    @Test
    public void recruit_post을_desc으로_정렬하여_조회한다() {
        //given

        //when
        List<RecruitPost> recruitPosts = recruitPostWebRepository.findAllLimitDesc(0, 10);

        for (RecruitPost recruitPost : recruitPosts) {
            System.out.println(
                    recruitPost.getCompanyUrl().getCompany().getCompanyType().getName()
                    + " "+ recruitPost.getCompanyUrl().getCompanyUrlType()
                    + " "+ recruitPost.getTitle()
                    + " "+ recruitPost.getContentUrl()
                    + " "+ recruitPost.getClosingDate()
            );
        }

        //then
        assertThat(recruitPosts.size() > 0);
    }


}

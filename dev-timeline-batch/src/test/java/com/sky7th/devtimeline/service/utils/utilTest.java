//package com.sky7th.devtimeline.service.utils;
//
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.core.domain.recruitpost.domain.RecruitPostRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.UUID;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class utilTest {
//
//    @Autowired
//    RecruitPostRepository recruitPostRepository;
//
//    @Test
//    void test() {
//        String contentUrl = "https://recruit.navercorp.com/naver/job/detail/developer?annoId=20003097&classId=&jobId=&entTypeCd=&searchTxt=&searchSysComCd=";
//        Pattern p = Pattern.compile("(?<=annoId=).+(?=&classId)");
//        Matcher matcher = p.matcher(contentUrl);
//        if (matcher.find()) {
//            System.out.println(matcher.group());
//        }
//    }
//
//    @Test
//    void test2() {
//        String contentUrl = "https://tech.kakao.com/2018/09/12/code-festival-2018-round-2/";
//        Pattern p = Pattern.compile("(?<=com/).+/.+/.+/.+(?=/)");
//        Matcher matcher = p.matcher(contentUrl);
//        if (matcher.find()) {
//            System.out.println(matcher.group());
//        }
//    }
//
//    @Test
//    void test3() {
//        UUID id1 = UUID.nameUUIDFromBytes("NAVER-RECRUIT-20004178".getBytes());
//        UUID id2 = UUID.nameUUIDFromBytes("NAVER-RECRUIT-20004178".getBytes());
//
//        System.out.println(id1.toString());
//        System.out.println(id2.toString());
//    }
//
//    @Test
//    void test4() {
//        CrawlingDto crawlingDto = CrawlingDto.builder()
//                .crawlId("NAVER-RECRUIT-20004178")
//                .author("lee")
//                .closingDate("")
//                .thumbnailUrl("")
//                .title("title")
//                .description("desc")
//                .contentUrl("http")
//                .build();
////        recruitPostRepository.save(crawlingDto.toRecruitPost());
////        RecruitPost recruitPost = recruitPostRepository.findById(1L).orElse(null);
////        assert recruitPost != null;
////        System.out.println(recruitPost.getPost().getCrawlId());
//    }
//}

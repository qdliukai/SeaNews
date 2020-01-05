package com.liukai.test;


import com.liukai.seanews.domain.RecommendDetail;
import com.liukai.seanews.domain.RecommendResult;
import com.liukai.seanews.domain.User;
import com.liukai.seanews.service.RecommendService;
import com.liukai.seanews.service.UserService;
import com.liukai.seanews.util.JsonUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class JsonTest {

    @Test
    public void test1(){
        Map<String, String> userLaberMap = new HashMap<>();
        userLaberMap.put("name", "13");
        userLaberMap.put("user", "10");
        userLaberMap.put("a", "6");
        userLaberMap.put("b", "3");
        userLaberMap.put("c", "6");
        userLaberMap.put("d", "5");
        userLaberMap.put("海洋文化", "2");
        String res = JsonUtil.mapToJsonStr(userLaberMap);
        System.out.println(res);
    }
    @Test
    public void test2(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        UserService us = (UserService)ac.getBean("userServiceImpl");
        RecommendService rrs = (RecommendService)ac.getBean("recommendServiceImpl");
        RecommendResult rrr = rrs.selectLatestRecommend();
        System.out.println(rrr.getRecommendType());
        User u = new User();
        u = us.selectOne("Peter");
        System.out.println(u.getUserName());

    }

    @Test
    public void test3(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        RecommendService rrs = (RecommendService)ac.getBean("recommendServiceImpl");
        RecommendDetail rrr = rrs.selectRecommendDetail("123");
        System.out.println(rrr.getRecommendKey());
        System.out.println(rrr.getSummary());
        System.out.println(rrr.getTitle());
    }

    @Test
    public void rest(){
        List<String> list = new ArrayList<>(Arrays.asList("a","b","c","f","b","e","d"));
        System.out.println(list);
        list.remove("b");
        System.out.println(list);
    }

}

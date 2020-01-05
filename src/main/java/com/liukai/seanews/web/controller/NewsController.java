package com.liukai.seanews.web.controller;

import com.liukai.seanews.domain.Page;
import com.liukai.seanews.domain.RecommendDetail;
import com.liukai.seanews.domain.RecommendResult;
import com.liukai.seanews.service.RecommendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.*;

@Controller
public class NewsController {

    @Resource(name = "recommendServiceImpl")
    private RecommendService rrs;

    /**
     * 显示最新新闻
     * @return
     */
    @RequestMapping("/index")
    public String findAll(Page page, Model model){
        Map<String, String> latestNews = new HashMap<>();
        latestNews.put("html/news_d.html", "大力发展海洋经济 举力建设依海强市");
        latestNews.put("html/news_d2.html", "助力新旧动能转换 威海奏响经略海洋强音");
        latestNews.put("html/news_d3.html", "燃蓝色引擎 激活蓝色经济");
        latestNews.put("html/news_d4.html", "山东省威海市创新驱动 打造海洋经济强区");
        latestNews.put("html/news_d5.html", "精准对接产业链 青岛加速海洋科技成果变产品");
        latestNews.put("html/news_d6.html", "海洋强国建设重点推动海洋经济高质量发展");
        Map<String, String> weektNews = new HashMap<>();
        weektNews.put("html/news_d.html", "22名专家智力支持辽宁省大连“海上天路”建设");
        weektNews.put("html/news_d2.html", "国内首座深远海智能化网箱完成水下倾斜手动阀手动阀阿斯蒂芬撒地方试验");
        weektNews.put("html/news_d3.html", "海洋文化特色引领 青岛滋养海洋文化根脉");
        weektNews.put("html/news_d4.html", "青岛将迎海上旅游高峰 海事部门发布安全提醒");
        weektNews.put("html/news_d5.html", "中国第一代航天远洋测量船退出海上测控序列");
        weektNews.put("html/news_d6.html", "精准对接产业链 青岛加速海洋科技成果变产品");
        model.addAttribute("latestNews", latestNews);
        model.addAttribute("weekNews", weektNews);
        Page pageRes = fen(page);
        model.addAttribute("paging", pageRes);
        return "index";
    }

    /**
     * 分页函数
     * @param page
     * @return
     */
    public Page fen(Page page) {
        int pageSize = 7;
        try {
            //查询出的list数据
            List<RecommendDetail> showdata = getLatestRResult();
            //刚开始的页面为第一页
            if (page.getCurrentPage() == null) {
                page.setCurrentPage(1);
            } else {
                page.setCurrentPage(page.getCurrentPage());
            }
            //设置每页数据为十条
            page.setPageSize(pageSize);
            //每页的开始数
            page.setStar((page.getCurrentPage() - 1) * page.getPageSize());
            //list的大小
            int count = showdata.size();
            page.setTotal(count);
            //设置总页数
            page.setTotalPage(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
            //对list进行截取
            page.setDataList(showdata.subList(page.getStar(), count - page.getStar() > page.getPageSize() ? page.getStar() + page.getPageSize() : count));
            //设置作用域
        }catch (Exception e){
            e.printStackTrace();
        }
        return page;
    }

    /**
     * 获取推荐的资讯的详情函数
     * @return
     */
    public List<RecommendDetail> getLatestRResult() {
        RecommendResult recommendResult = rrs.selectLatestRecommend();
        String recommendId = recommendResult.getRecommendId();
        String userId = recommendResult.getUserId();
        String recommendResultList = recommendResult.getRecommendResult();
        String recommendType = recommendResult.getRecommendType();
        String recommendTime = recommendResult.getRecommendTime();
        recommendResultList = recommendResultList.substring(1, recommendResultList.length()-1);
        List<String> list = Arrays.asList(recommendResultList.split(","));
        List<RecommendDetail> resultList = new ArrayList<>();
        for (String s : list){
            String key = s.split("=")[0].trim();
            RecommendDetail recommendDetail = rrs.selectRecommendDetail(key);
            resultList.add(recommendDetail);
        }
        return resultList;
    }


}

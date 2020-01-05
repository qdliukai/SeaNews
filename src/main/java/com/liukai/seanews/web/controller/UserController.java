package com.liukai.seanews.web.controller;

import com.liukai.seanews.domain.RecommendDetail;
import com.liukai.seanews.domain.Task;
import com.liukai.seanews.domain.User;
import com.liukai.seanews.service.RecommendService;
import com.liukai.seanews.service.UserService;
import com.liukai.seanews.util.DateTimeUtil;
import com.liukai.seanews.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {

    @Resource(name = "userServiceImpl")
    private UserService us;

    @Resource(name = "recommendServiceImpl")
    private RecommendService rrs;

    private static List<String> userCollectList;
    private static Map<String, String> userLabelMap;
    /**
     * 查询全部用户
     * @return
     */
    @RequestMapping("/user/findAll")
    public String findAll(Model model) {
        List<User> list = us.selectAll();
        model.addAttribute("allUsers", list);
        return "user/userList";
    }

    /**
     * 删除指定用户
     * @param req
     * @return
     */
    @RequestMapping("/user/deleteUser")
    public String deleteUser(HttpServletRequest req) {
        String userName = req.getParameter("userName");
        us.delete(userName);
        return "redirect:/user/findAll";
    }

    /**
     * 插入或更新用户
     * @param u
     * @return
     */
    @RequestMapping(value = "/user/userSave", method = RequestMethod.POST)
    public String saveUser(User u) {
        us.save(u);
        return "redirect:/user/findAll";
    }

    /**
     * 检查登录用户是否存在以及合法性
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkUser(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        User user = null;
        user = us.selectOne(userName);
        if (user == null) {
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        } else {
            if (!userPass.equals(user.getUserPassword())) {
                return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
            } else {
                session.setAttribute("user", user);
                return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
            }
        }
    }

    /**
     * 用户退出，删除session中记录
     * @param session
     * @return
     */
    @RequestMapping(value = "/userLogout", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userLogout(HttpSession session) {
        session.removeAttribute("user");
        return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
    }

    /**
     * 获取用户标签，用户词云显示
     * 如果用户未登录，设置初始值
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserLabel", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserLabel(HttpServletRequest request, HttpSession session) {
        //System.out.println("获取用户标签方法");
        String jsonStr = null;
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> userLaberMap = new HashMap<>();
            userLaberMap.put("尚未登录", "90");
            userLaberMap.put("请先登录", "90");
            jsonStr = JsonUtil.mapToJsonStr(userLaberMap);
            System.out.println(jsonStr);
        } else {
            jsonStr = user.getUserModel();
        }
        return jsonStr;
    }

    /**
     * 用户收藏
     * 用户点击按钮收藏海洋资讯时触发
     * @param request
     * @return
     */
    @RequestMapping(value = "/userCollect", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userCollect(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String collectKey = request.getParameter("collectKey");
        User user = us.selectOne(userName);
        if (user == null) {
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        } else {
            // 先查询数据库获取用户目前收藏
            String userCollectStr = user.getUserCollect();
            // 然后判断当前海洋资讯是否已经收藏
            if (userCollectStr.contains(collectKey)){
                return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
            }else {
                // 如果没有收藏那么添加新的收藏，插入数据库更新用户收藏
                userCollectStr = userCollectStr + "," + collectKey;
                User user1 = new User();
                user1.setUserName(userName);
                user1.setUserCollect(userCollectStr);
                us.updateUserCollect(user1);
                return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
            }
        }
    }

    /**
     * 用户收藏编辑
     * 根据用户名称查询用户收藏，存储到列表中
     * @param request
     * @return
     */
    @RequestMapping(value = "/collectEdit", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String collectEdit(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        User user = us.selectOne(userName);
        if (user == null) {
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        } else {
            String userCollectStr = user.getUserCollect();
            userCollectList = new ArrayList<>(Arrays.asList(userCollectStr.split(",")));
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
        }
    }

    /**
     * 展示用户收藏
     * @param model
     * @return
     */
    @RequestMapping(value = "/showCollect", produces = "text/html;charset=UTF-8")
    public String showCollect(Model model) {
        List<RecommendDetail> resultList = new ArrayList<>();
        // 根据collectEdit()查询出的用户收藏的key，查询数据库找到收藏的资讯的详细信息
        for (String s : userCollectList){
            String key = s.split("=")[0].trim();
            RecommendDetail recommendDetail = rrs.selectRecommendDetail(key);
            // System.out.println(recommendDetail.getTitle());
            resultList.add(recommendDetail);
        }
        // 将收藏的资讯的详细信息bean List添加到模型中
        model.addAttribute("userCollectList", resultList);
        return "/collectEditPage";
    }

    /**
     * 删除更新用户收藏
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateUserCollect", produces = "text/html;charset=UTF-8")
    public String updateUserCollect(HttpServletRequest request, HttpSession session) {
        User userSession = (User) session.getAttribute("user");
        String userName = userSession.getUserName();
        String collectId = request.getParameter("collectId");
        //System.out.println(userCollectList);
        // 遍历用户收藏，从用户收藏列表中删除该收藏
        Iterator<String> it = userCollectList.iterator();
        while (it.hasNext()){
            if (it.next().equals(collectId))
                it.remove();
        }
        String userCollect = StringUtils.join(userCollectList, ",");
        //System.out.println(userCollect);
        User user = new User();
        user.setUserName(userName);
        user.setUserCollect(userCollect);
        us.updateUserCollect(user);
        return "redirect:/showCollect";
    }

    /**
     * 查询用户兴趣标签
     * @param request
     * @return
     */
    @RequestMapping(value = "/labelEdit", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String labelEdit(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        //System.out.println(userName);
        User user = us.selectOne(userName);
        if (user == null) {
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        } else {
            // 查询用户兴趣标签，存储到HashMap中
            String userLabelStr = user.getUserModel();
            userLabelStr = userLabelStr.substring(1, userLabelStr.length()-1);
            //System.out.println(userLabelStr);
            userLabelMap = new HashMap<>();
            String[] splitPair = userLabelStr.split(",");
            for (String s : splitPair){
                String[] pair = s.split(":");
                String word = pair[0].substring(1, pair[0].length()-1);
                String weight = pair[1].substring(1, pair[1].length()-1);
                userLabelMap.put(word, weight);
            }
            // System.out.println(userLabelMap);
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
        }
    }

    /**
     * 展示用户兴趣标签
     * @param model
     * @return
     */
    @RequestMapping(value = "/showLabel", produces = "text/html;charset=UTF-8")
    public String showLabel(Model model) {
        // 用户对推荐结果的map进行排序
        List<Map.Entry<String, String>> similartylist = new ArrayList<>(userLabelMap.entrySet());
        Collections.sort(similartylist,(o1,o2) ->
//                o2.getValue().compareTo(o1.getValue())
                Integer.parseInt(o2.getValue())-Integer.parseInt(o1.getValue())
        );
        model.addAttribute("userLabelMap", similartylist);
        return "/labelEditPage";
    }

    /**
     * 删除用户兴趣标签
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteLabel", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteLabel(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String label = request.getParameter("label");
        String weight = request.getParameter("weight");
        if (userLabelMap.containsKey(label) && userName != null){
            //System.out.println(userName + label + weight);
            userLabelMap.remove(label);
            String userLabelStr = JsonUtil.mapToJsonStr(userLabelMap);
            User user = new User();
            user.setUserName(userName);
            user.setUserModel(userLabelStr);
            us.updateUserCollect(user);
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
        }else {
            System.out.println("wrong");
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        }
    }

    /**
     * 更新用户兴趣标签
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateLabel", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateLabel(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String label = request.getParameter("label");
        String weight = request.getParameter("weight");
        if (userName != null){
            //System.out.println(userName + label + weight);
            // 用户在界面修改兴趣标签后，不论是修改还是添加，都以更新的方式添加
            // 如果是修改，那么userLabelMap会覆盖原来label
            userLabelMap.put(label, weight);
            String userLabelStr = JsonUtil.mapToJsonStr(userLabelMap);
            User user = new User();
            user.setUserName(userName);
            user.setUserModel(userLabelStr);
            us.updateUserCollect(user);
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
        }else {
            System.out.println("wrong");
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        }
    }

    /**
     * 插入用户修改的推荐类型和推荐时间
     * @param request
     * @return
     */
    @RequestMapping(value = "/recommendType", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String recommendType(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String taskType = request.getParameter("taskType");
        String timeRange = request.getParameter("timeRange");
        String currentTime = DateTimeUtil.getCurrentDate();
        if (userName != null){
            Task task = new Task();
            task.setUserName(userName);
            task.setTaskType(taskType);
            task.setTimeRange(timeRange);
            task.setTaskTime(currentTime);
            us.insertSparkTask(task);
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "true" + "\"" + "}";
        }else {
            System.out.println("wrong");
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        }
    }

    /**
     * 查询用户的推荐设置
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectTaskTypeLatest", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String selectTaskTypeLatest(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String taskType = request.getParameter("taskType");
        Map<String, String> responseResMap = new HashMap<>();
        if (userName != null){
            Task task = new Task();
            task.setUserName(userName);
            task.setTaskType(taskType);
            Task returnTask = us.selectTaskTypeLatest(task);
            String returnTaskTimeRange = returnTask.getTimeRange();
            if (taskType.equals("offline")){
                String beginTime = returnTaskTimeRange.split(" ")[0];
                String endTime = returnTaskTimeRange.split(" ")[1];
                responseResMap.put("beginTime", beginTime);
                responseResMap.put("endTime", endTime);
                responseResMap.put("result", "true");
            }else {
                responseResMap.put("timeRange", returnTaskTimeRange);
                responseResMap.put("result", "true");
            }
            String responseStr = JsonUtil.mapToJsonStr(responseResMap);
            return responseStr;
        }else {
            System.out.println("wrong");
            return "{" + "\"" + "result" + "\"" + ":" + "\"" + "false" + "\"" + "}";
        }
    }
}

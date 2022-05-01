package boot.wx.controller;

import boot.wx.entity.*;
import boot.wx.properties.UserInfoConfig;
import boot.wx.service.IQuestionAdminService;
import boot.wx.service.UserService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "管理员题库管理")
public class QuestionAdminController {

    @Autowired
    private IQuestionAdminService service;

    @Autowired
    private UserInfoConfig userInfoConfig;

    @Autowired
    private UserService userService;

    @ApiOperation("测试openfeign远程调用")
    @GetMapping("/test/remote/{str}")
    public String testRemote(@PathVariable("str") String str){
        return userService.test(str);
    }

    @ApiOperation("测试Nacos统一配置中心")
    @GetMapping("/refresh/get")
    public String refreshConfig(){
        return userInfoConfig.toString();
    }

    @ApiOperation("管理员登录")
    @PostMapping("/admin/login")
    public CommentResult<String> login(@RequestParam("username") String username, @RequestParam("password") String password,
                                       HttpSession session){
        log.info("login...");
        return service.login(username, password, session);
    }

    /**
     *  退出登录
     */
    @PostMapping("/admin/logout")
    public CommentResult<String> logout(@RequestParam("username") String username, HttpSession session){
        return service.logout(username, session);
    }

    /**
     *  添加某个课程下的题目 测试用
     *
     */
    @PostMapping("/admin/question/add")
    public CommentResult<Integer> add(@RequestBody QuestionEntity questionEntity){
        return service.add(questionEntity);
    }

    /**
     *  获取所有的题目
     *  不分类型. 所有的题目
     */
    @GetMapping("/admin/question/findAllQuestions")
    public CommentResult<List<QuestionEntity>> findAllQuestions(){
        return service.findAllQuestions();
    }

    /**
     *  根据id删除某个题目
     *
     */
    @PostMapping("/admin/question/deleteById/{id}")
    public CommentResult<Integer> deleteById(@PathVariable("id") Integer id){
        return service.deleteById(id);
    }

    /**
     *  根据id更新某个题目
     *  id为必要参数， 其他参数需要又一个不为空
     */
    @PostMapping("/admin/question/updateById")
    public CommentResult<Integer> updateById(@RequestBody QuestionEntity entity){
        return service.updateById(entity);
    }

    /**
     * 查询所有的课程类型
     * @return
     */
    @GetMapping("/admin/question/findAllQuestionTypes")
    public CommentResult<List<QuestionTypeEntity>> findAllQuestionTypes(){
        return service.findAllQuestionTypes();
    }

    /**
     * 添加一个课程类型
     * @return
     */
    @PostMapping("/admin/question/addQuestionType")
    public CommentResult<Integer> addQuestionType(@RequestParam("name") String name){
        return service.addQuestionType(name);
    }

    /**
     * 根据id删除一个课程类型
     * @param id
     * @return
     */
    @PostMapping("/admin/question/deleteQuestionType/{id}")
    public CommentResult<Integer> deleteQuestionType(@PathVariable("id") Integer id){
        return service.deleteQuestionType(id);
    }

    /**
     * 根据id更新一个课程类型的名称
     * @return
     */
    @PostMapping("/admin/question/updateQuestionType")
    public CommentResult<Integer> updateQuestionType(@RequestParam("id") Integer id,
                                                            @RequestParam("name") String name){
        return service.updateQuestionType(id, name);
    }

    /**
     * 添加一个课程类型下的文件夹
     * @return
     */
    @PostMapping("/admin/question/addQuestionFile")
    public CommentResult<Integer> addQuestionFile(@RequestParam("name") String name, @RequestParam("questionType") String questionType){
        return service.addQuestionFile(name, questionType);
    }

    /**
     * 根据id更新一个课程类型下的文件夹的名称
     * @return
     */
    @PostMapping("/admin/question/updateQuestionFile")
    public CommentResult<Integer> updateQuestionFile(@RequestParam("id") Integer id,
                                                     @RequestParam("name") String name){
        return service.updateQuestionFile(id, name);
    }

    /**
     * 根据id删除一个课程类型下的文件夹
     * @return
     */
    @PostMapping("/admin/question/deleteQuestionFile/{id}")
    public CommentResult<Integer> deleteQuestionFile(@PathVariable("id") Integer id){
        return service.deleteQuestionFile(id);
    }

    /**
     * 添加某个课程类型下的考研课程
     * @return
     */
    @PostMapping("/admin/question/course")
    public CommentResult<Integer> addCourse(@RequestBody Course course){
        return service.addCourse(course);
    }

    /**
     * 添加某个课程类型下的备考指南
     * @return
     */
    @PostMapping("/admin/question/guide")
    public CommentResult<Integer> addGuide(@RequestBody Guide guide){
        return service.addGuide(guide);
    }
}

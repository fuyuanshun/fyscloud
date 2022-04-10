package boot.wx.controller;

import boot.wx.entity.*;
import boot.wx.service.IQuestionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class QuestionController {

    @Autowired
    private IQuestionService service;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/test/remote/{str}")
    public String test(@PathVariable("str") String str){
        return "this is user-server : " + port + " : " + str;
    }

    /**
     *  获取某个课程下的所有的题目
     * {questionType}表示课程的类型
     *
     */
    @GetMapping("/question/findQuestionByType/{questionType}/{user_id}")
    public CommentResult<List<QuestionEntity>> findQuestionByType(@PathVariable("questionType") String questionType,
                                                                  @PathVariable(value = "user_id") String userId){
        return service.findQuestionByType(questionType, userId);
    }

    /**
     *  获取某个课程下所有收藏的题目
     */
    @GetMapping("/question/findByTypeAndCollect/{questionType}/{user_id}")
    public CommentResult<List<QuestionEntity>> findByTypeAndCollect(@PathVariable("questionType") String questionType,
                                                       @PathVariable("user_id") String userId){
        return service.findByTypeAndCollect(questionType, userId);
    }

    /**
     *  获取某个题目的正确答案和解析
     *
     */
    @GetMapping("/question/findAnswerById/{question_id}")
    public CommentResult<QuestionEntity> findAnswerById(@PathVariable("question_id") Integer questionId){
        return service.findAnswerById(questionId);
    }

    /**
     *  收藏某个题目
     *
     * @param userId 用户id
     * @param questionId 题目id
     */
    @PostMapping("/question/collect/{question_id}/{user_id}")
    public CommentResult<Integer> collect(@PathVariable("question_id") Integer questionId,
                                          @PathVariable("user_id") String userId){
        return service.collect(questionId, userId);
    }

    /**
     * 提交答题卡
     *
     * @return
     */
    @PostMapping("/question/commit/{user_id}")
    public CommentResult<JSONObject> commitQuestionCard(@PathVariable("user_id") String userId,
                                                        @RequestBody List<QuestionEntity> list){
        return service.commitQuestionCard(userId, list);
    }

    /**
     * 查询错题记录
     *
     * @return
     */
    @GetMapping("/question/wrong/{user_id}")
    public CommentResult<List<QuestionEntity>> wrongList(@PathVariable("user_id") String userId){
        return service.wrongList(userId);
    }

    /**
     * 删除某个错题记录, 支持删除多个，多个id之间使用半角','分割
     *
     * @return
     */
    @PostMapping("/question/removeWrong/{question_ids}/{user_id}")
    public CommentResult<Integer> removeWrongQuestion(@PathVariable("question_ids") String questionIds,
                                                      @PathVariable("user_id") String userId){
        return service.removeWrongQuestion(questionIds, userId);
    }

    /**
     * 查询某个课程下的题目分类
     * @param questionType
     * @param userId
     * @return
     */
    @GetMapping("/question/files/{questionType}/{user_id}")
    public CommentResult<List<QuestionFiles>> files(@PathVariable("questionType") String questionType, @PathVariable("user_id") String userId){
        return service.files(questionType, userId);
    }

    /**
     * 搜题
     * 查询所匹配的所有题目的id、答案、答案解析
     *
     */
    @GetMapping("/question/search/{user_id}/{questionContent}")
    public CommentResult<List<QuestionEntity>> searchQuestion(@PathVariable("user_id") Integer userId, @PathVariable("questionContent") String questionContent){
        return service.searchQuestion(userId, questionContent);
    }

    /**
     * 查询某个课程下的考研课程
     */
    @GetMapping("/question/course/{questionType}")
    public CommentResult<Course> course(@PathVariable("questionType") String questionType){
        return service.course(questionType);
    }

    /**
     * 查询所有课程下的考研课程
     */
    @GetMapping("/question/course")
    public CommentResult<List<Course>> courseAll(){
        return service.courseAll();
    }

    /**
     * 查询某个课程下的考研指南
     */
    @GetMapping("/question/guide/{questionType}")
    public CommentResult<Guide> guide(@PathVariable("questionType") String questionType){
        return service.guide(questionType);
    }

    /**
     * 查询所有课程下的考研指南
     */
    @GetMapping("/question/guide")
    public CommentResult<List<Guide>> guideAll(){
        return service.guideAll();
    }

    /**
     * 签到打卡 ??
     * @param userId
     * @return
     */
    @PostMapping("/user/signin/{user_id}")
    public CommentResult<String> signIn(@PathVariable("user_id") Integer userId){
        return service.signIn(userId);
    }
}

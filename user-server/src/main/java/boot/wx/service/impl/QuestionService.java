package boot.wx.service.impl;

import boot.wx.constants.QuestionConstants;
import boot.wx.entity.*;
import boot.wx.persistence.QuestionMapper;
import boot.wx.service.IQuestionService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionMapper mapper;

    @Override
    public void test() {
        System.out.println("test invoked...");
    }

    @Override
    public CommentResult<List<QuestionEntity>> findQuestionByType(String questionType, String userId) {
        log.info("查询接口：  /question/findQuestionByType/{questionType} 被 " + userId + "调用");
        List<QuestionEntity> result = null;
        try{
            result =  mapper.findQuestionByType(questionType, userId);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        AtomicInteger i = new AtomicInteger(1);
        result.forEach(e->{
            e.setNum(i.getAndIncrement());
            if(e.getSelects() != null && !e.getSelects().toString().trim().equals("")){
                String [] strings = e.getSelects().toString().split(";;;");
                e.setSelects(strings);
                addColumn(e, strings);
            }
        });
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<List<QuestionEntity>> findByTypeAndCollect(String questionType, String userId) {
        log.info("查询收藏接口：  /question/collect/{questionType}/{user_id} 被 " + userId + "调用");
        List<QuestionEntity> result = null;
        try{
            result =  mapper.findByTypeAndCollect(questionType, userId);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        AtomicInteger i = new AtomicInteger(1);
        result.forEach(e->{
            e.setNum(i.getAndIncrement());
            if(e.getSelects() != null && !e.getSelects().toString().trim().equals("")){
                String [] strings = e.getSelects().toString().split(";;;");
                e.setSelects(strings);
                addColumn(e, strings);
            }
        });
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<QuestionEntity> findAnswerById(Integer questionId) {
        log.info("查询某个题目的答案和解析接口：  /question/findAnswerById/{question_id} 被调用");
        QuestionEntity result = null;
        try{
            List<QuestionEntity> answerById = mapper.findAnswerById(questionId.toString(), null);
            if(answerById.size() != 0){
                result = answerById.get(0);
                result.setSelects(result.getSelects().toString().split(";;;"));
            }
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<Integer> collect(Integer questionId, String userId) {
        log.info("收藏接口：  /question/collect/{user_id}/{question_id} 被 " + userId + "调用");
        try{
            int count = mapper.findCollectStatus(userId, questionId); //查询是否已经收藏过了
            if(count > 0){ //有数据了，删除收藏
                mapper.collect(userId, questionId, "delete");
            } else { //没有数据，添加收藏
                mapper.collect(userId, questionId, "collect");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, 1);
    }

    @Override
    public CommentResult<JSONObject> commitQuestionCard(String userId, List<QuestionEntity> list) {
        log.info("提交答题卡接口：  /question/commit/{user_id} 被 " + userId + "调用");
        Map<Integer, String> map = new HashMap<>();
        List<Integer> idList = new ArrayList<>();
        list.forEach(e->{
            map.put(e.getId(), e.getCommitSelect()); //题目id和提交的选项的map
            idList.add(e.getId());
        });
        List<QuestionEntity> answerById = null;
        try{
            answerById = mapper.findAnswerById(StringUtils.join(idList, ","), userId);
            List<QuestionEntity> wrongQuestion = new ArrayList<>();
            List<QuestionEntity> finalWrongQuestion = wrongQuestion;
            answerById.forEach(e->{
                if(map.containsKey(e.getId())){
                    String s = map.get(e.getId()); //提交的选项
                    if((0 == e.getSelectType() || 1 == e.getSelectType())){ //如果是单项或者多项选择的话，提交的选项和答案一致，说明答对了，如果不是单项或者多项选择，说明是简答题，只要不为空，就算答对
                        e.setCommitSelect(s == null || "".equals(s) ? "未填写" : s); //

                        if(!"".equals(s) && e.getCorrectSelect().equals(s)){
                            e.setStatus(1);
                        } else if (s == null || "".equals(s)){ //为空的不添加到错题记录表
                            e.setStatus(0);
                        } else {  //不一致，答错了，添加到错题列表，统一添加到错题记录表里
                            e.setStatus(0);
                            finalWrongQuestion.add(e);
                        }
                    } else if(2 == e.getSelectType()){
                        e.setCommitSelect(s == null || "".equals(s) ? "未填写" : s); //

                        /*if(s != null && !"".equals(s)){
                            e.setStatus(1);
                        } else { //为空的不添加到错题记录表
                            e.setStatus(0);
                            finalWrongQuestion.add(e);
                        }*/
                    } else {
                        e.setStatus(0);
                        finalWrongQuestion.add(e);
                    }
                }
            });
            List<Integer> wrongQuestionIdList = mapper.findWrongQuestionList(userId);
            finalWrongQuestion.forEach(e->{
                if(wrongQuestionIdList.contains(e.getId())){
                    e.setDisable(1); //1表示不用添加的错题表
                    log.info(e + " : 又答错了 .... 已存在错题表中，本次不记录\n");
                } else {
                    e.setDisable(0); //0表示添加到错题表
                    log.info(e + " : 答错了 .... 记录到错题表\n");
                }
            });
            wrongQuestion = finalWrongQuestion.stream().filter(e->e.getDisable() != 1).collect(Collectors.toList());
            if(wrongQuestion.size() != 0){
                int i = mapper.addWrongQuestion(userId, wrongQuestion);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        float correctSize = (float)answerById.stream().filter(e->e.getStatus() != null && e.getStatus() == 1).collect(Collectors.toList()).size();
        float a = (correctSize / answerById.stream().filter(e-> e.getStatus() != null).collect(Collectors.toList()).size()) * 100;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", answerById);
        jsonObject.put("percentage", String.format("%.2f", a) + "%");
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, jsonObject);
    }

    @Override
    public CommentResult<List<QuestionEntity>> wrongList(String userId) {
        log.info("查询某个用户的错题记录接口：  /question/wrong/{user_id} 被调用");
        List<QuestionEntity> result = null;
        try{
            result = mapper.wrongList(userId);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        AtomicInteger i = new AtomicInteger(1);
        result.forEach(e->{
            e.setNum(i.getAndIncrement());
            if(e.getSelects() != null && !e.getSelects().toString().trim().equals("")){
                String [] strings = e.getSelects().toString().split(";;;");
                e.setSelects(strings);
                addColumn(e, strings);
            }
        });
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<Integer> removeWrongQuestion(String questionIds, String userId) {
        log.info("删除某个错题记录接口：  /question/removeWrong/{question_ids}/{user_id} 被调用");
        String match = "[,\\d]+"; //防止sql注入
        int count = 0;
        if(questionIds.matches(match)){
            try{
                count = mapper.removeWrongQuestion(questionIds, userId);
            } catch (Exception e){
                e.printStackTrace();
                return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
            }
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, count);
    }

    @Override
    public CommentResult<List<QuestionFiles>> files(String questionType, String userId) {
        List<QuestionFiles> result = null;
        try{
            result = mapper.files(questionType, userId);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<List<QuestionEntity>> searchQuestion(Integer userId, String questionContent) {
        List<QuestionEntity> result = null;
        try{
            result = mapper.searchQuestion(userId, questionContent);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<Course> course(String questionType) {
        List<Course> list = null;
        try{
            list = mapper.course(questionType);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        Course result = null;
        if(list.size() != 0)
            result = list.get(0);
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<List<Course>> courseAll() {
        List<Course> result = null;
        try{
            result = mapper.course(null);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<Guide> guide(String questionType) {
        List<Guide> list = null;
        try{
            list = mapper.guide(questionType);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        Guide result = null;
        if(list.size() != 0)
            result = list.get(0);
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<List<Guide>> guideAll() {
        List<Guide> result = null;
        try{
            result = mapper.guide(null);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<String> signIn(Integer userId) {
        LocalDateTime now = LocalDateTime.now();

        return null;
    }

    private void addColumn(QuestionEntity e, String[] strings){
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i<strings.length; i++){
            JSONObject jsonObject = new JSONObject();
            switch (i){
                case 0:
                    jsonObject.put("name", "one");
                    jsonObject.put("value", "A");
                    break;
                case 1:
                    jsonObject.put("name", "two");
                    jsonObject.put("value", "B");
                    break;
                case 2:
                    jsonObject.put("name", "three");
                    jsonObject.put("value", "C");
                    break;
                case 3:
                    jsonObject.put("name", "four");
                    jsonObject.put("value", "D");
                    break;
            }
            jsonObject.put("text", strings[i]);
            jsonArray.add(jsonObject);
        }
        e.setAswItem(jsonArray);
    }
}

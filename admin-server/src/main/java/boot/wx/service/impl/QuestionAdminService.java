package boot.wx.service.impl;

import boot.wx.constants.QuestionConstants;
import boot.wx.entity.*;
import boot.wx.flowlimit.QuestionAdminFlowLimit;
import boot.wx.persistence.QuestionAdminMapper;
import boot.wx.service.IQuestionAdminService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.core.instrument.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class QuestionAdminService implements IQuestionAdminService {

    @Autowired
    private QuestionAdminMapper mapper;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "chushimei";

    @Override
    @SentinelResource("t3")
    public String test(String str) {
        return str;
    }

    @Override
    @SentinelResource(value = "t", blockHandler = "flowLimit", blockHandlerClass = QuestionAdminFlowLimit.class)
    public CommentResult<String> login(String username, String password, HttpSession session) {
        if(ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)){
            session.setAttribute("userName", ADMIN_USERNAME);
            return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.LOGIN_SUCCESS_MESSAGE, null);
        }
        return new CommentResult<>(1000, QuestionConstants.LOGIN_ERROR_MESSAGE, null);
    }

    @Override
    public CommentResult<String> logout(String username, HttpSession session) {
        if(username != null && !username.equals("")){
            session.invalidate();
            return new CommentResult<>(200, QuestionConstants.SUCCESS_MESSAGE, null);
        }
        return new CommentResult<>(1000, QuestionConstants.ERROR_MESSAGE, null);
    }

    @Override
    public CommentResult<Integer> add(QuestionEntity entity) {
        int i = 0;
        try{
            i = mapper.add(entity);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<List<QuestionEntity>> findAllQuestions() {
        List<QuestionEntity> result = null;
        try{
            result =  mapper.findAllQuestions();
        } catch (Exception e) {
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
    public CommentResult<Integer> deleteById(Integer id) {
        int i = 0;
        try{
            i = mapper.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> updateById(QuestionEntity entity) {
        if(entity.getId() == null || entity.getId() == 0){
            return new CommentResult<>(QuestionConstants.PARAM_ERROR_CODE, QuestionConstants.PARAM_ERROR_MESSAGE, 0);
        }
        int i = 0;
        try{
            i = mapper.updateById(entity);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<List<QuestionTypeEntity>> findAllQuestionTypes() {
        List<QuestionTypeEntity> result = null;
        try{
            result = mapper.findAllQuestionTypes();
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, null);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, result);
    }

    @Override
    public CommentResult<Integer> addQuestionType(String name) {
        int i = 0;
        try{
            i = mapper.addQuestionType(name);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> deleteQuestionType(Integer id) {
        int i = 0;
        try{
            i = mapper.deleteQuestionType(id);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> updateQuestionType(Integer id, String name) {
        int i = 0;
        try{
            i = mapper.updateQuestionType(id, name);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> addQuestionFile(String name, String questionType) {
        int i = 0;
        try{
            i = mapper.addQuestionFile(name, questionType);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> updateQuestionFile(Integer id, String name) {
        int i = 0;
        try{
            i = mapper.updateQuestionFile(id, name);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> deleteQuestionFile(Integer id) {
        int i = 0;
        try{
            i = mapper.deleteQuestionFile(id);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> addCourse(Course course) {
        if(course.getQuestionTypeId() == null){
            return new CommentResult<>(QuestionConstants.PARAM_ERROR_CODE, QuestionConstants.PARAM_ERROR_MESSAGE, 0);
        }
        //每个课程下只能有一个数据，如果没有才能插入
        int count = mapper.getCountByCourse(course.getQuestionTypeId());
        if(count > 0){
            return new CommentResult<>(10003, "请检查数据是否已经存在！", 0);
        }
        int i = 0;
        try{
            i = mapper.addCourse(course);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
    }

    @Override
    public CommentResult<Integer> addGuide(Guide guide) {
        if(guide.getQuestionTypeId() == null){
            return new CommentResult<>(QuestionConstants.PARAM_ERROR_CODE, QuestionConstants.PARAM_ERROR_MESSAGE, 0);
        }
        //每个课程下只能有一个数据，如果没有才能插入
        int count = mapper.getCountByGuide(guide.getQuestionTypeId());
        if(count > 0){
            return new CommentResult<>(10003, "请检查数据是否已经存在！", 0);
        }
        int i = 0;
        try{
            i = mapper.addGuide(guide);
        } catch (Exception e){
            e.printStackTrace();
            return new CommentResult<>(QuestionConstants.ERROR_CODE, QuestionConstants.ERROR_MESSAGE, 0);
        }
        return new CommentResult<>(QuestionConstants.SUCCESS_CODE, QuestionConstants.SUCCESS_MESSAGE, i);
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

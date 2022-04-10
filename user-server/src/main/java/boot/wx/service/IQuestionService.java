package boot.wx.service;

import boot.wx.entity.*;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface IQuestionService {
    void test();

    CommentResult<List<QuestionEntity>> findQuestionByType(String questionType, String userId);

    CommentResult<List<QuestionEntity>> findByTypeAndCollect(String questionType, String userId);

    CommentResult<QuestionEntity> findAnswerById(Integer questionId);

    CommentResult<Integer> collect(Integer questionId, String userId);

    CommentResult<JSONObject> commitQuestionCard(String userId, List<QuestionEntity> list);

    CommentResult<List<QuestionEntity>> wrongList(String userId);

    CommentResult<Integer> removeWrongQuestion(String questionIds, String userId);

    CommentResult<List<QuestionFiles>> files(String questionType, String userId);

    CommentResult<String> signIn(Integer userId);

    CommentResult<List<QuestionEntity>> searchQuestion(Integer userId, String questionContent);

    CommentResult<Course> course(String questionType);

    CommentResult<List<Course>> courseAll();

    CommentResult<Guide> guide(String questionType);

    CommentResult<List<Guide>> guideAll();
}

package boot.wx.service;

import boot.wx.entity.*;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IQuestionAdminService {
    CommentResult<Integer> add(QuestionEntity entity);

    CommentResult<String> login(String username, String password, HttpSession session);

    CommentResult<String> logout(String username, HttpSession session);

    CommentResult<List<QuestionEntity>> findAllQuestions();

    CommentResult<Integer> deleteById(Integer id);

    CommentResult<Integer> updateById(QuestionEntity entity);

    CommentResult<List<QuestionTypeEntity>> findAllQuestionTypes();

    CommentResult<Integer> addQuestionType(String name);

    CommentResult<Integer> deleteQuestionType(Integer id);

    CommentResult<Integer> updateQuestionType(Integer id, String name);

    CommentResult<Integer> updateQuestionFile(Integer id, String name);

    CommentResult<Integer> addQuestionFile(String name, String questionType);

    CommentResult<Integer> deleteQuestionFile(Integer id);

    CommentResult<Integer> addCourse(Course course);

    CommentResult<Integer> addGuide(Guide guide);

    String test(String str);

    String testXA();
}

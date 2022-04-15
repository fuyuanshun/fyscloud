package boot.wx.persistence.master;

import boot.wx.entity.Course;
import boot.wx.entity.Guide;
import boot.wx.entity.QuestionEntity;
import boot.wx.entity.QuestionTypeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAdminMapper {
    int add(@Param("entity") QuestionEntity entity);

    List<QuestionEntity> findAllQuestions();

    int deleteById(Integer id);

    int updateById(@Param("entity") QuestionEntity entity);

    List<QuestionTypeEntity> findAllQuestionTypes();

    int addQuestionType(String name);

    int deleteQuestionType(Integer id);

    int updateQuestionType(@Param("id") Integer id, @Param("name") String name);

    int updateQuestionFile(@Param("id") Integer id, @Param("name") String name);

    int addQuestionFile(@Param("name") String name, @Param("questionType") String questionType);

    int deleteQuestionFile(Integer id);

    int addCourse(@Param("course") Course course);

    int getCountByCourse(Integer question_type);

    int getCountByGuide(Integer questionTypeId);

    int addGuide(@Param("guide") Guide guide);

    void addn();
}

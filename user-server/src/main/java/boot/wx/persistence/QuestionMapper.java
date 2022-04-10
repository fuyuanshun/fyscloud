package boot.wx.persistence;

import boot.wx.entity.Course;
import boot.wx.entity.Guide;
import boot.wx.entity.QuestionEntity;
import boot.wx.entity.QuestionFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    List<QuestionEntity> findQuestionByType(@Param("questionType") String questionType, @Param("userId") String userId);

    List<QuestionEntity> findByTypeAndCollect(@Param("questionType") String questionType, @Param("userId") String userId);

    List<QuestionEntity> findAnswerById(@Param("questionId") String questionId, @Param("userId") String userId);

    void collect(@Param("userId") String userId, @Param("questionId") Integer questionId, @Param("option") String option);

    int findCollectStatus(@Param("userId") String userId, @Param("questionId") Integer questionId);

    int addWrongQuestion(@Param("userId") String userId, @Param("list") List<QuestionEntity> wrongQuestion);

    List<Integer> findWrongQuestionList(@Param("userId") String userId);

    List<QuestionEntity> wrongList(@Param("userId") String userId);

    int removeWrongQuestion(@Param("questionIds") String questionIds, @Param("userId") String userId);

    List<QuestionFiles> files(@Param("questionType") String questionType, @Param("userId") String userId);

    List<QuestionEntity> searchQuestion(@Param("userId") Integer userId, @Param("questionContent") String questionContent);

    List<Course> course(String questionType);

    List<Guide> guide(String questionType);
}

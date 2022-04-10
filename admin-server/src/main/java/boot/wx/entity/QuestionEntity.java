package boot.wx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionEntity {
    private Integer num;
    private Integer id;  //id
    private String questionType; //属于哪个课程
    private String questionContent; //题干
    private Object selects; //选项， 简答题时为空
    private String correctSelect;  //正确选项，简答题时为空
    private Integer selectType; //题目类型
    private Integer sorted;  //排序顺序
    private String createTime; //创建时间
    private String questionComment;  //备注
    private boolean collectStatus; //收藏状态， 0  false表示未收藏  1  true表示收藏
    private String questionResolve; //题目解析
    private String wrongSelect;  //答错时的错误选项
    private String commitSelect;  //提交的选项
    private Integer status;
    private Integer disable;
    private String userId;  //用户id
    private String wrongTime;  //答错题目的时间
    private Object aswItem;
}

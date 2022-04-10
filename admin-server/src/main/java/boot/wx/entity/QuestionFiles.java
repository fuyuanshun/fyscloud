package boot.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionFiles {
    private Integer id;
    private String questionType;
    private String name;
    private Integer questionTypeId;
}

package boot.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer id;
    private Integer questionTypeId;
    private String c_title;
    private String c_slogan;
    private String c_content;
    private String c_list;
    private String c_img;
    private String questionType;
}

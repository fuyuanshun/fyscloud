package boot.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
    private Integer id;
    private Integer questionTypeId;
    private String questionType;
    private String c_title;
    private String content;
    private String c_slogan;
    private String c_img;
}

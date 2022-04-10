package boot.wx.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResult<T> {
    private Integer code;
    private String message;
    private T data;
}

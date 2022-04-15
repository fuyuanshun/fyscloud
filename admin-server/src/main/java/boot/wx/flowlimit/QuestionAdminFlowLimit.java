package boot.wx.flowlimit;

import boot.wx.entity.CommentResult;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class QuestionAdminFlowLimit {
    public static CommentResult<String> flowLimit(String username, String password, HttpSession session, BlockException blockException){
        return new CommentResult<>(10000, "limit", "");
    }
}

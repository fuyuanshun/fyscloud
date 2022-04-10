package boot.wx.service.fallback;

import boot.wx.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserService {
    @Override
    public String test(String str) {
        return "调用超时...";
    }
}

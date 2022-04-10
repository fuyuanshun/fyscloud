package boot.wx.service.fallback;

import boot.wx.service.UserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return new UserService() {
            @Override
            public String test(String str) {
                return "接口超时...";
            }
        };
    }
}

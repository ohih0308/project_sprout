package ohih.sprout.interceptors;

import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Component
public class PostAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        Long accessAuthorizedPost = (Long) session.getAttribute(StringConst.ACCESS_AUTHORIZED_POST);
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);


        Long postId = Long.parseLong((String) pathVariables.get(StringConst.POST_ID));

        if (postId == accessAuthorizedPost) {
            return true;
        } else {
            return false;
        }
    }
}

package ohih.sprout.interceptors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class AdInterceptor implements HandlerInterceptor {

    private final FileService fileService;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        List<AdFileDto> adFileList = fileService.getAdFileList();
//        modelAndView.addObject(StringConst.AD_FILE_LIST, adFileList);

    }
}

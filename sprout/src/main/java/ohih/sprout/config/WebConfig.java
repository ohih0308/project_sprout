package ohih.sprout.config;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.interceptors.AdInterceptor;
import ohih.sprout.interceptors.PostAccessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final FileService fileService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PostAccessInterceptor()).addPathPatterns(
                "/post/{postId}/edit",
                "/post/{postId}/delete");

        registry.addInterceptor(new AdInterceptor(fileService)).addPathPatterns("/**");
    }

}

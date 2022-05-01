package ntdgy.cs307project2.config;

import ntdgy.cs307project2.security.LoginChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
        registry.addViewController("/dashboard.html").setViewName("dashboard");
        registry.addViewController("/customer.html").setViewName("childPages/customer");
        registry.addViewController("/management.html").setViewName("childPages/management");
        registry.addViewController("/register.html").setViewName("signup");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginChecker()).addPathPatterns("/**").excludePathPatterns("/", "/register.html", "/index.html", "/css/**", "/js/**", "/api/**", "/img/**");
    }
}

package ntdgy.cs307project2.config;

import ntdgy.cs307project2.security.LoginChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

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
        registry.addViewController("/selection.html").setViewName("childPages/selection");
        registry.addViewController("/stockIn.html").setViewName("childPages/stockIn");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginChecker()).addPathPatterns("/**").excludePathPatterns("/", "/register.html", "/index.html", "/css/**", "/js/**", "/api/**", "/img/**");
    }

    /**
     * 默认线程池线程池
     *
     * @return Executor
     */
    @Bean
    public ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(16);
        //指定最大线程数
        executor.setMaxPoolSize(300);
        //队列中最大的数目
        executor.setQueueCapacity(50);
        //线程名称前缀
        executor.setThreadNamePrefix("defaultThreadPool_");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(60);
        //加载
        executor.initialize();
        return executor;
    }
}

package ntdgy.cs307project2.controller;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.service.InsertService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/upload")
@EnableAsync
public class UploadController {

    private final InsertService insertService;

    final HikariDataSource hikariDataSource;
    //private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public UploadController(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
        this.insertService = new InsertService(hikariDataSource);
        //this.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    }

    @RequestMapping("/")
    @ResponseBody
    //@Async("taskExecutor")
    public CompletableFuture<String> test(
            // @RequestPart MultipartFile file,
            // @RequestBody String type
    ) {
//        if(type.equalsIgnoreCase("center")) {
//
//        }
        CompletableFuture<String> page1 = insertService.addCenter("1");
        CompletableFuture<String> page2 = insertService.addCenter("2");
        CompletableFuture<String> page3 = insertService.addCenter("3");
        CompletableFuture<String> page4 = insertService.addCenter("4");
        CompletableFuture.allOf(page1,page2,page3,page4).join();
        return CompletableFuture.completedFuture("");
    }

    @Async("taskExecutor")
    public void addCenter() {
        log.error("addCenter");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

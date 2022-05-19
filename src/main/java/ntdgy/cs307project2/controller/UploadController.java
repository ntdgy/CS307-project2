package ntdgy.cs307project2.controller;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private InsertService insertService;

    final HikariDataSource hikariDataSource;
    //private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public UploadController(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
        //this.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    }

    @RequestMapping("/")
    @ResponseBody
    @Async("hello")
    public CompletableFuture<String> test(
            // @RequestPart MultipartFile file,
            // @RequestBody String type
    ) {
//        if(type.equalsIgnoreCase("center")) {
//
//        }
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        insertService.test();
        return CompletableFuture.completedFuture("");
    }

    @Async("hello")
    public void addCenter() {
        log.error("addCenter");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

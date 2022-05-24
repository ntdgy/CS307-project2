package ntdgy.cs307project2.controller;

import ntdgy.cs307project2.dto.Center;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @Async
    @RequestMapping("/help")
    public String test() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("?");
        return "This is a help";
    }

    @RequestMapping("/center/test")
    public String test2(
            @RequestBody Center center
            ){
        return center.getName() + " " + center.getId();
    }
}

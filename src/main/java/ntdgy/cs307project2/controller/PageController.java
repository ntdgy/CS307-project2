package ntdgy.cs307project2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @RequestMapping("/help")
    public String test(){
        return "This is a help";
    }
}

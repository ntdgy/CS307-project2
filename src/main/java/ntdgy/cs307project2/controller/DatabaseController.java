package ntdgy.cs307project2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("database")
public class DatabaseController {

    @PostMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/test")
    public String wa(){
        return "WA method";
    }
}

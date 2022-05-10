package ntdgy.cs307project2.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/api/upload")
public class UploadController {

    public UploadController() {

    }

    @RequestMapping("/")
    public String index() {
        return "/";
    }

    @RequestMapping("/test")
    public String test(@RequestPart MultipartFile file) {
        log.info("file name: " + file.getOriginalFilename());
        log.info("file size: " + file.getSize());
        log.info("file content type: " + file.getContentType());
        return "/childPages/upload";
    }
}

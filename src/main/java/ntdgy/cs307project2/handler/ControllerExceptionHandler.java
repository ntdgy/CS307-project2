package ntdgy.cs307project2.handler;


import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.exception.InvalidDataException;
import ntdgy.cs307project2.exception.InvalidOperationException;
import ntdgy.cs307project2.exception.WrongDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@Slf4j
@RestControllerAdvice(basePackages = "ntdgy.cs307project2.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidDataException(InvalidDataException e) {
        Map<String, Object> res = new HashMap<>();
        res.put("result", "fail " + e.getMessage());
        return res;
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidOperationException(InvalidOperationException e) {
        Map<String, Object> res = new HashMap<>();
        res.put("result", "fail " + e.getMessage());
        return res;
    }

    @ExceptionHandler(WrongDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleWrongDataException(WrongDataException e) {
        log.error(e.getCause().toString());
        log.error(e.getCause().getMessage());
        if (e.getCause() instanceof DuplicateKeyException) {
            Map<String, Object> res = new HashMap<>();
            String message = e.getCause().getMessage();
            message = message.substring(message.indexOf("详细") + 2,message.indexOf("; nested exception"));
            res.put("result", "数据重复 "  + message);
            return res;
        }
        Map<String, Object> res = new HashMap<>();
        res.put("result", "fail " + e.getMessage());
        return res;
    }


}

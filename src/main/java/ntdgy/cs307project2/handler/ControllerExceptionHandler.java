package ntdgy.cs307project2.handler;


import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.exception.InvalidDataException;
import ntdgy.cs307project2.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}

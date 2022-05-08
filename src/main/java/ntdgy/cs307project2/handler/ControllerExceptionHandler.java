package ntdgy.cs307project2.handler;


import ntdgy.cs307project2.exception.InvalidDataException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice(basePackages = "ntdgy.cs307project2.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public Map<String, Object> handleInvalidDataException(InvalidDataException e) {
        Map<String, Object> res = new HashMap<>();
        res.put("result", "fail " + e.getMessage());
        return res;
    }



}

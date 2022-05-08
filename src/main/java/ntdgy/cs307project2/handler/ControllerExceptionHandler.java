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
    public List<Map<String, Object>> handleInvalidDataException(InvalidDataException e) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        res.put("result", "fail " + e.getMessage());
        list.add(res);
        return list;
    }



}

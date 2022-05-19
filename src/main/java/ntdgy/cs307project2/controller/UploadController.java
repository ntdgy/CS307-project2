package ntdgy.cs307project2.controller;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private InsertService insertService;

    private final String UPLOADED_FOLDER = "src/main/resources/static/upload/";

    final HikariDataSource hikariDataSource;


    public UploadController(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @RequestMapping("/test")
    public CompletableFuture<List<String>> test(
            @RequestPart MultipartFile file
            // @RequestBody String type
    ) {
        if (file.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        try {
            byte[] bytes = file.getBytes();
            Path dir = Paths.get(UPLOADED_FOLDER);
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Files.write(path, bytes);
            return addCenterController(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<List<String>> addCenterController(String path) {
        int countSuccess = 0, countFail = 0;
        List<CompletableFuture<Boolean>> result = new LinkedList<>();
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis,
                     StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                result.add(insertService.addCenter(Integer.parseInt(nextLine[0]), nextLine[1]));
            }
            for (var test : result) {
                CompletableFuture.allOf(test).join();
            }
            for (var test : result) {
                if (test.getNow(false)) {
                    countSuccess++;
                } else {
                    countFail++;
                }
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        List<String> list = new LinkedList<>();
        list.add("Success: " + countSuccess);
        list.add("Fail: " + countFail);
        list.add("Total: " + (countSuccess + countFail));
        list.add("请访问控制台查看日志");
        return CompletableFuture.completedFuture(list);
    }

    public CompletableFuture<List<String>> addEnterpriseController(String path) {
        int countSuccess = 0, countFail = 0;
        List<CompletableFuture<Boolean>> result = new LinkedList<>();
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis,
                     StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                result.add(insertService.addEnterprise(nextLine));
            }
            for (var test : result) {
                CompletableFuture.allOf(test).join();
            }
            for (var test : result) {
                if (test.getNow(false)) {
                    countSuccess++;
                } else {
                    countFail++;
                }
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        List<String> list = new LinkedList<>();
        list.add("Success: " + countSuccess);
        list.add("Fail: " + countFail);
        list.add("Total: " + (countSuccess + countFail));
        list.add("请访问控制台查看日志");
        return CompletableFuture.completedFuture(list);
    }

    public CompletableFuture<List<String>> addModelController(String path) {
        int countSuccess = 0, countFail = 0;
        List<CompletableFuture<Boolean>> result = new LinkedList<>();
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis,
                     StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                result.add(insertService.addModel(nextLine));
            }
            for (var test : result) {
                CompletableFuture.allOf(test).join();
            }
            for (var test : result) {
                if (test.getNow(false)) {
                    countSuccess++;
                } else {
                    countFail++;
                }
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        List<String> list = new LinkedList<>();
        list.add("Success: " + countSuccess);
        list.add("Fail: " + countFail);
        list.add("Total: " + (countSuccess + countFail));
        list.add("请访问控制台查看日志");
        return CompletableFuture.completedFuture(list);
    }


}

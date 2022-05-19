package ntdgy.cs307project2.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class InsertService {
    final HikariDataSource hikariDataSource;

    public InsertService(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Async
    public CompletableFuture<String> addCenter(String s) {
        log.error("addCenter" + s);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Success");
    }

    @Async("hello")
    public void test() {
        log.error(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

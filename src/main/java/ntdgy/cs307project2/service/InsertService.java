package ntdgy.cs307project2.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class InsertService {
    final HikariDataSource hikariDataSource;

    public InsertService(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Async("dgy")
    public CompletableFuture<Boolean> addCenter(String s) {
        log.error("addCenter");
        try {
            var conn = hikariDataSource.getConnection();
            var stmt = conn.prepareStatement("insert into center(name) values(?)");
            stmt.setString(1, s);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            log.error("error", e);
            return CompletableFuture.completedFuture(false);
        }
    }


}

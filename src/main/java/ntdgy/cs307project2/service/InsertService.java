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
    public CompletableFuture<Boolean> addCenter(int a, String s) {
        try {
            var conn = hikariDataSource.getConnection();
            var stmt = conn.prepareStatement("insert into center(id,name) values(?,?)");
            stmt.setInt(1, a);
            stmt.setString(2, s);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            log.error("error", e);
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async("dgy")
    public CompletableFuture<Boolean> addEnterprise(String[] data) {
        try {
            var conn = hikariDataSource.getConnection();
            var stmt =
                    conn.prepareStatement("insert into enterprise(id,name,country,city," +
                            "supply_center,industry) values(?,?,?,?,?,?)");
            stmt.setInt(1, Integer.parseInt(data[0]));
            stmt.setString(2, data[1]);
            stmt.setString(3, data[2]);
            stmt.setString(4, data[3]);
            stmt.setInt(5, Integer.parseInt(data[4]));
            stmt.setString(6, data[5]);
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

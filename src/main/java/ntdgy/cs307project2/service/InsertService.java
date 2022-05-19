package ntdgy.cs307project2.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.exception.WrongDataException;
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

    @Async("dgy")
    public CompletableFuture<Boolean> addModel(String[] data) {
        try {
            var conn = hikariDataSource.getConnection();
            var stmt = conn.prepareStatement("insert into model(id,number,model,name,unit_price) values(?,?,?,?,?)");
            stmt.setInt(1, Integer.parseInt(data[0]));
            stmt.setString(2, data[1]);
            stmt.setString(3, data[2]);
            stmt.setString(4, data[3]);
            stmt.setInt(5, Integer.parseInt(data[4]));
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
    public CompletableFuture<Boolean> addStaff(String[] data) {
        try {
            var conn = hikariDataSource.getConnection();
            var stmt = conn.prepareStatement(
                    "insert into staff(id, name, number, gender, age, mobile_number, supply_center, stafftype) " +
                            "VALUES (?,?,?,?,?,?,?,?);");
            stmt.setInt(1, Integer.parseInt(data[0]));
            stmt.setString(2, data[1]);
            stmt.setString(3, data[2]);
            stmt.setString(4, data[3]);
            stmt.setInt(5, Integer.parseInt(data[4]));
            stmt.setString(6, data[5]);
            stmt.setString(7, data[6]);
            switch (data[7]) {
                case "Director":
                    stmt.setInt(8, 0);
                    break;
                case "Supply Staff":
                    stmt.setInt(8, 1);
                    break;
                case "Contracts Manager":
                    stmt.setInt(8, 2);
                    break;
                case "Salesman":
                    stmt.setInt(8, 3);
                    break;
                default:
                    throw new WrongDataException("人员类型错误");
            }
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (SQLException | WrongDataException e) {
            log.error("error", e);
            return CompletableFuture.completedFuture(false);
        }
    }


}

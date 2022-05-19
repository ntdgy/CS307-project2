package ntdgy.cs307project2.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.exception.InvalidDataException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

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
    public CompletableFuture<Boolean> addStaff(String[] data) throws InvalidDataException {
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
                    throw new InvalidDataException("人员类型错误");
            }
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            log.error("error", e);
            return CompletableFuture.completedFuture(false);
        }
    }


    //data[]:id,supply_center,product_model,supply_staff,date,purchase_price,quantity
    @Async("dgy")
    public CompletableFuture<Boolean> stockIn(String[] data) throws InvalidDataException {
        try {
            var conn = hikariDataSource.getConnection();
            String check1 = "select * from staff where staff.number = ?";
            var stmt1 = conn.prepareStatement(check1);
            stmt1.setString(1, data[3]);
            var rs1 = stmt1.executeQuery();
            if (!rs1.next()) {
                log.error(data[3] + "不存在");
                throw new InvalidDataException("⼈员不存在");
            }
            if (!(rs1.getInt("stafftype") == 1)) {
                log.error(data[3] + "不是供应员工");
                throw new InvalidDataException("⼈员的类型不是supply_staff");
            }
            if (!(rs1.getString("supply_center").equals(data[1]))) {
                log.error(data[3] + "不在" + data[1] + "供应中心");
                throw new InvalidDataException("供应商不属于该供应中心");
            }
            String check2 = "select * from center where center.name = ?";
            var stmt2 = conn.prepareStatement(check2);
            stmt2.setString(1, data[1]);
            var rs2 = stmt2.executeQuery();
            if (!rs2.next()) {
                log.error(data[1] + "不存在");
                throw new InvalidDataException("供应中心不存在");
            }
            String check3 = "select * from model where model.model = ?";
            var stmt3 = conn.prepareStatement(check3);
            stmt3.setString(1, data[2]);
            var rs3 = stmt3.executeQuery();
            if (!rs3.next()) {
                log.error(data[2] + "不存在");
                throw new InvalidDataException("产品型号不存在");
            }
            String check4 = "select * from warehousing where center_name = ? and model_name = ?;";
            var stmt4 = conn.prepareStatement(check4);
            stmt4.setString(1, data[1]);
            stmt4.setString(2, data[2]);
            var rs4 = stmt4.executeQuery();
            if (rs4.next()) {
                String sql = "update warehousing set quantity = quantity + ? where center_name = ? and model_name = ?;";
                var stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(data[6]));
                stmt.setString(2, data[1]);
                stmt.setString(3, data[2]);
                stmt.executeUpdate();
            } else {
                String sql = "insert into warehousing(center_name,model_name,quantity) values(?,?,?)";
                var stmt = conn.prepareStatement(sql);
                stmt.setString(1, data[1]);
                stmt.setString(2, data[2]);
                stmt.setInt(3, Integer.parseInt(data[6]));
                stmt.executeUpdate();
            }
            String sql = "insert into stockin(id,supply_center,product_model," +
                    "supply_staff,date,purchase_price,quantity) values(?,?,?,?,?,?,?)";
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(data[0]));
            stmt.setString(2, data[1]);
            stmt.setString(3, data[2]);
            stmt.setString(4, data[3]);
            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[4])) {
                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
                Date date1 = jsFormat.parseDateTime(data[4]).toDate();
                java.sql.Date date = new java.sql.Date(date1.getTime());
                stmt.setDate(5, date);
            } else {
                java.sql.Date date = java.sql.Date.valueOf(data[4].replace('/', '-'));
                stmt.setDate(5, date);
            }
            stmt.setString(6, data[5]);
            stmt.setInt(7, Integer.parseInt(data[6]));
            stmt.executeUpdate();
            conn.commit();
            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            log.error("error", e);
            return CompletableFuture.completedFuture(false);
        }
    }

    //contract_num,enterprise,product_model,quantity,contract_manager,contract_date,
    // estimated_delivery_date, lodgement_date,salesman_num,contract_type
    public CompletableFuture<Boolean> placeOrder(String[] data) throws InvalidDataException {
        try {
            var conn = hikariDataSource.getConnection();
            String check1 = "select * from staff where staff.number = ?";
            var stmt1 = conn.prepareStatement(check1);
            stmt1.setString(1, data[8]);
            var rs1 = stmt1.executeQuery();
            if (!rs1.next()) {
                log.error(data[8] + " is not a valid staff number");
                throw new InvalidDataException("员工编号不存在");
            }
            if (rs1.getInt("stafftype") != 3) {
                throw new InvalidDataException("该员工不是salesman");
            }
            String check2 = "select *\n" +
                    "from warehousing w\n" +
                    "         join (select e.supply_center\n" +
                    "               from  enterprise e\n" +
                    "               where e.name = ?) as cesc\n" +
                    "              on w.center_name = cesc.supply_center " +
                    "                   and w.model_name = ? " +
                    "                   and w.quantity >= ?;";
            var stmt2 = conn.prepareStatement(check2);
            stmt2.setString(1, data[1]);
            stmt2.setString(2, data[2]);
            stmt2.setInt(3, Integer.parseInt(data[3]));
            var rs2 = stmt2.executeQuery();
            if (!rs2.next()) {
                throw new InvalidDataException("库存不足");
            }
            String check3 = "select * from contract where number = ?";
            var stmt3 = conn.prepareStatement(check3);
            stmt3.setString(1, data[0]);
            var rs3 = stmt3.executeQuery();
            String check4 = "select count(*) from sold where model_name = ?";
            var stmt4 = conn.prepareStatement(check4);
            stmt4.setString(1, data[2]);
            var rs4 = stmt4.executeQuery();
            Date estimated_delivery_date1, lodgement_date1, contractdate1;
            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
                    data[6])) {
                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
                estimated_delivery_date1 = jsFormat.parseDateTime(data[6]).toDate();
            } else {
                estimated_delivery_date1 = java.sql.Date.valueOf(data[6].replace('/', '-'));
            }
            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
                    data[7])) {
                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
                lodgement_date1 = jsFormat.parseDateTime(data[7]).toDate();
            } else {
                lodgement_date1 = java.sql.Date.valueOf(data[7].replace('/', '-'));
            }
            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
                    data[5])) {
                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
                contractdate1 = jsFormat.parseDateTime(data[5]).toDate();
            } else {
                contractdate1 = java.sql.Date.valueOf(data[5].replace('/', '-'));
            }
            java.sql.Date estimated_delivery_date = new java.sql.Date(estimated_delivery_date1.getTime());
            java.sql.Date lodgement_date = new java.sql.Date(lodgement_date1.getTime());
            java.sql.Date contractdate = new java.sql.Date(contractdate1.getTime());
            if (rs3.next()) {
                String sql = "insert into contract_content (contract_number, product_model_name, quantity, " +
                        "estimated_delivery_date, lodgement_date, salesman) " +
                        "values (?, ?, ?, ?, ?, ?)";
                var stmt = conn.prepareStatement(sql);
                stmt.setString(1, data[0]);
                stmt.setString(2, data[2]);
                stmt.setInt(3, Integer.parseInt(data[3]));
                stmt.setDate(4, estimated_delivery_date);
                stmt.setDate(5, lodgement_date);
                stmt.setString(6, data[8]);
                stmt.executeUpdate();
                String sql1 = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
                stmt = conn.prepareStatement(sql1);
                stmt.setInt(1, Integer.parseInt(data[3]));
                stmt.setString(2, rs2.getString("center_name"));
                stmt.setString(3, data[2]);
                stmt.executeUpdate();
                if (!rs4.next()) {
                    String sql2 = "insert into sold (model_name, quantity) values (?, ?)";
                    stmt = conn.prepareStatement(sql2);
                    stmt.setString(1, data[2]);
                    stmt.setInt(2, Integer.parseInt(data[3]));
                    stmt.executeUpdate();
                } else {
                    String sql2 = "update sold set quantity = quantity + ? where model_name = ?";
                    stmt = conn.prepareStatement(sql2);
                    stmt.setInt(1, Integer.parseInt(data[3]));
                    stmt.setString(2, data[2]);
                    stmt.executeUpdate();
                }
            } else {
                String sql = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
                        "values (?, ?, ?, ?, ?)";
                var stmt = conn.prepareStatement(sql);
                stmt.setString(1, data[0]);
                stmt.setString(2, data[1]);
                stmt.setDate(3, contractdate);
                stmt.setString(4, data[6]);
                stmt.setString(5, data[7]);
                stmt.executeUpdate();
                String sql1 = "insert into contract_content (contract_number, product_model_name, quantity, " +
                        "estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql1);
                stmt.setString(1, data[0]);
                stmt.setString(2, data[2]);
                stmt.setInt(3, Integer.parseInt(data[3]));
                stmt.setDate(4, estimated_delivery_date);
                stmt.setDate(5, lodgement_date);
                stmt.setString(6, data[8]);
                stmt.executeUpdate();
                String sql2 = "update warehousing set quantity = quantity - ? " +
                        "where center_name = ? and model_name = ?";
                stmt = conn.prepareStatement(sql2);
                stmt.setInt(1, Integer.parseInt(data[3]));
                stmt.setString(2, rs2.getString("center_name"));
                stmt.setString(3, data[2]);
                stmt.executeUpdate();
                if (!rs4.next()) {
                    String sql3 = "insert into sold (model_name, quantity) values (?, ?)";
                    stmt = conn.prepareStatement(sql3);
                    stmt.setString(1, data[2]);
                    stmt.setInt(2, Integer.parseInt(data[3]));
                    stmt.executeUpdate();
                } else {
                    String sql3 = "update sold set quantity = quantity + ? where model_name = ?";
                    stmt = conn.prepareStatement(sql3);
                    stmt.setInt(1, Integer.parseInt(data[3]));
                    stmt.setString(2, data[2]);
                    stmt.executeUpdate();
                }
            }
            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (SQLException e) {
            log.error("error", e);
            return CompletableFuture.completedFuture(false);
        }
    }


}

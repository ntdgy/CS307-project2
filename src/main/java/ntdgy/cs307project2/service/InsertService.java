package ntdgy.cs307project2.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.exception.InvalidDataException;
import ntdgy.cs307project2.exception.WrongDataException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Slf4j
@Service
public class InsertService {
    final HikariDataSource hikariDataSource;

    final JdbcTemplate jdbcTemplate;

    public InsertService(HikariDataSource hikariDataSource, JdbcTemplate jdbcTemplate) {
        this.hikariDataSource = hikariDataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Async("dgy")
    public CompletableFuture<Boolean> addCenter(int a, String s) throws WrongDataException {
        try {
            String sql = "insert into center(id,name) values(?,?)";
            jdbcTemplate.update(sql, a, s);
//            var conn = hikariDataSource.getConnection();
//            var stmt = conn.prepareStatement("insert into center(id,name) values(?,?)");
//            stmt.setInt(1, a);
//            stmt.setString(2, s);
//            stmt.executeUpdate();
//            stmt.close();
//            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(true);
        }
    }

    @Async("dgy")
    public CompletableFuture<Boolean> addEnterprise(String[] data) {
        try {
//            var conn = hikariDataSource.getConnection();
//            var stmt =
//                    conn.prepareStatement("insert into enterprise(id,name,country,city," +
//                            "supply_center,industry) values(?,?,?,?,?,?)");
//            stmt.setInt(1, Integer.parseInt(data[0]));
//            stmt.setString(2, data[1]);
//            stmt.setString(3, data[2]);
//            stmt.setString(4, data[3]);
//            stmt.setString(5, data[4]);
//            stmt.setString(6, data[5]);
            String sql = "insert into enterprise(id,name,country,city," +
                    "supply_center,industry) values(?,?,?,?,?,?)";
            Object[] params = new Object[]{
                    Integer.parseInt(data[0]),
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5]
            };
            jdbcTemplate.update(sql, params);
//            stmt.executeUpdate();
//            stmt.close();
//            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async("dgy")
    public CompletableFuture<Boolean> addModel(String[] data) {
        try {
            String sql = "insert into model(id,number,model,name,unit_price) values(?,?,?,?,?)";
            Object[] params = new Object[]{
                    Integer.parseInt(data[0]),
                    data[1],
                    data[2],
                    data[3],
                    Integer.parseInt(data[4])
            };
            jdbcTemplate.update(sql, params);
//            var conn = hikariDataSource.getConnection();
//            var stmt = conn.prepareStatement("insert into model(id,number,model,name,unit_price) values(?,?,?,?,?)");
//            stmt.setInt(1, Integer.parseInt(data[0]));
//            stmt.setString(2, data[1]);
//            stmt.setString(3, data[2]);
//            stmt.setString(4, data[3]);
//            stmt.setInt(5, Integer.parseInt(data[4]));
//            stmt.executeUpdate();
//            stmt.close();
//            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async("dgy")
    @Transactional
    //id,name,age,gender,number,supply_center,mobile_number,type
    public CompletableFuture<Boolean> addStaff(String[] data) throws InvalidDataException {
        try {
            String sql = "insert into staff(id, name, number, gender, age, mobile_number, supply_center, stafftype)" +
                    "VALUES (?,?,?,?,?,?,?,?);";
            int stafftype;
            switch (data[7]) {
                case "Director":
                    stafftype = 0;
                    break;
                case "Supply Staff":
                    stafftype = 1;
                    break;
                case "Contracts Manager":
                    stafftype = 2;
                    break;
                case "Salesman":
                    stafftype = 3;
                    break;
                default:
                    throw new InvalidDataException("人员类型错误");
            }
            Object[] params = new Object[]{
                    Integer.parseInt(data[0]),
                    data[1],
                    data[4],
                    data[3],
                    Integer.parseInt(data[2]),
                    data[6],
                    data[5],
                    stafftype
            };
            jdbcTemplate.update(sql, params);
//            var conn = hikariDataSource.getConnection();
//            var stmt = conn.prepareStatement(
//                    "insert into staff(id, name, number, gender, age, mobile_number, supply_center, stafftype) " +
//                            "VALUES (?,?,?,?,?,?,?,?);");
//            stmt.setInt(1,Integer.parseInt(data[0]));
//            stmt.setString(2,data[1]);
//            stmt.setString(3,data[4]);
//            stmt.setString(4,data[3]);
//            stmt.setInt(5,Integer.parseInt(data[2]));
//            stmt.setString(6,data[6]);
//            stmt.setString(7,data[5]);
//            switch (data[7]) {
//                case "Director":
//                    stmt.setInt(8, 0);
//                    break;
//                case "Supply Staff":
//                    stmt.setInt(8, 1);
//                    break;
//                case "Contracts Manager":
//                    stmt.setInt(8, 2);
//                    break;
//                case "Salesman":
//                    stmt.setInt(8, 3);
//                    break;
//                default:
//                    throw new InvalidDataException("人员类型错误");
//            }
//            stmt.executeUpdate();
//            stmt.close();
//            conn.close();
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CompletableFuture.completedFuture(false);
        }

    }


    //data[]:id,supply_center,product_model,supply_staff,date,purchase_price,quantity
    @Async("dgy")
    public CompletableFuture<Boolean> stockIn(String[] data) throws InvalidDataException {
        String sql;
        Object[] obj;
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbcTemplate.queryForList(check1, data[3]);
        if (check2.size() == 0) {
            log.error("没有该员工");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("⼈员不存在");
        }
        if (!check2.get(0).get("stafftype").equals(1)) {
            log.error("⼈员的类型不是supply_staff");
            return CompletableFuture.completedFuture(false);
            // throw new InvalidDataException("⼈员的类型不是supply_staff");
        }
        if (!check2.get(0).get("supply_center").equals(data[1])) {
            log.error("供应商不属于该供应中心");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("供应商不属于该供应中心");
        }
        String check3 = "select * from center where center.name = ?";
        List<Map<String, Object>> check4 = jdbcTemplate.queryForList(check3, data[1]);
        if (check4.size() == 0) {
            log.error("没有该供应中心");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("供应中心不存在");
        }
        String check5 = "select * from model where model.model = ?";
        List<Map<String, Object>> check6 = jdbcTemplate.queryForList(check5, data[2]);
        if (check6.size() == 0) {
            log.error("没有该型号");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("产品型号不存在");
        }
        String check9 = "select * from warehousing where center_name = ? and model_name = ?;";
        List<Map<String, Object>> check10 = jdbcTemplate.queryForList(check9, data[1], data[2]);
        if (check10.size() != 0) {
            sql = "update warehousing set quantity = quantity + ? where center_name = ? and model_name = ?;";
            obj = new Object[3];
            obj[0] = Integer.parseInt(data[6]);
            obj[1] = data[1];
            obj[2] = data[2];
            jdbcTemplate.update(sql, obj);
        } else {
            sql = "insert into warehousing(center_name,model_name,quantity) values(?,?,?)";
            obj = new Object[3];
            obj[0] = data[1];
            obj[1] = data[2];
            obj[2] = Integer.parseInt(data[6]);
            jdbcTemplate.update(sql, obj);
        }
        sql = "insert into stockin(id,supply_center,product_model,supply_staff,date,purchase_price,quantity) values(?,?,?,?,?,?,?)";
        obj = new Object[7];
        obj[0] = Integer.parseInt(data[0]);
        obj[1] = data[1];
        obj[2] = data[2];
        obj[3] = data[3];
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[4])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            Date date = jsFormat.parseDateTime(data[4]).toDate();
            obj[4] = date;
        } else {
            Date date = java.sql.Date.valueOf(data[4].replace('/', '-'));
            obj[4] = date;
        }
        obj[5] = Integer.parseInt(data[5]);
        obj[6] = Integer.parseInt(data[6]);
        jdbcTemplate.update(sql, obj);
//            var conn = hikariDataSource.getConnection();
//            String check1 = "select * from staff where staff.number = ?";
//            var stmt1 = conn.prepareStatement(check1);
//            stmt1.setString(1, data[3]);
//            var rs1 = stmt1.executeQuery();
//            if (!rs1.next()) {
//                log.error(data[3] + "不存在");
//                throw new InvalidDataException("⼈员不存在");
//            }
//            if (!(rs1.getInt("stafftype") == 1)) {
//                log.error(data[3] + "不是供应员工");
//                throw new InvalidDataException("⼈员的类型不是supply_staff");
//            }
//            if (!(rs1.getString("supply_center").equals(data[1]))) {
//                log.error(data[3] + "不在" + data[1] + "供应中心");
//                throw new InvalidDataException("供应商不属于该供应中心");
//            }
//            String check2 = "select * from center where center.name = ?";
//            var stmt2 = conn.prepareStatement(check2);
//            stmt2.setString(1, data[1]);
//            var rs2 = stmt2.executeQuery();
//            if (!rs2.next()) {
//                log.error(data[1] + "不存在");
//                throw new InvalidDataException("供应中心不存在");
//            }
//            String check3 = "select * from model where model.model = ?";
//            var stmt3 = conn.prepareStatement(check3);
//            stmt3.setString(1, data[2]);
//            var rs3 = stmt3.executeQuery();
//            if (!rs3.next()) {
//                log.error(data[2] + "不存在");
//                throw new InvalidDataException("产品型号不存在");
//            }
//            String check4 = "select * from warehousing where center_name = ? and model_name = ?;";
//            var stmt4 = conn.prepareStatement(check4);
//            stmt4.setString(1, data[1]);
//            stmt4.setString(2, data[2]);
//            var rs4 = stmt4.executeQuery();
//            if (rs4.next()) {
//                String sql = "update warehousing set quantity = quantity + ? where center_name = ? and model_name = ?;";
//                var stmt = conn.prepareStatement(sql);
//                stmt.setInt(1, Integer.parseInt(data[6]));
//                stmt.setString(2, data[1]);
//                stmt.setString(3, data[2]);
//                stmt.executeUpdate();
//            } else {
//                String sql = "insert into warehousing(center_name,model_name,quantity) values(?,?,?)";
//                var stmt = conn.prepareStatement(sql);
//                stmt.setString(1, data[1]);
//                stmt.setString(2, data[2]);
//                stmt.setInt(3, Integer.parseInt(data[6]));
//                stmt.executeUpdate();
//            }
//            String sql = "insert into stockin(id,supply_center,product_model," +
//                    "supply_staff,date,purchase_price,quantity) values(?,?,?,?,?,?,?)";
//            var stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, Integer.parseInt(data[0]));
//            stmt.setString(2, data[1]);
//            stmt.setString(3, data[2]);
//            stmt.setString(4, data[3]);
//            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[4])) {
//                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
//                Date date1 = jsFormat.parseDateTime(data[4]).toDate();
//                java.sql.Date date = new java.sql.Date(date1.getTime());
//                stmt.setDate(5, date);
//            } else {
//                java.sql.Date date = java.sql.Date.valueOf(data[4].replace('/', '-'));
//                stmt.setDate(5, date);
//            }
//            stmt.setString(6, data[5]);
//            stmt.setInt(7, Integer.parseInt(data[6]));
//            stmt.executeUpdate();
//            conn.commit();
//            conn.close();
        return CompletableFuture.completedFuture(true);
    }

    //contract_num,enterprise,product_model,quantity,contract_manager,contract_date,
    // estimated_delivery_date, lodgement_date,salesman_num,contract_type
    @Async("dgy")
    public CompletableFuture<Boolean> placeOrder(String[] data) throws InvalidDataException {
        String[] sql;
        List<Object[]> objects;
        String check3 = "select *\n" +
                "from warehousing w\n" +
                "         join (select e.supply_center\n" +
                "               from  enterprise e\n" +
                "               where e.name = ?) as cesc\n" +
                "              on w.center_name = cesc.supply_center and w.model_name = ? and w.quantity >= ? for update;";
        List<Map<String, Object>> check4 = jdbcTemplate.queryForList(check3, data[1], data[2], Integer.parseInt(data[3]));
        Date estimated_delivery_date, lodgement_date;
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[6])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            estimated_delivery_date = jsFormat.parseDateTime(data[6]).toDate();
        } else {
            estimated_delivery_date = java.sql.Date.valueOf(data[6].replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[7])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            lodgement_date = jsFormat.parseDateTime(data[7]).toDate();
        } else {
            lodgement_date = java.sql.Date.valueOf(data[7].replace('/', '-'));
        }
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbcTemplate.queryForList(check1, data[8]);
        if (check2.isEmpty()) {
            log.error("员工不存在");
            return CompletableFuture.completedFuture(false);
        }
        if (!(check2.get(0).get("stafftype").toString().equals("3"))) {
            log.error("员工不是销售员");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("该员工不是salesman");
        }
        if (check4.size() == 0) {
            log.error("库存不足");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("库存不足");
        }
        sql = new String[3];
        objects = new ArrayList<>();
        sql[0] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?;";
        objects.add(new Object[]{Integer.parseInt(data[3]), check4.get(0).get("center_name"), data[2]});
        jdbcTemplate.update(sql[0], objects.get(0));
        String check7 = "select count(*) from sold where model_name = ?";
        Integer check8 = jdbcTemplate.queryForObject(check7, Integer.class, data[2]);
        if (check8 != null && check8 == 0) {
            sql[1] = "insert into sold (model_name, quantity) values (?, ?)";
            objects.add(new Object[]{data[2], Integer.parseInt(data[3])});
            jdbcTemplate.update(sql[1], objects.get(1));
        } else {
            sql[1] = "update sold set quantity = quantity + ? where model_name = ?";
            objects.add(new Object[]{Integer.parseInt(data[3]), data[2]});
            jdbcTemplate.update(sql[1], objects.get(1));
        }
        sql[2] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman)\n" +
                "values (?, ?, ?, ?, ?, ?)";
        objects.add(new Object[]{data[0], data[2], Integer.parseInt(data[3]), estimated_delivery_date, lodgement_date, data[8]});
        jdbcTemplate.update(sql[2], objects.get(2));
//        String check5 = "select * from contract where number = ?";
//        List<Map<String, Object>> check6 = jdbcTemplate.queryForList(check5, data[0]);
//        if (true) {
//            sql = new String[3];
//            objects = new ArrayList<>();
//            String check7 = "select count(*) from sold where model_name = ?";
//            Integer check8 = jdbcTemplate.queryForObject(check7, Integer.class, data[2]);
//            if (check8 != null && check8 == 0) {
//                sql[0] = "insert into sold (model_name, quantity) values (?, ?)";
//                objects.add(new Object[]{data[2], Integer.parseInt(data[3])});
//                jdbcTemplate.update(sql[0], objects.get(0));
//            } else {
//                sql[0] = "update sold set quantity = quantity + ? where model_name = ?";
//                objects.add(new Object[]{Integer.parseInt(data[3]), data[2]});
//                jdbcTemplate.update(sql[0], objects.get(0));
//            }
//            sql[1] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman)\n" +
//                    "values (?, ?, ?, ?, ?, ?)";
//            objects.add(new Object[]{data[0], data[2], Integer.parseInt(data[3]), estimated_delivery_date, lodgement_date, data[8]});
//            sql[2] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
//            objects.add(new Object[]{Integer.parseInt(data[3]), check4.get(0).get("center_name"), data[2]});
//            jdbcTemplate.update(sql[1], objects.get(1));
//            jdbcTemplate.update(sql[2], objects.get(2));
//        } else {
//            sql = new String[4];
//            objects = new ArrayList<>();
//            String check7 = "select count(*) from sold where model_name = ?";
//            Integer check8 = jdbcTemplate.queryForObject(check7, Integer.class, data[2]);
//            if (check8 != null && check8 == 0) {
//                sql[0] = "insert into sold (model_name, quantity) values (?, ?)";
//                objects.add(new Object[]{data[2], Integer.parseInt(data[3])});
//                jdbcTemplate.update(sql[0], objects.get(0));
//            } else {
//                sql[0] = "update sold set quantity = quantity + ? where model_name = ?";
//                objects.add(new Object[]{Integer.parseInt(data[3]), data[2]});
//                jdbcTemplate.update(sql[0], objects.get(0));
//            }
////            sql[1] = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
////                    "values (?, ?, ?, ?, ?)";
//            objects.add(new Object[]{data[0], data[1], contractdate, data[4], data[9]});
//            sql[2] = "insert into contract_content (contract_number, product_model_name, quantity, " +
//                    "estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
//            objects.add(new Object[]{data[0], data[2], Integer.parseInt(data[3]), estimated_delivery_date, lodgement_date, data[8]});
//            sql[3] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
//            objects.add(new Object[]{Integer.parseInt(data[3]), check4.get(0).get("center_name"), data[2]});
//            //jdbcTemplate.update(sql[1], objects.get(1));
//            jdbcTemplate.update(sql[2], objects.get(2));
//            jdbcTemplate.update(sql[3], objects.get(3));
// }
//            var conn = hikariDataSource.getConnection();
//            String check1 = "select * from staff where staff.number = ?";
//            var stmt1 = conn.prepareStatement(check1);
//            stmt1.setString(1, data[8]);
//            var rs1 = stmt1.executeQuery();
//            if (!rs1.next()) {
//                log.error(data[8] + " is not a valid staff number");
//                throw new InvalidDataException("员工编号不存在");
//            }
//            if (rs1.getInt("stafftype") != 3) {
//                throw new InvalidDataException("该员工不是salesman");
//            }
//            String check2 = "select *\n" +
//                    "from warehousing w\n" +
//                    "         join (select e.supply_center\n" +
//                    "               from  enterprise e\n" +
//                    "               where e.name = ?) as cesc\n" +
//                    "              on w.center_name = cesc.supply_center " +
//                    "                   and w.model_name = ? " +
//                    "                   and w.quantity >= ?;";
//            var stmt2 = conn.prepareStatement(check2);
//            stmt2.setString(1, data[1]);
//            stmt2.setString(2, data[2]);
//            stmt2.setInt(3, Integer.parseInt(data[3]));
//            var rs2 = stmt2.executeQuery();
//            if (!rs2.next()) {
//                throw new InvalidDataException("库存不足");
//            }
//            String check3 = "select * from contract where number = ?";
//            var stmt3 = conn.prepareStatement(check3);
//            stmt3.setString(1, data[0]);
//            var rs3 = stmt3.executeQuery();
//            String check4 = "select count(*) from sold where model_name = ?";
//            var stmt4 = conn.prepareStatement(check4);
//            stmt4.setString(1, data[2]);
//            var rs4 = stmt4.executeQuery();
//            Date estimated_delivery_date1, lodgement_date1, contractdate1;
//            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
//                    data[6])) {
//                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
//                estimated_delivery_date1 = jsFormat.parseDateTime(data[6]).toDate();
//            } else {
//                estimated_delivery_date1 = java.sql.Date.valueOf(data[6].replace('/', '-'));
//            }
//            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
//                    data[7])) {
//                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
//                lodgement_date1 = jsFormat.parseDateTime(data[7]).toDate();
//            } else {
//                lodgement_date1 = java.sql.Date.valueOf(data[7].replace('/', '-'));
//            }
//            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
//                    data[5])) {
//                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
//                contractdate1 = jsFormat.parseDateTime(data[5]).toDate();
//            } else {
//                contractdate1 = java.sql.Date.valueOf(data[5].replace('/', '-'));
//            }
//            java.sql.Date estimated_delivery_date = new java.sql.Date(estimated_delivery_date1.getTime());
//            java.sql.Date lodgement_date = new java.sql.Date(lodgement_date1.getTime());
//            java.sql.Date contractdate = new java.sql.Date(contractdate1.getTime());
//            if (rs3.next()) {
//                String sql = "insert into contract_content (contract_number, product_model_name, quantity, " +
//                        "estimated_delivery_date, lodgement_date, salesman) " +
//                        "values (?, ?, ?, ?, ?, ?)";
//                var stmt = conn.prepareStatement(sql);
//                stmt.setString(1, data[0]);
//                stmt.setString(2, data[2]);
//                stmt.setInt(3, Integer.parseInt(data[3]));
//                stmt.setDate(4, estimated_delivery_date);
//                stmt.setDate(5, lodgement_date);
//                stmt.setString(6, data[8]);
//                stmt.executeUpdate();
//                String sql1 = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
//                stmt = conn.prepareStatement(sql1);
//                stmt.setInt(1, Integer.parseInt(data[3]));
//                stmt.setString(2, rs2.getString("center_name"));
//                stmt.setString(3, data[2]);
//                stmt.executeUpdate();
//                if (!rs4.next()) {
//                    String sql2 = "insert into sold (model_name, quantity) values (?, ?)";
//                    stmt = conn.prepareStatement(sql2);
//                    stmt.setString(1, data[2]);
//                    stmt.setInt(2, Integer.parseInt(data[3]));
//                    stmt.executeUpdate();
//                } else {
//                    String sql2 = "update sold set quantity = quantity + ? where model_name = ?";
//                    stmt = conn.prepareStatement(sql2);
//                    stmt.setInt(1, Integer.parseInt(data[3]));
//                    stmt.setString(2, data[2]);
//                    stmt.executeUpdate();
//                }
//            } else {
//                String sql = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
//                        "values (?, ?, ?, ?, ?)";
//                var stmt = conn.prepareStatement(sql);
//                stmt.setString(1, data[0]);
//                stmt.setString(2, data[1]);
//                stmt.setDate(3, contractdate);
//                stmt.setString(4, data[6]);
//                stmt.setString(5, data[7]);
//                stmt.executeUpdate();
//                String sql1 = "insert into contract_content (contract_number, product_model_name, quantity, " +
//                        "estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
//                stmt = conn.prepareStatement(sql1);
//                stmt.setString(1, data[0]);
//                stmt.setString(2, data[2]);
//                stmt.setInt(3, Integer.parseInt(data[3]));
//                stmt.setDate(4, estimated_delivery_date);
//                stmt.setDate(5, lodgement_date);
//                stmt.setString(6, data[8]);
//                stmt.executeUpdate();
//                String sql2 = "update warehousing set quantity = quantity - ? " +
//                        "where center_name = ? and model_name = ?";
//                stmt = conn.prepareStatement(sql2);
//                stmt.setInt(1, Integer.parseInt(data[3]));
//                stmt.setString(2, rs2.getString("center_name"));
//                stmt.setString(3, data[2]);
//                stmt.executeUpdate();
//                if (!rs4.next()) {
//                    String sql3 = "insert into sold (model_name, quantity) values (?, ?)";
//                    stmt = conn.prepareStatement(sql3);
//                    stmt.setString(1, data[2]);
//                    stmt.setInt(2, Integer.parseInt(data[3]));
//                    stmt.executeUpdate();
//                } else {
//                    String sql3 = "update sold set quantity = quantity + ? where model_name = ?";
//                    stmt = conn.prepareStatement(sql3);
//                    stmt.setInt(1, Integer.parseInt(data[3]));
//                    stmt.setString(2, data[2]);
//                    stmt.executeUpdate();
//                }
//            }
//            conn.close();

        return CompletableFuture.completedFuture(true);
    }

    @Async("dgy")
    public void insertContract(String[] data) {
        //            sql[1] = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
//                    "values (?, ?, ?, ?, ?)";
        //objects.add(new Object[]{data[0], data[1], contractdate, data[4], data[9]});
        String sql = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
                "values (?, ?, ?, ?, ?)";
        Date contractdate;
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[5])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            contractdate = jsFormat.parseDateTime(data[5]).toDate();
        } else {
            contractdate = java.sql.Date.valueOf(data[5].replace('/', '-'));
        }
        try {
            jdbcTemplate.update(sql, data[0], data[1], contractdate, data[4], data[9]);
        } catch (Exception ignored) {
        }
    }

    public Boolean placeOrderSingleThread(String[] data) throws InvalidDataException {
        String[] sql;
        List<Object[]> objects;
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbcTemplate.queryForList(check1, data[8]);
        if (check2.isEmpty()) {
            return false;
        }
        if (!(check2.get(0).get("stafftype").toString().equals("3"))) {
            return false;
        }
        String check3 = "select *\n" +
                "from warehousing w\n" +
                "         join (select e.supply_center\n" +
                "               from  enterprise e\n" +
                "               where e.name = ?) as cesc\n" +
                "              on w.center_name = cesc.supply_center and w.model_name = ? and w.quantity >= ?;";
        List<Map<String, Object>> check4 = jdbcTemplate.queryForList(check3, data[1],
                data[2], Integer.parseInt(data[3]));
        if (check4.size() == 0) {
            return false;
        }
        String check5 = "select * from contract where number = ?";
        List<Map<String, Object>> check6 = jdbcTemplate.queryForList(check5, data[0]);
        String check7 = "select count(*) from sold where model_name = ?";
        Integer check8 = jdbcTemplate.queryForObject(check7, Integer.class, data[2]);
        Date estimated_delivery_date, lodgement_date, contractdate;
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[6])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            estimated_delivery_date = jsFormat.parseDateTime(data[6]).toDate();
        } else {
            estimated_delivery_date = java.sql.Date.valueOf(data[6].replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[7])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            lodgement_date = jsFormat.parseDateTime(data[7]).toDate();
        } else {
            lodgement_date = java.sql.Date.valueOf(data[7].replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", data[5])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            contractdate = jsFormat.parseDateTime(data[5]).toDate();
        } else {
            contractdate = java.sql.Date.valueOf(data[5].replace('/', '-'));
        }
        if (check6.size() != 0) {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman)\n" +
                    "values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{data[0], data[2], Integer.parseInt(data[3]), estimated_delivery_date, lodgement_date, data[8]});
            sql[1] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{Integer.parseInt(data[3]), check4.get(0).get("center_name"), data[2]});
            if (check8 != null && check8 == 0) {
                sql[2] = "insert into sold (model_name, quantity) values (?, ?)";
                objects.add(new Object[]{data[2], Integer.parseInt(data[3])});
            } else {
                sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
                objects.add(new Object[]{Integer.parseInt(data[3]), data[2]});
            }
            jdbcTemplate.update(sql[0], objects.get(0));
            jdbcTemplate.update(sql[1], objects.get(1));
            jdbcTemplate.update(sql[2], objects.get(2));
        } else {
            sql = new String[4];
            objects = new ArrayList<>();
            sql[0] = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
                    "values (?, ?, ?, ?, ?)";
            objects.add(new Object[]{data[0], data[1], contractdate, data[4], data[9]});
            sql[1] = "insert into contract_content (contract_number, product_model_name, quantity, " +
                    "estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{data[0], data[2], Integer.parseInt(data[3]), estimated_delivery_date, lodgement_date, data[8]});
            sql[2] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{Integer.parseInt(data[3]), check4.get(0).get("center_name"), data[2]});
            if (check8 != null && check8 == 0) {
                sql[3] = "insert into sold (model_name, quantity) values (?, ?)";
                objects.add(new Object[]{data[2], Integer.parseInt(data[3])});
            } else {
                sql[3] = "update sold set quantity = quantity + ? where model_name = ?";
                objects.add(new Object[]{Integer.parseInt(data[3]), data[2]});
            }
            jdbcTemplate.update(sql[0], objects.get(0));
            jdbcTemplate.update(sql[1], objects.get(1));
            jdbcTemplate.update(sql[2], objects.get(2));
            jdbcTemplate.update(sql[3], objects.get(3));
        }
        return true;
    }


    //data[]:contract	product_model	salesman	quantity	estimate_delivery_date	lodgement_date
    public CompletableFuture<Boolean> updateOrder(String[] data) throws InvalidDataException {
        String[] sql;
        List<Object[]> objects;
        String check1 = "select * from contract_content where contract_number = ? " +
                "and salesman = ? and product_model_name = ?";
        List<Map<String, Object>> check2 = jdbcTemplate.queryForList(check1, data[0], data[2], data[1]);
        if (check2.size() == 0) {
            log.error("该合同不属于该销售员");
            return CompletableFuture.completedFuture(false);
        }
        String check3 = "select supply_center from contract join enterprise e " +
                "on contract.enterprise = e.name where number = ?;";
        String supplyCenter = jdbcTemplate.queryForObject(check3, String.class, data[0]);
        int quantity = Integer.parseInt(data[3]) - Integer.parseInt(check2.get(0).get("quantity").toString());
        Date estimated_delivery_date1, lodgement_date1;
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
                data[4])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            estimated_delivery_date1 = jsFormat.parseDateTime(data[4]).toDate();
        } else {
            estimated_delivery_date1 = java.sql.Date.valueOf(data[4].replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
                data[5])) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            lodgement_date1 = jsFormat.parseDateTime(data[5]).toDate();
        } else {
            lodgement_date1 = java.sql.Date.valueOf(data[5].replace('/', '-'));
        }
        java.sql.Date estimated_delivery_date = new java.sql.Date(estimated_delivery_date1.getTime());
        java.sql.Date lodgement_date = new java.sql.Date(lodgement_date1.getTime());
        if (data[3].equals("0")) {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
            objects.add(new Object[]{data[0], data[1], data[2]});
            sql[1] = "update sold set quantity = quantity + ? where model_name = ?";
            objects.add(new Object[]{quantity, data[1]});
            sql[2] = "update warehousing set quantity = quantity - ? where model_name = ? and center_name = ?;";
            objects.add(new Object[]{quantity, data[1], supplyCenter});
            jdbcTemplate.update(sql[0], objects.get(0));
            jdbcTemplate.update(sql[1], objects.get(1));
            jdbcTemplate.update(sql[2], objects.get(2));
        } else {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "update contract_content set quantity = ?, estimated_delivery_date = ?, " +
                    "lodgement_date = ? where contract_number = ? and product_model_name = ? " +
                    "and salesman = ?";
            objects.add(new Object[]{Integer.parseInt(data[3]),
                    estimated_delivery_date, lodgement_date, data[0],
                    data[1], data[2]});
            sql[1] = "update warehousing set quantity = quantity - ? where model_name = ? " +
                    "and center_name = ?;";
            objects.add(new Object[]{quantity, data[1], supplyCenter});
            sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
            objects.add(new Object[]{quantity, data[1]});
            jdbcTemplate.update(sql[0], objects.get(0));
            jdbcTemplate.update(sql[1], objects.get(1));
            jdbcTemplate.update(sql[2], objects.get(2));
        }

//            var conn = hikariDataSource.getConnection();
//            String check1 = "select * from contract_content where contract_number = ? " +
//                    "and salesman = ? and product_model_name = ?";
//            var stmt = conn.prepareStatement(check1);
//            stmt.setString(1, data[0]);
//            stmt.setString(2, data[2]);
//            stmt.setString(3, data[1]);
//            var rs1 = stmt.executeQuery();
//            if (!rs1.next()) {
//                throw new InvalidDataException("该合同不属于该销售员");
//            }
//            String check2 = "select supply_center from contract join enterprise e " +
//                    "on contract.enterprise = e.name where number = ?;";
//            stmt = conn.prepareStatement(check2);
//            stmt.setString(1, data[0]);
//            var rs = stmt.executeQuery();
//            String supplyCenter = rs.getString("supply_center");
//            int quantity = Integer.parseInt(data[3]) - Integer.parseInt(rs1.getString("quantity"));
//            Date estimated_delivery_date1, lodgement_date1;
//            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
//                    data[6])) {
//                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
//                estimated_delivery_date1 = jsFormat.parseDateTime(data[6]).toDate();
//            } else {
//                estimated_delivery_date1 = java.sql.Date.valueOf(data[6].replace('/', '-'));
//            }
//            if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$",
//                    data[7])) {
//                DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
//                lodgement_date1 = jsFormat.parseDateTime(data[7]).toDate();
//            } else {
//                lodgement_date1 = java.sql.Date.valueOf(data[7].replace('/', '-'));
//            }
//            java.sql.Date estimated_delivery_date = new java.sql.Date(estimated_delivery_date1.getTime());
//            java.sql.Date lodgement_date = new java.sql.Date(lodgement_date1.getTime());
//            if (data[3].equals("0")) {
//                String[] sql = new String[3];
//                sql[0] = "delete from contract_content where contract_number = ? " +
//                        "and product_model_name = ? and salesman = ?";
//                stmt = conn.prepareStatement(sql[0]);
//                stmt.setString(1, data[0]);
//                stmt.setString(2, data[1]);
//                stmt.setString(3, data[2]);
//                stmt.executeUpdate();
//                sql[1] = "update sold set quantity = quantity + ? where model_name = ?";
//                stmt = conn.prepareStatement(sql[1]);
//                stmt.setInt(1, quantity);
//                stmt.setString(2, data[1]);
//                stmt.executeUpdate();
//                sql[2] = "update warehousing set quantity = quantity - ? where model_name = ? and center_name = ?;";
//                stmt = conn.prepareStatement(sql[2]);
//                stmt.setInt(1, quantity);
//                stmt.setString(2, data[1]);
//                stmt.setString(3, supplyCenter);
//                stmt.executeUpdate();
//            } else {
//                String[] sql = new String[3];
//                sql[0] = "update contract_content set quantity = ?, estimated_delivery_date = ?, " +
//                        "lodgement_date = ? where contract_number = ? and product_model_name = ? " +
//                        "and salesman = ?";
//                stmt = conn.prepareStatement(sql[0]);
//                stmt.setInt(1, Integer.parseInt(data[3]));
//                stmt.setDate(2, estimated_delivery_date);
//                stmt.setDate(3, lodgement_date);
//                stmt.setString(4, data[0]);
//                stmt.setString(5, data[1]);
//                stmt.setString(6, data[2]);
//                stmt.executeUpdate();
//                sql[1] = "update warehousing set quantity = quantity - ? where model_name = ? " +
//                        "and center_name = ?;";
//                stmt = conn.prepareStatement(sql[1]);
//                stmt.setInt(1, quantity);
//                stmt.setString(2, data[1]);
//                stmt.setString(3, supplyCenter);
//                stmt.executeUpdate();
//                sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
//                stmt = conn.prepareStatement(sql[2]);
//                stmt.setInt(1, quantity);
//                stmt.setString(2, data[1]);
//                stmt.executeUpdate();
//            }
//            conn.close();
        return CompletableFuture.completedFuture(true);
    }


    //contract	salesman	seq
    public CompletableFuture<Boolean> deleteOrder(String[] data) throws InvalidDataException {
        String[] sql;
        List<Object[]> objects;
        String check1 = "select * from contract_content where contract_number = ? and salesman = ? " +
                "order by estimated_delivery_date, product_model_name;";
        List<Map<String, Object>> check2 = jdbcTemplate.queryForList(check1, data[0], data[1]);
        if (check2.size() < Integer.parseInt(data[2])) {
            log.error("删除订单失败，该合同不存在该销售员的订单");
            return CompletableFuture.completedFuture(false);
            //throw new InvalidDataException("该合同不属于该销售员");
        }
        var content = check2.get(Integer.parseInt(data[2]) - 1);
        String check3 = "select count(*) from sold where model_name = ?";
        Integer check4 = jdbcTemplate.queryForObject(check3, Integer.class, content.get("product_model_name"));
        String check5 = "select supply_center from contract join enterprise e on contract.enterprise = e.name\n" +
                "    where number = ?;";
        String check6 = jdbcTemplate.queryForObject(check5, String.class, data[0]);
        sql = new String[3];
        objects = new ArrayList<>();
        sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
        objects.add(new Object[]{data[0], content.get("product_model_name"), data[1]});
        sql[1] = "update warehousing set quantity = quantity + ? where model_name = ? and center_name = ?;";
        objects.add(new Object[]{Integer.parseInt(content.get("quantity").toString()), content.get("product_model_name"), check6});
        if (check4 != null && check4 == Integer.parseInt(content.get("quantity").toString())) {
            sql[2] = "delete from sold where model_name = ?";
            objects.add(new Object[]{content.get("product_model_name")});
        } else {
            sql[2] = "update sold set quantity = quantity - ? where model_name = ?";
            objects.add(new Object[]{Integer.parseInt(content.get("quantity").toString()), content.get("product_model_name")});
        }
        jdbcTemplate.update(sql[0], objects.get(0));
        jdbcTemplate.update(sql[1], objects.get(1));
        jdbcTemplate.update(sql[2], objects.get(2));
//            var conn = hikariDataSource.getConnection();
//            String check1 = "select * from contract_content where contract_number = ? and salesman = ? " +
//                    "order by estimated_delivery_date, product_model_name;";
//            var stmt = conn.prepareStatement(check1);
//            stmt.setString(1, data[0]);
//            stmt.setString(2, data[1]);
//            var rs = stmt.executeQuery();
//            rs.last();
//            int count = rs.getRow();
//            if (count < Integer.parseInt(data[2])) {
//                throw new InvalidDataException("该合同不属于该销售员");
//            }
//            rs.first();
//            for (int i = 0; i < Integer.parseInt(data[2]); i++) {
//                rs.next();
//            }
//            String check2 = "select count(*) cnt from sold where model_name = ?";
//            stmt = conn.prepareStatement(check2);
//            stmt.setString(1, data[3]);
//            var rs1 = stmt.executeQuery();
//            int cnt = rs1.getInt("cnt");
//            String check3 = "select supply_center from contract join enterprise e on contract.enterprise = e.name\n" +
//                    "    where number = ?;";
//            stmt = conn.prepareStatement(check3);
//            stmt.setString(1, data[0]);
//            var rs2 = stmt.executeQuery();
//            String supplyCenter = rs2.getString("supply_center");
//            String[] sql = new String[3];
//            sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
//            stmt = conn.prepareStatement(sql[0]);
//            stmt.setString(1, data[0]);
//            stmt.setString(2, rs.getString("product_model_name"));
//            stmt.setString(3, data[1]);
//            stmt.executeUpdate();
//            sql[1] = "update warehousing set quantity = quantity + ? where model_name = ? and center_name = ?";
//            stmt = conn.prepareStatement(sql[1]);
//            stmt.setInt(1, rs.getInt("quantity"));
//            stmt.setString(2, rs.getString("product_model_name"));
//            stmt.setString(3, supplyCenter);
//            stmt.executeUpdate();
//            sql[2] = "update sold set quantity = quantity - ? where model_name = ?";
//            stmt = conn.prepareStatement(sql[2]);
//            stmt.setInt(1, rs.getInt("quantity"));
//            stmt.setString(2, rs.getString("product_model_name"));
//            stmt.executeUpdate();
//            stmt.close();
//            conn.close();
        return CompletableFuture.completedFuture(true);
    }
}

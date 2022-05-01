package ntdgy.cs307project2.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;

@Controller
@RequestMapping("/api/database")
public class DatabaseController {

    final DataSource dataSource;

    final JdbcTemplate jdbc;

    public DatabaseController(DataSource dataSource, JdbcTemplate jdbc){
        this.dataSource = dataSource;
        this.jdbc = jdbc;
    }

    @PostMapping("/center/add")
    public String addCenter(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            Model model
    ) throws SQLException {
        String sql = "insert into center(id, name) values(?, ?)";
        Object[] obj = new Object[2];
        obj[0] = id;
        obj[1] = name;
        jdbc.update(sql, obj);
        model.addAttribute("centermsg", "success");
        return "childPages/management";
    }

    @PostMapping("/center/select")
    public String selectCenter(
            @RequestParam("id") String id,
            @RequestParam("name") String name
    ){
        return "";
    }

    @PostMapping("/center/delete")
    public String deleteCenter(
            @RequestParam("id") String id,
            @RequestParam("name") String name
    ){
        return "";
    }

    @PostMapping("/center/update")
    public String updateCenter(
            @RequestParam("id") String id,
            @RequestParam("name") String name
    ){
        String sql = "update into center(id, name) values(?, ?)";
        Object[] obj = new Object[2];
        obj[0] = id;
        obj[1] = name;
        jdbc.update(sql, obj);
        return "";
    }

    @PostMapping("/enterprise/add")
    public String addEnterprise(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("country") String country,
            @RequestParam("city") @Nullable String city,
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("industry") String industry
    ) throws SQLException {
        String sql = "insert into enterprise(id, name, supply_center_id, country, city, industry) values(?, ?, ?, ?, ?, ?)";
        Object[] obj = new Object[6];

        return "";
    }

    @PostMapping("/enterprise/select")
    public String selectEnterprise(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("industry") String industry
    ){
        return "";
    }

    @PostMapping("/enterprise/delete")
    public String deleteEnterprise(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("industry") String industry
    ){
        return "";
    }

    @PostMapping("/enterprise/update")
    public String updateEnterprise(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("industry") String industry
    ){
        return "";
    }

    @PostMapping("/stockIn")
    public String stockIn(
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("product_model") String productModel,
            @RequestParam("supply_staff") String supplyStaff,
            @RequestParam("date") String date,
            @RequestParam("purchase_price") String price,
            @RequestParam("quantity") String quantity
    ){
        return "";
    }

}

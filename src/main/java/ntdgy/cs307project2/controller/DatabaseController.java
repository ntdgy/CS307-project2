package ntdgy.cs307project2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;

@Controller
@RequestMapping("/api/database")
public class DatabaseController {

    final DataSource dataSource;

    public DatabaseController(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @PostMapping("/enterprise/add")
    public String addEnterprise(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("industry") String industry
    ) throws SQLException {
        var con = dataSource.getConnection();
        var stmt = con.prepareStatement("insert into enterprise(id, name, supply_center_id, country, city, industry)"
                + " values(?, ?, ?, ?, ?, ?)");
        //stmt.setInt();
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

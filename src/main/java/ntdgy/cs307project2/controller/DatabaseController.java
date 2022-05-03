package ntdgy.cs307project2.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/database")
public class DatabaseController {

    final DataSource dataSource;

    final JdbcTemplate jdbc;

    public DatabaseController(DataSource dataSource, JdbcTemplate jdbc) {
        this.dataSource = dataSource;
        this.jdbc = jdbc;
    }

    @PostMapping("/center")
    public String addCenter(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam(value = "updateid", required = false) String updateId,
            @RequestParam(value = "updatename", required = false) String updateName,
            Model model
    ) {
        String sql;
        Object[] obj;
        if(type.equals("Insert")){
            if (id.equals("")) {
                sql = "insert into center(name) values(?)";
                obj = new Object[1];
                obj[0] = name;
            } else {
                sql = "insert into center(id, name) values(?, ?)";
                obj = new Object[2];
                obj[0] = Integer.parseInt(id);
                obj[1] = name;
            }
        } else if(type.equals("Update")){ //TODO
            sql = "update center set id=?, name=? where id=? and name=?";
            obj = new Object[4];
            obj[0] = Integer.parseInt(id);
            obj[1] = name;
            obj[2] = Integer.parseInt(updateId);
            obj[3] = updateName;
        } else if(type.equals("Delete")){
            sql= "delete from center where name = ?";
            obj = new Object[1];
            obj[0] = name;
        } else if(type.equals("Select")){ //TODO
            sql = "";
            obj = new Object[1];
        } else {
            model.addAttribute("centermsg", "Failed");
            return "childPages/management";
        }
        model.addAttribute("centermsg", "Failed");
        try {
            jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e){
            throw e;
        }
        return "childPages/management";
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
    ) {
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
    ) {
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
    ) {
        return "";
    }

    @PostMapping("/stockIn")
    public String stockIn(
            @RequestParam("supply_center") String supplyCenter,
            @RequestParam("product_model") String productModel,
            @RequestParam("supply_staff") String supplyStaff,
            @RequestParam("date") String date,
            @RequestParam("type") String type,
            @RequestParam("purchase_price") String price,
            @RequestParam("quantity") String quantity,
            Model model
    ) {
        String sql;
        Object[] obj;
        if(type.equals("Insert")){
            String check1 = "select * from staff where staff.number = ?";
            List<Map<String, Object>> check2 = jdbc.queryForList(check1, supplyStaff);
            if(check2.size() == 0){
                    model.addAttribute("centermsg", "⼈员不存在");
                    return "childPages/management";
            }
            if (!check2.get(0).get("type").equals("1")){
                model.addAttribute("centermsg", "⼈员的类型不是supply_staff");
                return "childPages/management";
            }
            String check3 = "select * from center where center.name = ?";
            List<Map<String, Object>> check4 = jdbc.queryForList(check3, supplyCenter);
            if(check4.size() == 0){
                model.addAttribute("centermsg", "供应中⼼不存在");
                return "childPages/management";
            }
            String check5 = "select * from model where model.model = ?";
            List<Map<String, Object>> check6 = jdbc.queryForList(check5, productModel);
            if(check6.size() == 0){
                model.addAttribute("centermsg", "产品型号不存在");
            }
            String check7 = "select c.name from staff join center c on staff.supply_center_id = c.id;";
            List<Map<String, Object>> check8 = jdbc.queryForList(check7, supplyStaff);
            if(!check8.get(0).get("name").equals(supplyCenter)){
                model.addAttribute("centermsg", "供应中⼼与⼈员所在的供应中⼼对应不上");
            }
            sql = "insert into orders(supply_center_id, product_model, supply_staff, date, purchase_price, quantity) values(?, ?, ?, ?, ?, ?)";
            obj = new Object[6];
            obj[0] = supplyCenter;
            obj[1] = productModel;
            obj[2] = supplyStaff;
            obj[3] = date;
            obj[4] = price;
            obj[5] = quantity;
        } else {
            sql = "update orders set supply_center_id = ?, product_model = ?, supply_staff = ?, date = ?, purchase_price = ?, quantity = ? where id = ?";
            obj = new Object[7];
        }
        model.addAttribute("centermsg", "Failed");
        try {
            jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e){
            throw e;
        }
        return "childPages/management";
    }

    public boolean login(String name, String pwd) {
        String salt = "djj is super smart and beautiful mei shao nv";
        pwd = pwd + salt;
        String encodeStr = DigestUtils.md5DigestAsHex(pwd.getBytes());
        String sql = "select * from users where passwd_md5 = ? and user_name = ?";
        Object[] temp = new Object[2];
        temp[0] = encodeStr;
        temp[1] = name;
        List<Map<String, Object>> re = jdbc.queryForList(sql, temp);
        return !re.isEmpty();
    }

    public boolean signup(String name, String pwd, String rechapter) {
        String salt = "djj is super smart and beautiful mei shao nv";
        pwd = pwd + salt;
        String encodeStr = DigestUtils.md5DigestAsHex(pwd.getBytes());
        String sql = "select * from rechapter where chapter = ? ";
        Object[] temp = new Object[1];
        temp[0] = rechapter;
        List<Map<String, Object>> re = jdbc.queryForList(sql, temp);
        if(re.isEmpty()) return false;
        if (re.get(0).get("isUsed") != null && (int)re.get(0).get("isUsed") >= 1) {
            sql = "update rechapter set isUsed = case when isUsed is null then 1 else isUsed + 1 end where chapter = ?;";
            return false;
        }else{
            sql = "update rechapter set isUsed = 1,user_name = ?, used_date = ? where chapter = ?;";
            Object[] tmp = new Object[3];
            tmp[0] = name;
            tmp[1] = new Date();
            tmp[2] = rechapter;
            jdbc.update(sql,tmp);
            sql = "insert into users(user_name, passwd_md5) values (?,?);" ;
            Object[] m1 = new Object[2];
            m1[0] = name;
            m1[1] = encodeStr;
            jdbc.update(sql,m1);
            return true;
        }
    }

}

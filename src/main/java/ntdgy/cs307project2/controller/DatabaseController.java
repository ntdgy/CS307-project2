package ntdgy.cs307project2.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

import javax.sql.DataSource;
import java.util.*;

@Controller
@RequestMapping("/api/database")
public class DatabaseController {


    final JdbcTemplate jdbc;

    public DatabaseController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostMapping("/select/center")
    @ResponseBody
    public List<Map<String, Object>> selectCenter(
            @RequestBody Map<String, Object> map
    ){
        String sql = "select * from center where";
        List<String> s = new LinkedList<>();
        for(var entry: map.entrySet()){
            if(entry.getValue() == null || entry.getValue().equals("")) {
                s.add(entry.getKey());
            }
        }
        for(String i: s){
            map.remove(i);
        }
        if(map.isEmpty())
            //todo: error code
            return null;
        Object[] obj = new Object[map.size()];
        int i = 0;
        for(var entry: map.entrySet()){
            if(i == 0){
                sql = sql + " " +  entry.getKey() + " = ?";
            } else {
                sql = sql + " and " +  entry.getKey() + " = ?";
            }
            obj[i++] = entry.getValue();
        }
        return jdbc.queryForList(sql, obj);
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
        if (type.equals("Insert")) {
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
        } else if (type.equals("Update")) { //TODO
            sql = "update center set id=?, name=? where id=? and name=?";
            obj = new Object[4];
            obj[0] = Integer.parseInt(id);
            obj[1] = name;
            obj[2] = Integer.parseInt(updateId);
            obj[3] = updateName;
        } else if (type.equals("Delete")) {
            sql = "delete from center where name = ?";
            obj = new Object[1];
            obj[0] = name;
        } else if (type.equals("Select")) { //TODO
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
        } catch (Exception e) {
            throw e;
        }
        return "childPages/management";
    }

    @PostMapping("/enterprise")
    public String addEnterprise(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("country") String country,
            @RequestParam("city") @Nullable String city,
            @RequestParam("supplycenter") String supplyCenter,
            @RequestParam("type") String type,
            @RequestParam("industry") String industry,
            Model model
    ) {
        String sql;
        Object[] obj;
        if (type.equals("Insert")) {
            sql = "insert into enterprise(id, name, country, city, supply_center, industry) values(?, ?, ?, ?, ?, ?)";
            obj = new Object[6];
            obj[0] = id;
            obj[1] = name;
            obj[2] = country;
            obj[3] = city;
            obj[4] = supplyCenter;
            obj[5] = industry;
        } else if (type.equals("Update")) {
            sql = "update enterprise set id=?, name=?, country=?, city=?, supply_center=?, industry=? where id=?";
            obj = new Object[7];
            obj[0] = id;
            obj[1] = name;
            obj[2] = country;
            obj[3] = city;
            obj[4] = supplyCenter;
            obj[5] = industry;
            obj[6] = id;
        } else if (type.equals("Delete")) {
            sql = "delete from enterprise where id = ?";
            obj = new Object[1];
            obj[0] = id;
        } else if (type.equals("Select")) {
            sql = "";
            obj = new Object[1];

        } else {
            model.addAttribute("centermsg", "操作异常");
            return "childPages/management";
        }
        try {
            jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e) {
            throw e;
        }
        return "childPages/management";
    }


    @PostMapping("/staff")
    public String addEnterprise(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("number") String number,
            @RequestParam("gender") String q,
            @RequestParam("age") String age,
            @RequestParam("mobilenumber") String mobileNumber,
            @RequestParam("supplycenterid") String supplyCenterId,
            @RequestParam("type") String type,
            Model model
    ) {
        String sql;
        Object[] obj;
        if (type.equals("Insert")) {
            sql = "insert into staff(id, name, number, q, age, mobile_number, supply_center_id) values(?, ?, ?, ?, ?, ?, ?)";
            obj = new Object[7];
            obj[0] = id;
            obj[1] = name;
            obj[2] = number;
            obj[3] = q;
            obj[4] = age;
            obj[5] = mobileNumber;
            obj[6] = supplyCenterId;
        } else if (type.equals("Update")) {
            sql = "update staff set id=?, name=?, number=?, q=?, age=?, mobile_number=?, supply_center_id=? where id=?";
            obj = new Object[8];
            obj[0] = id;
            obj[1] = name;
            obj[2] = number;
            obj[3] = q;
            obj[4] = age;
            obj[5] = mobileNumber;
            obj[6] = supplyCenterId;
            obj[7] = id;
        } else if (type.equals("Delete")) {
            sql = "delete from staff where id = ?";
            obj = new Object[1];
            obj[0] = id;

        } else if (type.equals("Select")) {
            sql = "";
            obj = new Object[1];
        } else {
            model.addAttribute("centermsg", "操作异常");
            return "childPages/management";
        }
        try {
            jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e) {
            throw e;
        }
        return "childPages/management";
    }


    @PostMapping("/model")
    public String stockIn(
            @RequestParam("id") int id,
            @RequestParam("number") String number,
            @RequestParam("model") String model1,
            @RequestParam("name") String name,
            @RequestParam("unitprice") String unitPrice,
            @RequestParam("type") String type,
            Model model
    ) {
        String sql;
        Object[] obj;
        if (type.equals("Insert")) {
            sql = "insert into model(id, number, model, name, unit_price) values(?, ?, ?, ?, ?)";
            obj = new Object[5];
            obj[0] = id;
            obj[1] = number;
            obj[2] = model1;
            obj[3] = name;
            obj[4] = unitPrice;
        } else if (type.equals("Update")) {
            sql = "update model set number=?, model=?, name=?, unit_price=? where id=?";
            obj = new Object[5];
            obj[0] = number;
            obj[1] = model1;
            obj[2] = name;
            obj[3] = unitPrice;
            obj[4] = id;
        } else if (type.equals("Delete")) {
            sql = "delete from model where id = ?";
            obj = new Object[1];
        } else if (type.equals("Select")) {
            sql = "";
            obj = new Object[1];
        } else {
            model.addAttribute("centermsg", "操作异常");
            return "childPages/management";
        }
        try {
            jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e) {
            throw e;
        }
        return "childPages/management";
    }

    @PostMapping("/stockIn")
    public String stockIn(
            @RequestParam("supplycenter") String supplyCenter,
            @RequestParam("productmodel") String productModel,
            @RequestParam("supplystaff") String supplyStaff,
            @RequestParam("date") String date,
            @RequestParam("type") String type,
            @RequestParam("purchaseprice") String price,
            @RequestParam("quantity") String quantity,
            Model model
    ) {
        String sql;
        Object[] obj;
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, supplyStaff);
        if (check2.size() == 0) {
            model.addAttribute("centermsg", "⼈员不存在");
            return "childPages/management";
        }
        if (!check2.get(0).get("type").equals("1")) {
            model.addAttribute("centermsg", "⼈员的类型不是supply_staff");
            return "childPages/management";
        }
        String check3 = "select * from center where center.name = ?";
        List<Map<String, Object>> check4 = jdbc.queryForList(check3, supplyCenter);
        if (check4.size() == 0) {
            model.addAttribute("centermsg", "供应中⼼不存在");
            return "childPages/management";
        }
        String check5 = "select * from model where model.model = ?";
        List<Map<String, Object>> check6 = jdbc.queryForList(check5, productModel);
        if (check6.size() == 0) {
            model.addAttribute("centermsg", "产品型号不存在");
        }
        String check7 = "select c.name from staff join center c on staff.supply_center_id = c.id;";
        List<Map<String, Object>> check8 = jdbc.queryForList(check7, supplyStaff);
        if (!check8.get(0).get("name").equals(supplyCenter)) {
            model.addAttribute("centermsg", "供应中⼼与⼈员所在的供应中⼼对应不上");
        }
        String getid = "select id from model where model = ?";
        List<Map<String, Object>> getid1 = jdbc.queryForList(getid, productModel);
        int id = (int) getid1.get(0).get("id");
        sql = "update warehousing set quantity = quantity + ? where model_id = ?";
        obj = new Object[2];
        obj[0] = quantity;
        obj[1] = id;
        model.addAttribute("centermsg", "Failed");
        try {
            jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e) {
            throw e;
        }
        return "childPages/management";
    }

    @PostMapping("/placeorder")
    public String placeOrder(
            @RequestParam("contract_num") String contractNum,
            @RequestParam("enterprise") String enterprise,
            @RequestParam("product_model") String productModel,
            @RequestParam("quantity") String quantity,
            @RequestParam("contract_manager") String contractManager,
            @RequestParam("contract_date") String contractDate,
            @RequestParam("estimated_delivery_date") String estimatedDeliveryDate,
            @RequestParam("lodgement_date") String lodgementDate,
            @RequestParam("salesman_num") String salesmanNum,
            @RequestParam("contract_type") String contractType,
            Model model
    ) {
        String sql;
        Object obj;
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, contractManager);
        if (!check2.get(0).get("type").equals("3")) {
            model.addAttribute("centermsg", "该员工不是salesman");
            return "childPages/management";
        }
        String check3 = "select * from warehousing where supply_center = ? and product_model = ? and quantity >= ?;";
        List<Map<String, Object>> check4 = jdbc.queryForList(check3, productModel, quantity);
        if (check4.size() == 0) {
            model.addAttribute("centermsg", "库存不足");
            return "childPages/management";
        }
        try {
            //jdbc.update(sql, obj);
            model.addAttribute("centermsg", "Success");
        } catch (Exception e) {
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
        if (re.isEmpty()) return false;
        if (re.get(0).get("isUsed") != null && (int) re.get(0).get("isUsed") >= 1) {
            sql = "update rechapter set isUsed = case when isUsed is null then 1 else isUsed + 1 end where chapter = ?;";
            return false;
        } else {
            sql = "update rechapter set isUsed = 1,user_name = ?, used_date = ? where chapter = ?;";
            Object[] tmp = new Object[3];
            tmp[0] = name;
            tmp[1] = new Date();
            tmp[2] = rechapter;
            jdbc.update(sql, tmp);
            sql = "insert into users(user_name, passwd_md5) values (?,?);";
            Object[] m1 = new Object[2];
            m1[0] = name;
            m1[1] = encodeStr;
            jdbc.update(sql, m1);
            return true;
        }
    }

}

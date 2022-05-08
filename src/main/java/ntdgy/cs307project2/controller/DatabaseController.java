package ntdgy.cs307project2.controller;

import ntdgy.cs307project2.exception.InvalidDataException;
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
    ) throws InvalidDataException {
        StringBuilder sql = new StringBuilder("select * from center where");
        removeEmpty(map);
        if (map.isEmpty()) {
            throw new InvalidDataException("查询条件为空");
        }
        Object[] obj = new Object[map.size()];
        int i = 0;
        for (var entry : map.entrySet()) {
            if (i == 0) {
                sql.append(" ").append(entry.getKey()).append(" = ?");
            } else {
                sql.append(" and ").append(entry.getKey()).append(" = ?");
            }
            obj[i++] = entry.getValue();
        }
        return jdbc.queryForList(sql.toString(), obj);
    }

    @PostMapping("/center")
    @ResponseBody
    public Map<String, Object> Center(
//            @RequestParam("id") String id,
//            @RequestParam("name") String name,
//            @RequestParam("type") String type,
//            @RequestParam(value = "updateid", required = false) String updateId,
//            @RequestParam(value = "updatename", required = false) String updateName,
            @RequestBody Map<String, Object> map
    ) {
        Map<String, Object> response = new HashMap<>();
        String[] para = new String[]{"id", "name"};
        String[] update = new String[]{"updateid", "updatename"};
        Object[] obj;
        removeEmpty(map);
        Map<String, Object> res = wash(map, para);
        String type = (String) map.get("type");
        StringBuilder sql;
        if (type.equals("Insert")) {
            sql = new StringBuilder("insert into center(");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(") values(");
            sql.append("?,".repeat(Math.max(0, res.size() - 1)));
            sql.append("?)");
        } else if (type.equals("Update")) {
            var temp = wash(map, update);
            sql = new StringBuilder("update center set ");
            obj = new Object[res.size() + temp.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=?");
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(" where ");
            for (var entry : temp.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != obj.length - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else if (type.equals("Delete")) {
            sql = new StringBuilder("delete from center where ");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != res.size() - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else {
            response.put("result", "failed");
            return response;
        }
        try {
            jdbc.update(sql.toString(), obj);
        } catch (Exception e) {
            throw e;
        }
        response.put("result", "success");
        return response;
    }

    @PostMapping("/enterprise")
    public Map<String, Object> addEnterprise(
//            @RequestParam("id") int id,
//            @RequestParam("name") String name,
//            @RequestParam("country") String country,
//            @RequestParam("city") @Nullable String city,
//            @RequestParam("supplycenter") String supplyCenter,
//            @RequestParam("type") String type,
//            @RequestParam("industry") String industry,
//            Model model
            @RequestBody Map<String, Object> map
    ) {
        Map<String, Object> response = new HashMap<>();
        String[] para = new String[]{"id", "name", "country", "city", "supplycenter", "type", "industry"};
        String[] update = new String[]{"updateid", "updatename", "updatecountry", "updatecity", "updatesupplycenter", "updatetype", "updateindustry"};
        Object[] obj;
        removeEmpty(map);
        Map<String, Object> res = wash(map, para);
        String type = (String) map.get("type");
        StringBuilder sql;
        if (type.equals("Insert")) {
            sql = new StringBuilder("insert into enterprise(");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(") values(");
            sql.append("?,".repeat(Math.max(0, res.size() - 1)));
            sql.append("?)");
        } else if (type.equals("Update")) {
            var temp = wash(map, update);
            sql = new StringBuilder("update enterprise set ");
            obj = new Object[res.size() + temp.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=?");
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(" where ");
            for (var entry : temp.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != obj.length - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else if (type.equals("Delete")) {
            sql = new StringBuilder("delete from enterprise where ");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != res.size() - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else {
            response.put("result", "failed");
            return response;
        }
        try {
            jdbc.update(sql.toString(), obj);
        } catch (Exception e) {
            throw e;
        }
        response.put("result", "success");
        return response;
    }


    @PostMapping("/staff")
    public Map<String, Object> Enterprise(
//            @RequestParam("id") int id,
//            @RequestParam("name") String name,
//            @RequestParam("number") String number,
//            @RequestParam("gender") String q,
//            @RequestParam("age") String age,
//            @RequestParam("mobilenumber") String mobileNumber,
//            @RequestParam("supplycenterid") String supplyCenterId,
//            @RequestParam("type") String type,
//            Model model
            @RequestBody Map<String, Object> map
    ) {
        Map<String, Object> response = new HashMap<>();
        String[] para = new String[]{"id", "name", "number", "gender", "age", "mobilenumber", "supplycenterid", "type"};
        String[] update = new String[]{"updateid", "updatename", "updategender", "updateage", "updatemobilenumber", "updatemobilenumber", "updatesupplycenterid"};
        Object[] obj;
        removeEmpty(map);
        Map<String, Object> res = wash(map, para);
        String type = (String) map.get("type");
        StringBuilder sql;
        if (type.equals("Insert")) {
            sql = new StringBuilder("insert into enterprise(");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(") values(");
            sql.append("?,".repeat(Math.max(0, res.size() - 1)));
            sql.append("?)");
        } else if (type.equals("Update")) { //TODO
            var temp = wash(map, update);
            sql = new StringBuilder("update enterprise set ");
            obj = new Object[res.size() + temp.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=?");
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(" where ");
            for (var entry : temp.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != obj.length - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else if (type.equals("Delete")) {
            sql = new StringBuilder("delete from enterprise where ");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != res.size() - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else {
            response.put("result", "failed");
            return response;
        }
        try {
            jdbc.update(sql.toString(), obj);
        } catch (Exception e) {
            throw e;
        }
        response.put("result", "success");
        return response;
    }

    @PostMapping("/model")
    public Map<String, Object> model(
//            @RequestParam("id") int id,
//            @RequestParam("number") String number,
//            @RequestParam("model") String model1,
//            @RequestParam("name") String name,
//            @RequestParam("unitprice") String unitPrice,
//            @RequestParam("type") String type,
//            Model model
            @RequestBody Map<String, Object> map
    ) {
        Map<String, Object> response = new HashMap<>();
        String[] para = new String[]{"id", "number", "model", "name", "unitprice", "type"};
        String[] update = new String[]{"updateid", "updatenumber", "updatemodel", "updatename", "updateunitprice", "updatetype"};
        Object[] obj;
        removeEmpty(map);
        Map<String, Object> res = wash(map, para);
        String type = (String) map.get("type");
        StringBuilder sql;
        if (type.equals("Insert")) {
            sql = new StringBuilder("insert into enterprise(");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(") values(");
            sql.append("?,".repeat(Math.max(0, res.size() - 1)));
            sql.append("?)");
        } else if (type.equals("Update")) { //TODO
            var temp = wash(map, update);
            sql = new StringBuilder("update enterprise set ");
            obj = new Object[res.size() + temp.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=?");
                if (i != res.size() - 1) {
                    sql.append(',');
                }
                obj[i++] = entry.getValue();
            }
            sql.append(" where ");
            for (var entry : temp.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != obj.length - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else if (type.equals("Delete")) {
            sql = new StringBuilder("delete from enterprise where ");
            obj = new Object[res.size()];
            int i = 0;
            for (var entry : res.entrySet()) {
                sql.append(entry.getKey());
                sql.append("=? ");
                if (i != res.size() - 1) {
                    sql.append("and ");
                }
                obj[i++] = entry.getValue();
            }
        } else {
            response.put("result", "failed");
            return response;
        }
        try {
            jdbc.update(sql.toString(), obj);
        } catch (Exception e) {
            throw e;
        }
        response.put("result", "success");
        return response;
    }

    @PostMapping("/stockIn")
    public Map<String, Object> stockIn(
//            @RequestParam("supplycenter") String supplyCenter,
//            @RequestParam("productmodel") String productModel,
//            @RequestParam("supplystaff") String supplyStaff,
//            @RequestParam("date") String date,
//            @RequestParam("type") String type,
//            @RequestParam("purchaseprice") String price,
//            @RequestParam("quantity") String quantity,
//            Model model
            @RequestBody Map<String, Object> map
            //map的参数同上
    ) throws Exception {
        removeEmpty(map);
        Map<String, Object> res = new HashMap<>();
        String sql;
        Object[] obj;
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("supplystaff"));
        if (check2.size() == 0) {
            throw new InvalidDataException("供应商不存在");
        }
        if (!check2.get(0).get("type").equals("1")) {
            throw new InvalidDataException("⼈员的类型不是supply_staff");
        }
        String check3 = "select * from center where center.name = ?";
        List<Map<String, Object>> check4 = jdbc.queryForList(check3, map.get("supplycenter"));
        if (check4.size() == 0) {
            throw new InvalidDataException("供应中心不存在");
        }
        String check5 = "select * from model where model.model = ?";
        List<Map<String, Object>> check6 = jdbc.queryForList(check5, map.get("productmodel"));
        if (check6.size() == 0) {
            throw new InvalidDataException("产品型号不存在");
        }
        String check7 = "select c.name from staff join center c on staff.supply_center_id = c.id;";
        List<Map<String, Object>> check8 = jdbc.queryForList(check7, map.get("supplystaff"));
        if (!check8.get(0).get("name").equals(map.get("supplycenter"))) {
            throw new InvalidDataException("供应商不属于该供应中心");
        }
        String getid = "select id from model where model = ?";
        List<Map<String, Object>> getid1 = jdbc.queryForList(getid, map.get("productmodel"));
        int id = (int) getid1.get(0).get("id");
        sql = "update warehousing set quantity = quantity + ? where model_id = ?";
        obj = new Object[2];
        obj[0] = map.get("quantity");
        obj[1] = id;
        jdbc.update(sql, obj);
        res.put("result", "Success");
        return res;
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


    private void removeEmpty(Map<String, Object> map) {
        List<String> s = new LinkedList<>();
        for (var entry : map.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals("")) {
                s.add(entry.getKey());
            }
        }
        for (String i : s) {
            map.remove(i);
        }
    }


    private Map<String, Object> wash(Map<String, Object> map, String[] para) {
        Map<String, Object> res = new HashMap<>();
        for (String s : para) {
            if (map.containsKey(s)) {
                res.put(s, map.get(s));
            }
        }
        return res;
    }

}

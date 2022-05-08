package ntdgy.cs307project2.controller;

import ntdgy.cs307project2.exception.InvalidDataException;
import ntdgy.cs307project2.exception.InvalidOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;
import org.springframework.dao.DuplicateKeyException;

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
    ) throws Exception {
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
            throw new InvalidOperationException("type is not valid");
        }
        try {
            jdbc.update(sql.toString(), obj);
        } catch (Exception e) {
            throw new InvalidDataException("wssb", e);
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
//            @RequestParam("stafftype") String type,
//            Model model
            @RequestBody Map<String, Object> map
    ) {
        Map<String, Object> response = new HashMap<>();
        String[] para = new String[]{"id", "name", "number", "gender", "age", "mobilenumber", "supplycenterid", "stafftype"};
        String[] update = new String[]{"updateid", "updatename", "updategender", "updateage", "updatemobilenumber", "updatemobilenumber", "updatesupplycenterid", "updatestafftype"};
        Object[] obj;
        removeEmpty(map);
        Map<String, Object> res = wash(map, para);
        String type = (String) map.get("type");
        StringBuilder sql;
        if (type.equals("Insert")) {
            sql = new StringBuilder("insert into staff(");
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
            sql = new StringBuilder("update staff set ");
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
            sql = new StringBuilder("delete from staff where ");
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
            sql = new StringBuilder("insert into model(");
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
            sql = new StringBuilder("update model set ");
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
            sql = new StringBuilder("delete from model where ");
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
            throw new InvalidDataException("⼈员不存在");
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
        String check7 = "select staff.center from staff where staff.name = ?;";
        List<Map<String, Object>> check8 = jdbc.queryForList(check7, map.get("supplystaff"));
        if (!check8.get(0).get("name").equals(map.get("supplycenter"))) {
            throw new InvalidDataException("供应商不属于该供应中心");
        }
        sql = "update warehousing set quantity = quantity + ? where center_name = ?";
        obj = new Object[2];
        obj[0] = map.get("quantity");
        obj[1] = map.get("supplycenter");
        jdbc.update(sql, obj);
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/placeorder")
    public Map<String, Object> placeOrder(
//            @RequestParam("contractnum") String contractNum,
//            @RequestParam("enterprise") String enterprise,
//            @RequestParam("productmodel") String productModel,
//            @RequestParam("quantity") String quantity,
//            @RequestParam("contractmanager") String contractManager,
//            @RequestParam("contractdate") String contractDate,
//            @RequestParam("estimated_delivery_date") String estimatedDeliveryDate,
//            @RequestParam("lodgementdate") String lodgementDate,
//            @RequestParam("salesmannum") String salesmanNum,
//            @RequestParam("contracttype") String contractType,
//            Model model
            @RequestBody Map<String, Object> map
    ) throws Exception {
        removeEmpty(map);
        Map<String, Object> res = new HashMap<>();
        String[] sql;
        List<Object[]> objects;
        String check1 = "select * from staff where staff.number = ?";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("contractmanager"));
        if (!check2.get(0).get("type").equals("3")) {
            throw new InvalidDataException("该员工不是salesman");
        }
        String check3 = "select * from warehousing w join (select e.supply_center from contract join enterprise e on contract.enterprise = e.name where contract.number = ?) as cesc on w.center_name = cesc.supply_center and w.model_name = ? and w.quantity >= ?;";
        List<Map<String, Object>> check4 = jdbc.queryForList(check3, map.get("contractnum"), map.get("productmodel"), map.get("quantity"));
        if (check4.size() == 0) {
            throw new InvalidDataException("库存不足");
        }
        String check5 = "select * from contract where contract_num = ?";
        List<Map<String, Object>> check6 = jdbc.queryForList(check5, map.get("contractnum"));
        if (check6.size() != 0) {
            sql = new String[2];
            objects = new ArrayList<>();
            sql[0] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman)\n" +
                    "values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), map.get("quantity"), map.get("estimated_delivery_date"), map.get("lodgementdate"), map.get("salesmannum")});
            sql[1] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{map.get("quantity"), check4.get(0).get("center_name"), map.get("productmodel")});
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
        } else {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "insert into contract (number, enterprise, contract_date, estimated_delivery_date, contract_manager, contract_type) values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("enterprise"), map.get("contractdate"), map.get("estimated_delivery_date"), map.get("contractmanager"), map.get("contracttype")});
            sql[1] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), map.get("quantity"), map.get("estimated_delivery_date"), map.get("lodgementdate"), map.get("salesmannum")});
            sql[2] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{map.get("quantity"), check4.get(0).get("center_name"), map.get("productmodel")});
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
            jdbc.update(sql[2], objects.get(2));
        }
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/updateOrder")
    public Map<String, Object> updateOrder(
//            contract: The contract number
//            productmodel: The model of the product which is selled to the client enterprise
//            salesman: The salesman number
//            quantity: The number of the product which is selled to the client enterprise, this value maybe update
//            estimatedeliverydate: Estimated delivery date of the product, this vcalue may be update
//            lodgementdate: Actual delivery date of the product, this vcalue may be update
//            The salesman can only update the order correspond to himself, error should be taken when
//            illegal update appears
//            The "quantity" in stock should update according to the update of quantity in order
//            If the final value of quantity after update is 0, this order should be deleted from the contract
//             After updating, if there is no order in contract, do not delete contract.
            @RequestBody Map<String, Object> map
    ) throws InvalidDataException {
        removeEmpty(map);
        Map<String, Object> res = new HashMap<>();
        String[] sql;
        List<Object[]> objects;
        String check1 = "select * from contract_content where contract_number = ? and salesman = ? and product_model_name = ?";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("contract"), map.get("salesman"), map.get("productmodel"));
        if (check2.size() == 0) {
            throw new InvalidDataException("该合同不属于该销售员");
        }
        if ((Integer) map.get("quantity") == 0) {
            sql = new String[1];
            objects = new ArrayList<>();
            sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
            objects.add(new Object[]{map.get("contract"), map.get("productmodel"), map.get("salesman")});
            jdbc.update(sql[0], objects.get(0));
        } else {
            sql = new String[2];
            objects = new ArrayList<>();
            sql[0] = "update contract_content set quantity = ?, estimated_delivery_date = ?, lodgement_date = ? where contract_number = ? and product_model_name = ? and salesman = ?";
            objects.add(new Object[]{map.get("quantity"), map.get("estimated_delivery_date"), map.get("lodgement_date"), map.get("contract"), map.get("productmodel"), map.get("salesman")});
            sql[1] = "update warehousing set quantity = quantity - ? where model_name = ? and center_name = ?;";
            int quantity = (Integer) map.get("quantity") - (Integer) check2.get(0).get("quantity");
            objects.add(new Object[]{(Object) quantity, map.get("productmodel"), check2.get(0).get("center_name")});
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
        }
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/deleteOrder")
    public Map<String, Object> deleteOrder(
//            contract: The contract number
//            salesman: The salesman number
//            seq:
            @RequestBody Map<String, Object> map
    ) throws InvalidDataException {
        removeEmpty(map);
        Map<String, Object> res = new HashMap<>();
        String[] sql;
        List<Object[]> objects;
        String check1 = "select * from contract_content where contract_number = ? and salesman = ? order by estimated_delivery_date, product_model_name;";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("contract"), map.get("salesman"));
        if (check2.size() == 0) {
            throw new InvalidDataException("该合同不属于该销售员");
        }
        //String productmodel = check2.get((Integer) map.get("seq")).get("product_model_name").toString();
        sql = new String[2];
        objects = new ArrayList<>();
        sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
        objects.add(new Object[]{map.get("contract"), check2.get((Integer) map.get("seq")).get("product_model_name"), map.get("salesman")});
        sql[1] = "update warehousing set quantity = quantity + ? where model_name = ? and center_name = ?;";
        objects.add(new Object[]{check2.get((Integer) map.get("seq")).get("quantity"), check2.get((Integer) map.get("seq")).get("product_model_name"), check2.get((Integer) map.get("seq")).get("center_name")});
        jdbc.update(sql[0], objects.get(0));
        jdbc.update(sql[1], objects.get(1));
        res.put("result", "Success");
        return res;
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
                if (s.startsWith("update")) {
                    res.put(s.replace("update", ""), map.get(s));
                } else
                    res.put(s, map.get(s));
            }
        }
        return res;
    }

}

package ntdgy.cs307project2.controller;

import ntdgy.cs307project2.exception.InvalidDataException;
import ntdgy.cs307project2.exception.InvalidOperationException;
import ntdgy.cs307project2.exception.WrongDataException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

import java.util.*;

@EnableAsync
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
    public Map<String, Object> center(
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
            throw new WrongDataException("", e);
        }
        response.put("result", "success");
        return response;
    }

    @PostMapping("/enterprise")
    @ResponseBody
    public Map<String, Object> enterprise(
//            @RequestParam("id") int id,
//            @RequestParam("name") String name,
//            @RequestParam("country") String country,
//            @RequestParam("city") @Nullable String city,
//            @RequestParam("supplycenter") String supplyCenter,
//            @RequestParam("type") String type,
//            @RequestParam("industry") String industry,
//            Model model
            @RequestBody Map<String, Object> map
    ) throws WrongDataException {
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
            throw new WrongDataException("数据错误", e);
        }
        response.put("result", "success");
        return response;
    }


    @PostMapping("/staff")
    @ResponseBody
    public Map<String, Object> staff(
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
    ) throws WrongDataException {
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
            throw new WrongDataException("数据错误", e);
        }
        response.put("result", "success");
        return response;
    }

    @PostMapping("/model")
    @ResponseBody
    public Map<String, Object> model(
//            @RequestParam("id") int id,
//            @RequestParam("number") String number,
//            @RequestParam("model") String model1,
//            @RequestParam("name") String name,
//            @RequestParam("unitprice") String unitPrice,
//            @RequestParam("type") String type,
//            Model model
            @RequestBody Map<String, Object> map
    ) throws WrongDataException {
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
            throw new WrongDataException("数据错误", e);
        }
        response.put("result", "success");
        return response;
    }

    @PostMapping("/stockIn")
    @ResponseBody
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
        String check9 = "select * from warehousing where center_name = ? and model_name = ?;";
        List<Map<String, Object>> check10 = jdbc.queryForList(check9, map.get("supplycenter"), map.get("productmodel"));
        if (check10.size() != 0) {
            sql = "update warehousing set quantity = quantity + ? where center_name = ? and model_name = ?;";
            obj = new Object[3];
            obj[0] = map.get("quantity");
            obj[1] = map.get("supplycenter");
            obj[2] = map.get("productmodel");
            jdbc.update(sql, obj);
        }else{
            sql = "insert into warehousing(center_name,model_name,quantity) values(?,?,?)";
            obj = new Object[3];
            obj[0] = map.get("supplycenter");
            obj[1] = map.get("productmodel");
            obj[2] = map.get("quantity");
            jdbc.update(sql, obj);
        }
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/placeorder")
    @ResponseBody
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
        String check7 = "select count(*) from sold where model_name = ?";
        Integer check8 = jdbc.queryForObject(check7, Integer.class, map.get("productmodel"));
        if (check6.size() != 0) {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman)\n" +
                    "values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), map.get("quantity"), map.get("estimated_delivery_date"), map.get("lodgementdate"), map.get("salesmannum")});
            sql[1] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{map.get("quantity"), check4.get(0).get("center_name"), map.get("productmodel")});
            if (check8 != null && check8 == 0) {
                sql[2] = "insert into sold (model_name, quantity) values (?, ?)";
                objects.add(new Object[]{map.get("productmodel"), map.get("quantity")});
            } else {
                sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
                objects.add(new Object[]{map.get("quantity"), map.get("productmodel")});
            }
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
            jdbc.update(sql[2], objects.get(2));
        } else {
            sql = new String[4];
            objects = new ArrayList<>();
            sql[0] = "insert into contract (number, enterprise, contract_date, estimated_delivery_date, contract_manager, contract_type) values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("enterprise"), map.get("contractdate"), map.get("estimated_delivery_date"), map.get("contractmanager"), map.get("contracttype")});
            sql[1] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), map.get("quantity"), map.get("estimated_delivery_date"), map.get("lodgementdate"), map.get("salesmannum")});
            sql[2] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{map.get("quantity"), check4.get(0).get("center_name"), map.get("productmodel")});
            if (check8 != null && check8 == 0) {
                sql[3] = "insert into sold (model_name, quantity) values (?, ?)";
                objects.add(new Object[]{map.get("productmodel"), map.get("quantity")});
            } else {
                sql[3] = "update sold set quantity = quantity + ? where model_name = ?";
                objects.add(new Object[]{map.get("quantity"), map.get("productmodel")});
            }
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
            jdbc.update(sql[2], objects.get(2));
            jdbc.update(sql[3], objects.get(3));
        }
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/updateOrder")
    @ResponseBody
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
        int quantity = (Integer) map.get("quantity") - (Integer) check2.get(0).get("quantity");
        if (check2.size() == 0) {
            throw new InvalidDataException("该合同不属于该销售员");
        }
        if ((Integer) map.get("quantity") == 0) {
            sql = new String[2];
            objects = new ArrayList<>();
            sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
            objects.add(new Object[]{map.get("contract"), map.get("productmodel"), map.get("salesman")});
            sql[1] = "update sold set quantity = quantity - ? where model_name = ?";
            objects.add(new Object[]{quantity, map.get("productmodel")});
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
        } else {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "update contract_content set quantity = ?, estimated_delivery_date = ?, lodgement_date = ? where contract_number = ? and product_model_name = ? and salesman = ?";
            objects.add(new Object[]{map.get("quantity"), map.get("estimated_delivery_date"), map.get("lodgement_date"), map.get("contract"), map.get("productmodel"), map.get("salesman")});
            sql[1] = "update warehousing set quantity = quantity - ? where model_name = ? and center_name = ?;";
            objects.add(new Object[]{(Object) quantity, map.get("productmodel"), check2.get(0).get("center_name")});
            sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
            objects.add(new Object[]{(Object) quantity, map.get("productmodel")});
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
            jdbc.update(sql[2], objects.get(2));
        }
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/deleteOrder")
    @ResponseBody
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
        String check3 = "select count(*) from sold where model_name = ?";
        Integer check4 = jdbc.queryForObject(check3, Integer.class, map.get("productmodel"));
        if (check2.size() == 0) {
            throw new InvalidDataException("该合同不属于该销售员");
        }
        sql = new String[3];
        objects = new ArrayList<>();
        sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
        objects.add(new Object[]{map.get("contract"), check2.get((Integer) map.get("seq")).get("product_model_name"), map.get("salesman")});
        sql[1] = "update warehousing set quantity = quantity + ? where model_name = ? and center_name = ?;";
        objects.add(new Object[]{check2.get((Integer) map.get("seq")).get("quantity"), check2.get((Integer) map.get("seq")).get("product_model_name"), check2.get((Integer) map.get("seq")).get("center_name")});
        if(check4!=null && check4 == check2.get((Integer) map.get("seq")).get("quantity")){
            sql[2] = "delete from sold where model_name = ?";
            objects.add(new Object[]{check2.get((Integer) map.get("seq")).get("product_model_name")});
        }else{
            sql[2] = "update sold set quantity = quantity - ? where model_name = ?";
            objects.add(new Object[]{check2.get((Integer) map.get("seq")).get("quantity"), check2.get((Integer) map.get("seq")).get("product_model_name")});
        }
        jdbc.update(sql[0], objects.get(0));
        jdbc.update(sql[1], objects.get(1));
        jdbc.update(sql[2], objects.get(2));
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/getAllStaffCount")
    @ResponseBody
    public Map<String, Object> getAllStaffCount() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select count(*) from staff where stafftype = 0;";
        res.put("director", jdbc.queryForObject(sql, Integer.class));
        sql = "select count(*) from staff where stafftype = 1;";
        res.put("supply", jdbc.queryForObject(sql, Integer.class));
        sql = "select count(*) from staff where stafftype = 2;";
        res.put("contracts", jdbc.queryForObject(sql, Integer.class));
        sql = "select count(*) from staff where stafftype = 3;";
        res.put("salesman", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getContractCount")
    @ResponseBody
    public Map<String, Object> getContractCount() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select count(*) from contract;";
        res.put("contract", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getOrderCount")
    @ResponseBody
    public Map<String, Object> getOrderCount() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select count(*) from contract_content;";
        res.put("order", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getNeverSoldProductCount")
    @ResponseBody
    public Map<String, Object> getNeverSoldProductCount() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select count(*) from model where name not in (select product_model_name from contract_content);";
        res.put("result", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getFavoriteProductModel")
    @ResponseBody
    public Map<String, Object> getFavoriteProductModel() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select model_name,quantity from sold s where (select quantity q from sold order by quantity limit 1) = s.quantity;";
        List<Map<String, Object>> check = jdbc.queryForList(sql);
        res.put("result", check);
        return res;
    }


    @PostMapping("/getAvgStockByCenter")
    @ResponseBody
    public Map<String, Object> getAvgStockByCenter() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select center_name,round(avg(quantity),1) from warehousing group by center_name order by center_name;";
        List<Map<String, Object>> check = jdbc.queryForList(sql);
        res.put("result", check);
        return res;
    }

//    getProductByNumber:
    @PostMapping("/getProductByNumber")
    @ResponseBody
    public Map<String, Object> getProductByNumber(@RequestBody Map<String, Object> map) {
        Map<String, Object> res = new HashMap<>();
        String sql = "select center_name,model_name,m.model,quantity from warehousing join model m on warehousing.model_name = m.name where model_name = ?;";
        List<Map<String, Object>> check = jdbc.queryForList(sql, map.get("model_name"));
        res.put("result", check);
        return res;
    }

    //13) getContractInfo
    @PostMapping("/getContractInfo")
    @ResponseBody
    public Map<String, Object> getContractInfo(@RequestBody Map<String, Object> map) {
        String contract_number = (String) map.get("contract_number");
        Map<String, Object> res = new HashMap<>();
        String sql = "select * from contract where contract_number = ?;";
        List<Map<String, Object>> check = jdbc.queryForList(sql, contract_number);
        String sql2 = "select * from contract_content where contract_number = ?;";
        List<Map<String, Object>> check2 = jdbc.queryForList(sql2, contract_number);
        res.put("result", check);
        res.put("result2", check2);
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
            //sql = "update rechapter set isUsed = case when isUsed is null then 1 else isUsed + 1 end where chapter = ?;";
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

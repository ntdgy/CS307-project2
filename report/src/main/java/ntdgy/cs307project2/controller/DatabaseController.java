package ntdgy.cs307project2.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ntdgy.cs307project2.dto.*;
import ntdgy.cs307project2.exception.InvalidDataException;
import ntdgy.cs307project2.exception.InvalidOperationException;
import ntdgy.cs307project2.exception.WrongDataException;
import ntdgy.cs307project2.service.LoginService;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Api
@EnableAsync
@Controller
@RequestMapping("/api/database")
public class DatabaseController {


    final JdbcTemplate jdbc;

    Map<String, Object> staffCache;

    public void setStaffHit(boolean staffHit) {
        this.staffHit = staffHit;
    }

    boolean staffHit = false;

    public DatabaseController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostMapping("/select/center")
    @ResponseBody
    public List<Map<String, Object>> selectCenter(
//            @RequestBody Map<String, Object> map
            @RequestBody Center center
    ) throws InvalidDataException {
        StringBuilder sql = new StringBuilder("select * from center where");
        Map<String, Object> map =  new HashMap<>();
        map.put("id", center.getId());
        map.put("name", center.getName());
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

    @PostMapping("/select/contract")
    @ResponseBody
    public List<Map<String, Object>> selectContract(
//            @RequestBody Map<String, Object> map
            @RequestBody Contract contract
            ) throws InvalidDataException {
        String[] para = new String[]{"id", "number", "enterprise", "contract_date", "contract_manager", "contract_type"};
        StringBuilder sql = new StringBuilder("select * from contract where");
        Map<String, Object> map =  new HashMap<>();
        map.put("id", contract.getId());
        map.put("number", contract.getNumber());
        map.put("enterprise", contract.getEnterprise());
        map.put("contract_date", contract.getContract_date());
        map.put("contract_manager", contract.getContract_manager());
        map.put("contract_type", contract.getContract_type());
        removeEmpty(map);
//        map = wash(map, para);
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

    @PostMapping("/select/enterprise")
    @ResponseBody
    public List<Map<String, Object>> selectEnterprise(
//            @RequestBody Map<String, Object> map
            @RequestBody Enterprise enterprise
            ) throws InvalidDataException {
        StringBuilder sql = new StringBuilder("select * from enterprise where");
        Map<String, Object> map =  new HashMap<>();
        map.put("id", enterprise.getId());
        map.put("name", enterprise.getName());
        map.put("supply_center", enterprise.getSupply_center());
        map.put("country", enterprise.getCountry());
        map.put("city", enterprise.getCity());
        map.put("industry", enterprise.getIndustry());
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

    @PostMapping("/select/model")
    @ResponseBody
    public List<Map<String, Object>> selectModel(
//            @RequestBody Map<String, Object> map
            @RequestBody Model model
    ) throws InvalidDataException {
        StringBuilder sql = new StringBuilder("select * from model where");
        Map<String, Object> map =  new HashMap<>();
        map.put("id", model.getId());
        map.put("name", model.getName());
        map.put("number", model.getNumber());
        map.put("model", model.getModel());
        map.put("unit_price", model.getUnit_price());
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

    @PostMapping("/select/staff")
    @ResponseBody
    public List<Map<String, Object>> selectStaff(
//            @RequestBody Map<String, Object> map
            @RequestBody Staff staff
    ) throws InvalidDataException {
        StringBuilder sql = new StringBuilder("select * from staff where");
        Map<String, Object> map =  new HashMap<>();
        map.put("id", staff.getId());
        map.put("name", staff.getName());
        map.put("number", staff.getNumber());
        map.put("gender", staff.getGender());
        map.put("age", staff.getAge());
        map.put("supply_center", staff.getSupply_center());
        map.put("mobile_number", staff.getMobile_number());
        map.put("staffType", staff.getStaffType());
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
        var result = jdbc.queryForList(sql.toString(), obj);
        for (var list : result) {
            switch ((Integer) list.get("stafftype")) {
                case 0:
                    list.replace("stafftype", "Director");
                    break;
                case 1:
                    list.replace("stafftype", "Supply Staff");
                    break;
                case 2:
                    list.replace("stafftype", "Contracts Manager");
                    break;
                case 3:
                    list.replace("stafftype", "Salesman");
            }
        }
        return result;
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
        String[] para = new String[]{"id", "name", "country", "city", "supplycenter", "industry", "supply_center"};
        String[] update = new String[]{"updateid", "updatename", "updatecountry", "updatecity", "updatesupplycenter", "updateindustry", "updatesupply_center"};
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
        staffHit = false;
        Map<String, Object> response = new HashMap<>();
        String[] para = new String[]{"id", "name", "number", "gender", "age", "supply_center",
                "mobile_number", "stafftype"};
        String[] update = new String[]{"updateid", "updatename", "updategender", "updateage",
                "updatemobilenumber", "updatesupply_center", "updatemobile_number", "updatestafftype"};
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
        String[] para = new String[]{"id", "number", "model", "name", "unit_price"};
        String[] update = new String[]{"updateid", "updatenumber", "updatemodel", "updatename", "updateunit_price"};
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
        } else if (type.equals("Update")) {
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
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("supplystaff").toString());
        if (check2.size() == 0) {
            throw new InvalidDataException("⼈员不存在");
        }
        if (!check2.get(0).get("stafftype").equals(1)) {
            throw new InvalidDataException("⼈员的类型不是supply_staff");
        }
        if (!check2.get(0).get("supply_center").equals(map.get("supplycenter"))) {
            throw new InvalidDataException("供应商不属于该供应中心");
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
        String check9 = "select * from warehousing where center_name = ? and model_name = ?;";
        List<Map<String, Object>> check10 = jdbc.queryForList(check9, map.get("supplycenter"), map.get("productmodel"));
        if (check10.size() != 0) {
            sql = "update warehousing set quantity = quantity + ? where center_name = ? and model_name = ?;";
            obj = new Object[3];
            obj[0] = Integer.parseInt(map.get("quantity").toString());
            obj[1] = map.get("supplycenter");
            obj[2] = map.get("productmodel");
            jdbc.update(sql, obj);
        } else {
            sql = "insert into warehousing(center_name,model_name,quantity) values(?,?,?)";
            obj = new Object[3];
            obj[0] = map.get("supplycenter");
            obj[1] = map.get("productmodel");
            obj[2] = Integer.parseInt(map.get("quantity").toString());
            jdbc.update(sql, obj);
        }
        sql = "insert into stockin(id,supply_center,product_model,supply_staff,date,purchase_price,quantity) values(?,?,?,?,?,?,?)";
        obj = new Object[7];
        obj[0] = Integer.parseInt(map.get("id").toString());
        obj[1] = map.get("supplycenter");
        obj[2] = map.get("productmodel");
        obj[3] = map.get("supplystaff");
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", map.get("date").toString())) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            Date date = jsFormat.parseDateTime(map.get("date").toString()).toDate();
            obj[4] = date;
        } else {
            Date date = java.sql.Date.valueOf(map.get("date").toString().replace('/', '-'));
            obj[4] = date;
        }
        obj[5] = Integer.parseInt(map.get("purchaseprice").toString());
        obj[6] = Integer.parseInt(map.get("quantity").toString());
        jdbc.update(sql, obj);
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
//            @RequestParam("estimateddeliverydate") String estimatedDeliveryDate,
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
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("salesmannum"));
        if (check2.isEmpty()) {
            throw new InvalidDataException("员工不存在");
        }
        if (!(check2.get(0).get("stafftype").toString().equals("3"))) {
            throw new InvalidDataException("该员工不是salesman");
        }
        String check3 = "select *\n" +
                "from warehousing w\n" +
                "         join (select e.supply_center\n" +
                "               from  enterprise e\n" +
                "               where e.name = ?) as cesc\n" +
                "              on w.center_name = cesc.supply_center and w.model_name = ? and w.quantity >= ?;";
        List<Map<String, Object>> check4 = jdbc.queryForList(check3, map.get("enterprise"),
                map.get("productmodel"), Integer.parseInt(map.get("quantity").toString()));
        if (check4.size() == 0) {
            throw new InvalidDataException("库存不足");
        }
        String check5 = "select * from contract where number = ?";
        List<Map<String, Object>> check6 = jdbc.queryForList(check5, map.get("contractnum"));
        String check7 = "select count(*) from sold where model_name = ?";
        Integer check8 = jdbc.queryForObject(check7, Integer.class, map.get("productmodel"));
        Date estimated_delivery_date, lodgement_date, contractdate;
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", map.get("estimateddeliverydate").toString())) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            estimated_delivery_date = jsFormat.parseDateTime(map.get("estimateddeliverydate").toString()).toDate();
        } else {
            estimated_delivery_date = java.sql.Date.valueOf(map.get("estimateddeliverydate").toString().replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", map.get("lodgementdate").toString())) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            lodgement_date = jsFormat.parseDateTime(map.get("lodgementdate").toString()).toDate();
        } else {
            lodgement_date = java.sql.Date.valueOf(map.get("lodgementdate").toString().replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", map.get("contractdate").toString())) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            contractdate = jsFormat.parseDateTime(map.get("contractdate").toString()).toDate();
        } else {
            contractdate = java.sql.Date.valueOf(map.get("contractdate").toString().replace('/', '-'));
        }
        if (check6.size() != 0) {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "insert into contract_content (contract_number, product_model_name, quantity, estimated_delivery_date, lodgement_date, salesman)\n" +
                    "values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), Integer.parseInt(map.get("quantity").toString()), estimated_delivery_date, lodgement_date, map.get("salesmannum")});
            sql[1] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{Integer.parseInt(map.get("quantity").toString()), check4.get(0).get("center_name"), map.get("productmodel")});
            if (check8 != null && check8 == 0) {
                sql[2] = "insert into sold (model_name, quantity) values (?, ?)";
                objects.add(new Object[]{map.get("productmodel"), Integer.parseInt(map.get("quantity").toString())});
            } else {
                sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
                objects.add(new Object[]{Integer.parseInt(map.get("quantity").toString()), map.get("productmodel")});
            }
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
            jdbc.update(sql[2], objects.get(2));
        } else {
            sql = new String[4];
            objects = new ArrayList<>();
            sql[0] = "insert into contract (number, enterprise, contract_date, contract_manager, contract_type) " +
                    "values (?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("enterprise"), contractdate, map.get("contractmanager"), map.get("contracttype")});
            sql[1] = "insert into contract_content (contract_number, product_model_name, quantity, " +
                    "estimated_delivery_date, lodgement_date, salesman) values (?, ?, ?, ?, ?, ?)";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), Integer.parseInt(map.get("quantity").toString()), estimated_delivery_date, lodgement_date, map.get("salesmannum")});
            sql[2] = "update warehousing set quantity = quantity - ? where center_name = ? and model_name = ?";
            objects.add(new Object[]{Integer.parseInt(map.get("quantity").toString()), check4.get(0).get("center_name"), map.get("productmodel")});
            if (check8 != null && check8 == 0) {
                sql[3] = "insert into sold (model_name, quantity) values (?, ?)";
                objects.add(new Object[]{map.get("productmodel"), Integer.parseInt(map.get("quantity").toString())});
            } else {
                sql[3] = "update sold set quantity = quantity + ? where model_name = ?";
                objects.add(new Object[]{Integer.parseInt(map.get("quantity").toString()), map.get("productmodel")});
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
//            contractnum: The contract number
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
        String check1 = "select * from contract_content where contract_number = ? " +
                "and salesman = ? and product_model_name = ?";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("contractnum"), map.get("salesman"), map.get("productmodel"));
        if (check2.size() == 0) {
            throw new InvalidDataException("该合同不属于该销售员");
        }
        String check3 = "select supply_center from contract join enterprise e " +
                "on contract.enterprise = e.name where number = ?;";
        String supplyCenter = jdbc.queryForObject(check3, String.class, map.get("contractnum"));
        int quantity = Integer.parseInt(map.get("quantity").toString()) - Integer.parseInt(check2.get(0).get("quantity").toString());
        Date estimated_delivery_date, lodgement_date;
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", map.get("estimateddeliverydate").toString())) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            estimated_delivery_date = jsFormat.parseDateTime(map.get("estimateddeliverydate").toString()).toDate();
        } else {
            estimated_delivery_date = java.sql.Date.valueOf(map.get("estimateddeliverydate").toString().replace('/', '-'));
        }
        if (Pattern.matches("^\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+Z$", map.get("lodgementdate").toString())) {
            DateTimeFormatter jsFormat = ISODateTimeFormat.dateTime();
            lodgement_date = jsFormat.parseDateTime(map.get("lodgementdate").toString()).toDate();
        } else {
            lodgement_date = java.sql.Date.valueOf(map.get("lodgementdate").toString().replace('/', '-'));
        }
        if (map.get("quantity").toString().equals("0")) {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
            objects.add(new Object[]{map.get("contractnum"), map.get("productmodel"), map.get("salesman")});
            sql[1] = "update sold set quantity = quantity + ? where model_name = ?";
            objects.add(new Object[]{quantity, map.get("productmodel")});
            sql[2] = "update warehousing set quantity = quantity - ? where model_name = ? and center_name = ?;";
            objects.add(new Object[]{quantity, map.get("productmodel"), supplyCenter});
            jdbc.update(sql[0], objects.get(0));
            jdbc.update(sql[1], objects.get(1));
            jdbc.update(sql[2], objects.get(2));
        } else {
            sql = new String[3];
            objects = new ArrayList<>();
            sql[0] = "update contract_content set quantity = ?, estimated_delivery_date = ?, " +
                    "lodgement_date = ? where contract_number = ? and product_model_name = ? " +
                    "and salesman = ?";
            objects.add(new Object[]{Integer.parseInt(map.get("quantity").toString()),
                    estimated_delivery_date, lodgement_date, map.get("contractnum"),
                    map.get("productmodel"), map.get("salesman")});
            sql[1] = "update warehousing set quantity = quantity - ? where model_name = ? " +
                    "and center_name = ?;";
            objects.add(new Object[]{quantity, map.get("productmodel"), supplyCenter});
            sql[2] = "update sold set quantity = quantity + ? where model_name = ?";
            objects.add(new Object[]{quantity, map.get("productmodel")});
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
        String check1 = "select * from contract_content where contract_number = ? and salesman = ? " +
                "order by estimated_delivery_date, product_model_name;";
        List<Map<String, Object>> check2 = jdbc.queryForList(check1, map.get("contract"), map.get("salesman"));
        if (check2.size() < Integer.parseInt(map.get("seq").toString())) {
            throw new InvalidDataException("该合同不属于该销售员");
        }
        var content = check2.get(Integer.parseInt(map.get("seq").toString()) - 1);
        String check3 = "select count(*) from sold where model_name = ?";
        Integer check4 = jdbc.queryForObject(check3, Integer.class, content.get("product_model_name"));
        String check5 = "select supply_center from contract join enterprise e on contract.enterprise = e.name\n" +
                "    where number = ?;";
        String check6 = jdbc.queryForObject(check5, String.class, map.get("contract"));
        sql = new String[3];
        objects = new ArrayList<>();
        sql[0] = "delete from contract_content where contract_number = ? and product_model_name = ? and salesman = ?";
        objects.add(new Object[]{map.get("contract"), content.get("product_model_name"), map.get("salesman")});
        sql[1] = "update warehousing set quantity = quantity + ? where model_name = ? and center_name = ?;";
        objects.add(new Object[]{Integer.parseInt(content.get("quantity").toString()), content.get("product_model_name"), check6});
        if (check4 != null && check4 == Integer.parseInt(content.get("quantity").toString())) {
            sql[2] = "delete from sold where model_name = ?";
            objects.add(new Object[]{content.get("product_model_name")});
        } else {
            sql[2] = "update sold set quantity = quantity - ? where model_name = ?";
            objects.add(new Object[]{Integer.parseInt(content.get("quantity").toString()), content.get("product_model_name")});
        }
        jdbc.update(sql[0], objects.get(0));
        jdbc.update(sql[1], objects.get(1));
        jdbc.update(sql[2], objects.get(2));
        res.put("result", "Success");
        return res;
    }

    @PostMapping("/getAllStaffCount")
    @GetMapping("/getAllStaffCount")
    @ResponseBody
    public Map<String, Object> getAllStaffCount() {
        if (staffHit) {
            return staffCache;
        }
        Map<String, Object> res = new HashMap<>();
        String sql = "select count(*) from staff where stafftype = 0;";
        res.put("director", jdbc.queryForObject(sql, Integer.class));
        sql = "select count(*) from staff where stafftype = 1;";
        res.put("supply", jdbc.queryForObject(sql, Integer.class));
        sql = "select count(*) from staff where stafftype = 2;";
        res.put("contracts", jdbc.queryForObject(sql, Integer.class));
        sql = "select count(*) from staff where stafftype = 3;";
        res.put("salesman", jdbc.queryForObject(sql, Integer.class));
        staffCache = res;
        staffHit = true;
        return res;
    }

    @PostMapping("/getContractCount")
    @ResponseBody
    public Map<String, Object> getContractCount() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select * from select_contract_count;";
        res.put("contract", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getOrderCount")
    @ResponseBody
    public Map<String, Object> getOrderCount() {
        Map<String, Object> res = new HashMap<>();
        String sql = "select * from select_order_count;";
        res.put("order", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getNeverSoldProductCount")
    @ResponseBody
    public Map<String, Object> getNeverSoldProductCount() {
        Map<String, Object> res = new HashMap<>();
//        String sql = "select count(distinct model_name) from warehousing where quantity!=0 " +
//                "and model_name not in(select distinct product_model_name from contract_content);";
        String sql = "select * from select_never_sold;";
        res.put("result", jdbc.queryForObject(sql, Integer.class));
        return res;
    }

    @PostMapping("/getFavoriteProductModel")
    @ResponseBody
    public Map<String, Object> getFavoriteProductModel() {
        Map<String, Object> res = new HashMap<>();
//        String sql = "select model_name,quantity from sold s where (select quantity q from sold order by quantity desc limit 1) = s.quantity;";
        String sql = "select * from select_favourite;";
        List<Map<String, Object>> check = jdbc.queryForList(sql);
        res.put("result", check);
        return res;
    }


    @PostMapping("/getAvgStockByCenter")
    @ResponseBody
    public Map<String, Object> getAvgStockByCenter() {
        Map<String, Object> res = new HashMap<>();
//        String sql = "select center_name,round(avg(quantity),1) from warehousing group by center_name order by center_name;";
        String sql = "select * from select_avg;";
        List<Map<String, Object>> check = jdbc.queryForList(sql);
        res.put("result", check);
        return res;
    }

    //    getProductByNumber:
    @PostMapping("/getProductByNumber")
    @ResponseBody
    public Map<String, Object> getProductByNumber(@RequestBody Map<String, Object> map) {
        Map<String, Object> res = new HashMap<>();
        String sql = "select center_name,model_name,m.model,quantity,unit_price from warehousing " +
                "join model m on warehousing.model_name = m.model where number = ?;";
        List<Map<String, Object>> check = jdbc.queryForList(sql, map.get("model_number"));
        res.put("result", check);
        return res;
    }

    //13) getContractInfo
    @PostMapping("/getContractInfo")
    @ResponseBody
    public Map<String, Object> getContractInfo(@RequestBody Map<String, Object> map) {
        String contract_number = (String) map.get("contractnumber");
        Map<String, Object> res = new HashMap<>();
        String sql = "select c.number,c.enterprise,e.supply_center,s.name from contract c " +
                "join staff s on c.contract_manager = s.number " +
                "join enterprise e on c.enterprise = e.name " +
                "where c.number = ?;";
        List<Map<String, Object>> check = jdbc.queryForList(sql, contract_number);
        String sql2 = "select c.product_model_name,c.quantity,c.estimated_delivery_date,c.lodgement_date,s.name,m.unit_price " +
                "from contract_content c " +
                "join staff s on c.salesman = s.number " +
                "join model m on c.product_model_name = m.model " +
                "where contract_number = ?;";
        List<Map<String, Object>> check2 = jdbc.queryForList(sql2, contract_number);
        res.put("result", check);
        res.put("result2", check2);
        return res;
    }


    @PostMapping("drop")
    @ResponseBody
    public String dropDarabse() {
        staffHit = false;
        String sql = "drop table if exists center cascade;\n" +
                "drop table if exists enterprise cascade;\n" +
                "drop table if exists staff cascade;\n" +
                "drop table if exists model cascade;\n" +
                "drop table if exists sold cascade;\n" +
                "drop table if exists warehousing cascade;\n" +
                "drop table if exists stockin cascade;\n" +
                "drop table if exists contract cascade;\n" +
                "drop table if exists contract_content cascade;";
        jdbc.update(sql);
        return "Success";
    }

    @PostMapping("create")
    @ResponseBody
    public String createDarabse() {
        String sql = "create table center\n" +
                "(\n" +
                "    id   serial primary key,\n" +
                "    name varchar(60) not null unique\n" +
                ");\n" +
                "create index center_name on center(name);\n" +
                "\n" +
                "create table enterprise\n" +
                "(\n" +
                "    id               serial primary key,\n" +
                "    name             varchar not null unique ,\n" +
                "    supply_center varchar not null references center(name) on update cascade on delete cascade,         -- references supply_center(id),-- not null ,\n" +
                "    country          varchar(30) not null,\n" +
                "    city             varchar(20),\n" +
                "    industry         varchar(40)\n" +
                ");\n" +
                "create index enterprise_name on enterprise(name);\n" +
                "\n" +
                "create table staff\n" +
                "(\n" +
                "    id            serial primary key,\n" +
                "    name          varchar(30) not null ,\n" +
                "    number        varchar(8) unique not null ,\n" +
                "    gender        varchar(6),\n" +
                "    age           int,\n" +
                "    mobile_number varchar(11) unique not null,\n" +
                "    supply_center varchar not null references center(name) on update cascade on delete cascade,\n" +
                "    stafftype          int          -- 0 is director, 1 is supply =staff, 2 is contracts manager, 3 is salesman\n" +
                ");\n" +
                "create index staff_name on staff(number);\n" +
                "\n" +
                "create table model\n" +
                "(\n" +
                "    id         serial primary key,\n" +
                "    number     varchar(20),\n" +
                "    model      varchar(60) not null unique,\n" +
                "    name       varchar not null ,\n" +
                "    unit_price int   not null\n" +
                ");\n" +
                "create index model_model on model(model);\n" +
                "\n" +
                "create table warehousing(\n" +
                "    id serial primary key,\n" +
                "    model_name varchar not null references model(model) on update cascade on delete cascade,\n" +
                "    quantity int not null,\n" +
                "    center_name varchar not null references center(name)  on update cascade on delete cascade\n" +
                ");\n" +
                "\n" +
                "create table stockin\n" +
                "(\n" +
                "    id             serial primary key,\n" +
                "    supply_center  varchar not null references center(name) on update cascade on delete cascade,\n" +
                "    product_model  varchar not null references model(model) on update cascade on delete cascade,\n" +
                "    supply_staff   varchar(8),\n" +
                "    date           date,\n" +
                "    purchase_price int,\n" +
                "    quantity       int\n" +
                ");\n" +
                "\n" +
                "create table sold\n" +
                "(\n" +
                "    id             serial primary key,\n" +
                "    model_name     varchar not null references model(model) on update cascade on delete cascade,\n" +
                "    quantity       int not null\n" +
                ");\n" +
                "\n" +
                "create table contract\n" +
                "(\n" +
                "    id               serial primary key,\n" +
                "    number           varchar(10) not null unique ,\n" +
                "    enterprise    varchar not null references enterprise(name) on update cascade on delete cascade,\n" +
                "    contract_date    date not null,\n" +
                "    contract_manager varchar(8),\n" +
                "    contract_type varchar(20)\n" +
                ");\n" +
                "create index contract_number on contract(number);\n" +
                "\n" +
                "create table contract_content\n" +
                "(\n" +
                "    id                      serial primary key,\n" +
                "    contract_number            varchar(10) not null,\n" +
                //references contract(number) on update cascade on delete cascade, -- references contract (id) not null ,\n" +
                "    product_model_name       varchar not null references model(model) on update cascade on delete cascade, -- references product_model (id) not null,\n" +
                "    quantity                int, -- not null,\n" +
                "    estimated_delivery_date date,\n" +
                "    lodgement_date          date,\n" +
                "    -- price int not null,\n" +
                "    salesman   varchar not null references staff(number) on update cascade on delete cascade -- references salesman (id) not null\n" +
                ");" +
                "create or replace view select_contract_count as\n" +
                "    select count(*) from contract;\n" +
                "create or replace view select_order_count as\n" +
                "select count(*) from contract_content;\n" +
                "create or replace view select_never_sold as\n" +
                "    select count(distinct model_name) from warehousing where quantity!=0\n" +
                "    and model_name not in(select distinct product_model_name from contract_content);\n" +
                "\n" +
                "create or replace view select_favourite as\n" +
                "    select model_name,quantity from sold s where (select quantity q from sold order by quantity desc limit 1) = s.quantity;\n" +
                "\n" +
                "create or replace view select_avg as\n" +
                "    select center_name,round(avg(quantity),1) from warehousing group by center_name order by center_name;";
        jdbc.update(sql);
        return "Success";
    }


    @PostMapping("clear")
    @ResponseBody
    public String clearDarabse() {
        staffHit = false;
        String sql = "truncate center cascade;\n" +
                "truncate contract cascade ;\n" +
                "truncate contract_content cascade ;\n" +
                "truncate enterprise cascade ;\n" +
                "truncate model cascade ;\n" +
                "truncate sold cascade ;\n" +
                "truncate staff cascade ;\n" +
                "truncate stockin cascade ;\n" +
                "truncate warehousing cascade ;";
        jdbc.update(sql);
        this.staffHit = false;
        return "Success";
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
        int level = (int) re.get(0).get("level");
        LoginService.login(name, level);
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
            tmp[1] = new java.util.Date();
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
            if (entry.getValue() == null || entry.getValue().equals("") || entry.getValue().equals(0)) {
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

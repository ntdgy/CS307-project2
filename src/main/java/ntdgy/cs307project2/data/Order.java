package ntdgy.cs307project2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private int id;
    private String supplyCenter;
    private String productModel;
    private String supplyStaff;
    private Date date;
    private int purchasePrice;
    private int quantity;

}

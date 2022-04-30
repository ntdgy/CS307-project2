package ntdgy.cs307project2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    private int id;
    private String number;
    private String model;
    private String name;
    private double unit_price;

}

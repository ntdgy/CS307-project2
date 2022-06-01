package ntdgy.cs307project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    private int id;
    private String name;
    private String number;
    private String model;
    private int unit_price;

}

package ntdgy.cs307project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    private int id;
    private String name;
    private int age;
    private String gender;
    private int number;
    private String supply_center;
    private int mobile_number;
    private String type;

}

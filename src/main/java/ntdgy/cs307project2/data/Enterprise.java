package ntdgy.cs307project2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {

    private int id;
    private String name;
    private String country;
    private String city;
    private String supply_center;
    private String industry;

}

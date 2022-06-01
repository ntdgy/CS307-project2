package ntdgy.cs307project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

    private int id;
    private String number;
    private String enterprise;
    private Date contract_date;
    private String contract_manager;
    private String contract_type;

}

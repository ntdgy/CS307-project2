package ntdgy.cs307project2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractContent {

    private int id;
    private int contractID;
    private int modelID;
    private int quantity;
    private Date estimatedDate;
    private Date lodgementDate;
    private int salesmanID;

}

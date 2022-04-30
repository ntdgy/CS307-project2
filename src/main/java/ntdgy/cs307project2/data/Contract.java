package ntdgy.cs307project2.data;

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
    private int enterpriseID;
    private Date date;
    private String contactManager;

}

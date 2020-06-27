package application.model.viewmodel.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVM {
    private int id;
    private String userName;
    private String customerName;
    private String phoneNumber;
    private String address;
    private String email;
    private String createdDate;
    private String price;
}

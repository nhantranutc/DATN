package application.model.viewmodel.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicesVM {
    private int id;
    private String serviceName;
    private String description;
    private String content;
    private String image;
    private String price;
    private int amount;
    private int cartId;
    private String pay;
}

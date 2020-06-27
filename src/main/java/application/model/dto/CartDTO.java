package application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {

    private int id;
    private int accessaryId;
    private int serviceId;
    private int amount;
}

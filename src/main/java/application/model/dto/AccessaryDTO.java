package application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessaryDTO {
    private int id;
    private String name;
    private int accessaryTypeId;
    private Double price;
    private Double originalPrice;
    private Double promotionPrice;
    private int quantity;
    private int warranty;
    private String image;
    private String origin;
    private String description;
}

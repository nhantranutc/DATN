package application.model.viewmodel.accessary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessaryVM {
    private int id;
    private String name;
    private String accessaryTypeName;
    private String price;
    private int warranty;
    private String image;
    private String origin;
    private String description;
}

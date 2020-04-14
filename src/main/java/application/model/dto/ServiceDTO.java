package application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {
    private int id;
    private String name;
    private int serviceTypeId;
    private String description;
    private String content;
    private String image;
}

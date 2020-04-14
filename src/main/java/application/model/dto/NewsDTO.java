package application.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NewsDTO {

    private int id;
    private String title;
    private String mainImage;
    private String content;
    private String shortDesc;
    private int isHot;

}

package application.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String appointmentDate;
    private int vehicleBrand;
    private String content;
    private int actionType;
    private String email;
}

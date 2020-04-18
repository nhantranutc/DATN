package application.model.viewmodel.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookVM {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String vehicleBrand;
    private String content;
    private String appointmentDate;
}

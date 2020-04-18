package application.model.viewmodel.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVM {
    private int id;
    private String avatar;
    private String userName;
    private String email;
    private String fullName;
    private String addressName;
    private String phoneNumber;
    private String gender;
}

package application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "dbo_user_role")
public class UserRole {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    @Column(name = "user_role_id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "is_delete")
    private int isDelete;
}

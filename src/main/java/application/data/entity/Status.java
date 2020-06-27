package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "dbo_status")
public class Status {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_id")
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private List<AppointmentBook> orderList = new ArrayList<>();
}

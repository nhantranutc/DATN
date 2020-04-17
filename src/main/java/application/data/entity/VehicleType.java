package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "dbo_vehicle_type")
public class VehicleType {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_type_id")
    @Id
    private int id;

    @Column(name = "vehicle_type_name")
    private String vehicleTypeName;

    @Column(name = "is_delete")
    private int isDelete;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleType")
    private List<Vehicle> vehicleList = new ArrayList<>();
}

package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "dbo_vehicle")
public class Vehicle {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_id")
    @Id
    private int id;

    @Column(name = "vehicle_name")
    private String vehicleName;

    @Column(name = "vehicle_image")
    private String vehicleImage;

    @Column(name = "description")
    private String description;

    @Column(name = "vehicle_type_id" , insertable = false, updatable = false)
    private int vehicleTypeId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @Column(name = "is_delete")
    private int isDelete;
}

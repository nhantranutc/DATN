package application.model.viewmodel.vehicle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleVM {

    private int id;
    private String vehicleName;
    private String vehicleImage;
    private String description;
    private int vehicleType;
}

package application.model.viewmodel.book;

import application.model.viewmodel.vehicle.VehicleVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookLanding {

    private List<VehicleVM> vehicleVMList;
}

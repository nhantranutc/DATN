package application.model.viewmodel.common;

import application.data.entity.User;
import application.model.viewmodel.vehicle.VehicleVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LayoutHeaderVM {
    private String logo;
    private List<VehicleVM> vehicleVMList;
    private User user;
}

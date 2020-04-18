package application.controller.web;

import application.data.entity.Vehicle;
import application.data.service.VehicleService;
import application.model.viewmodel.book.BookLanding;
import application.model.viewmodel.vehicle.VehicleVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/book")
public class AppointmentBookController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping(path = "")
    public String book (Model model) {
        BookLanding vm = new BookLanding();

        List<VehicleVM> vehicleVMList = new ArrayList<>();
        for(Vehicle vehicle : vehicleService.getListAllVehicles()) {
            VehicleVM vehicleVM = new VehicleVM();
            vehicleVM.setId(vehicle.getId());
            vehicleVM.setVehicleName(vehicle.getVehicleName());

            vehicleVMList.add(vehicleVM);
        }

        vm.setVehicleVMList(vehicleVMList);

        model.addAttribute("vm", vm);

        return "book";
    }
}

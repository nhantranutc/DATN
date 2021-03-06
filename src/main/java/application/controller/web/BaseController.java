package application.controller.web;

import application.constant.LogoConstant;
import application.data.entity.AccessaryType;
import application.data.entity.User;
import application.data.entity.Vehicle;
import application.data.repository.VehicleRepository;
import application.data.service.AccessaryTypeService;
import application.data.service.UserService;
import application.data.service.VehicleService;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import application.model.viewmodel.vehicle.VehicleVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class BaseController {

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    public LayoutHeaderVM getLayoutHeaderVM() {
        LayoutHeaderVM vm = new LayoutHeaderVM();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        List<VehicleVM> vehicleVMList = new ArrayList<>();
        for(Vehicle vehicle : vehicleRepository.findAll()) {
            VehicleVM vehicleVM = new VehicleVM();
            vehicleVM.setId(vehicle.getId());
            vehicleVM.setVehicleName(vehicle.getVehicleName());

            vehicleVMList.add(vehicleVM);
        }

        if(user != null) {
            if(user.getAvatar() == null) {
                user.setAvatar("https://p7.hiclipart.com/preview/336/946/494/avatar-user-medicine-surgery-patient-avatar.jpg");
                vm.setUser(user);
            } else {
                vm.setUser(user);
            }
        }

        vm.setLogo(LogoConstant.logo);
        vm.setVehicleVMList(vehicleVMList);
        return vm;
    }

    public List<AccessaryTypeVM> getAccessaryTypeVM () {
        List<AccessaryTypeVM> accessaryTypeList = new ArrayList<>();
        for(AccessaryType accessaryType : accessaryTypeService.getListAllAccessaryTypes()) {
            AccessaryTypeVM accessaryTypeVM = new AccessaryTypeVM();
            accessaryTypeVM.setId(accessaryType.getId());
            accessaryTypeVM.setName(accessaryType.getName());

            accessaryTypeList.add(accessaryTypeVM);
        }

        return accessaryTypeList;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM(HttpServletRequest request) {

        LayoutHeaderAdminVM vm = new LayoutHeaderAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        if(user != null) {
            if(user.getAvatar() != null) {
                vm.setAvatar(user.getAvatar());
            }else {
                vm.setAvatar("https://p7.hiclipart.com/preview/336/946/494/avatar-user-medicine-surgery-patient-avatar.jpg");
            }
        }

        return vm;
    }
}

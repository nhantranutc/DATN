package application.controller.web.admin;

import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.Services;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.*;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.services.ServicesVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ServiceAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ServicesService servicesService;

    @GetMapping("service")
    public String service(Model model, HttpServletRequest request,
                          @Valid @ModelAttribute("serviceName") ServicesVM serviceName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {

        HomeAdminVM vm = new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        UserRole userRole1 = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Supporter, user.getId());
        UserRole userRole2 = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_User, user.getId());
        if (userRole != null) {
            vm.setRoleId(1);
        }
        if (userRole1 != null) {
            vm.setRoleId(4);
        }
        if (userRole2 != null) {
            vm.setRoleId(2);
        }

        Pageable pageable = new PageRequest(page, size);
        Page<Services> servicesPage = null;

        if (serviceName.getServiceName() != null && !serviceName.getServiceName().isEmpty()) {
            servicesPage = servicesService.getListAllServicesByServiceNameContaining(pageable, serviceName.getServiceName().trim());
        } else {
            servicesPage = servicesService.getListAllServicesByServiceNameContaining(pageable, null);
        }

        List<ServicesVM> servicesVMList = new ArrayList<>();
        for (Services services : servicesPage.getContent()) {
            ServicesVM servicesVM = new ServicesVM();
            servicesVM.setId(services.getId());
            servicesVM.setDescription(services.getDescription());
            servicesVM.setServiceName(services.getServiceName());

            servicesVMList.add(servicesVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setServicesVMList(servicesVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", servicesPage);

        return "/admin/service";
    }
}

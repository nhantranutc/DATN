package application.controller.web.admin;

import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.Accessary;
import application.data.entity.Services;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
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
public class BillAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private ServicesService servicesService;

    @GetMapping("/bill")
    public String bill(Model model, HttpServletRequest request,
                       @Valid @ModelAttribute("accessaryName") AccessaryVM accessaryName,
                       @Valid @ModelAttribute("serviceName") ServicesVM serviceName,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                       @RequestParam(name = "page1", required = false, defaultValue = "0") Integer page1,
                       @RequestParam(name = "size1", required = false, defaultValue = "10") Integer size1) {
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
        Page<Accessary> accessaryPage = null;

        if (accessaryName.getName() != null && !accessaryName.getName().isEmpty()) {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, null, accessaryName.getName().trim());
        } else {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, null, null);
        }

        List<AccessaryVM> accessaryVMList = new ArrayList<>();
        for (Accessary accessary : accessaryPage.getContent()) {
            AccessaryVM accessaryVM = new AccessaryVM();
            if (accessary.getAccessaryType() == null) {
                accessaryVM.setAccessaryTypeName("Không xác định?");
            } else {
                accessaryVM.setAccessaryTypeName(accessary.getAccessaryType().getName());
            }
            accessaryVM.setId(accessary.getId());
            accessaryVM.setName(accessary.getName());
            accessaryVM.setPrice(FormatPrice.formatPrice(accessary.getPrice()));
            accessaryVM.setWarranty(accessary.getWarranty());
            accessaryVM.setOrigin(accessary.getOrigin());
            accessaryVM.setQuantity(accessary.getQuantity());

            accessaryVMList.add(accessaryVM);
        }

        Pageable pageable1 = new PageRequest(page1, size1);
        Page<Services> servicesPage = null;

        if (serviceName.getServiceName() != null && !serviceName.getServiceName().isEmpty()) {
            servicesPage = servicesService.getListAllServicesByServiceNameContaining(pageable1, serviceName.getServiceName().trim());
        } else {
            servicesPage = servicesService.getListAllServicesByServiceNameContaining(pageable1, null);
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
        vm.setAccessaryVMList(accessaryVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", accessaryPage);
        model.addAttribute("page1", servicesPage);
        model.addAttribute("count", cartService.getListAllCarts().size());

        return "/admin/bill";
    }
}

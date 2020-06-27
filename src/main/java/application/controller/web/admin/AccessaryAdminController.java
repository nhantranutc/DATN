package application.controller.web.admin;

import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.Accessary;
import application.data.entity.AccessaryType;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.admin.HomeAdminVM;
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
public class AccessaryAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @GetMapping("/accessary")
    public String accessary(Model model,
                            HttpServletRequest request,
                            @Valid @ModelAttribute("accessaryName") AccessaryVM accessaryName,
                            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

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

            accessaryVMList.add(accessaryVM);
        }

        List<AccessaryTypeVM> accessaryTypeVMList = new ArrayList<>();
        for (AccessaryType accessaryType : accessaryTypeService.getListAllAccessaryTypes()) {
            AccessaryTypeVM accessaryTypeVM = new AccessaryTypeVM();
            accessaryTypeVM.setId(accessaryType.getId());
            accessaryTypeVM.setName(accessaryType.getName());

            accessaryTypeVMList.add(accessaryTypeVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setAccessaryVMList(accessaryVMList);
        vm.setAccessaryTypeVMList(accessaryTypeVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", accessaryPage);

        return "/admin/accessary";
    }
}

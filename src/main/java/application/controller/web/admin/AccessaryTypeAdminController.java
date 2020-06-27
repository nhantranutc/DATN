package application.controller.web.admin;

import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.AccessaryType;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.AccessaryTypeService;
import application.data.service.UserRoleService;
import application.data.service.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AccessaryTypeAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @GetMapping("accessaryType")
    public String accessaryType(Model model, HttpServletRequest request,
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
        Page<AccessaryType> accessaryTypePage = accessaryTypeService.getListAllAccessaryTypeByContaining(pageable);
        List<AccessaryTypeVM> accessaryTypeVMList = new ArrayList<>();
        for (AccessaryType accessaryType : accessaryTypePage.getContent()) {
            AccessaryTypeVM accessaryTypeVM = new AccessaryTypeVM();
            accessaryTypeVM.setId(accessaryType.getId());
            accessaryTypeVM.setName(accessaryType.getName());
            accessaryTypeVM.setDesc(accessaryType.getDescription());

            accessaryTypeVMList.add(accessaryTypeVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setAccessaryTypeVMList(accessaryTypeVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", accessaryTypePage);

        return "/admin/accessary-type";
    }
}

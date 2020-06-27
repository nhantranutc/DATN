package application.controller.web.admin;

import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.Role;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.RoleService;
import application.data.service.UserRoleService;
import application.data.service.UserService;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.role.RoleVM;
import application.model.viewmodel.user.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request,
                       @RequestParam(name = "roleId", required = false) Integer roleId) {
        HomeAdminVM vm = new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        UserRole userRole3 = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Supporter, user.getId());
        UserRole userRole2 = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_User, user.getId());
        if (userRole != null) {
            vm.setRoleId(1);
        }
        if (userRole3 != null) {
            vm.setRoleId(4);
        }
        if (userRole2 != null) {
            vm.setRoleId(2);
        }

        List<UserVM> userVMList = new ArrayList<>();
        if (roleId != null) {
            List<UserRole> userRoleList = userRoleService.findUserRolebyRoleId(roleId);
            for (UserRole userRole1 : userRoleList) {
                User user1 = userService.findOne(userRole1.getUserId());
                UserVM userVM = new UserVM();
                userVM.setId(user1.getId());
                userVM.setUserName(user1.getUserName());
                userVM.setFullName(user1.getFullName());
                userVM.setAddressName(user1.getAddressName());
                userVM.setGender(user1.getGender());
                userVM.setPhoneNumber(user1.getPhoneNumber());
                userVM.setEmail(user1.getEmail());

                userVMList.add(userVM);
            }

            List<RoleVM> roleVMList = new ArrayList<>();
            for (Role role : roleService.getListAllRole()) {
                RoleVM roleVM = new RoleVM();
                roleVM.setId(role.getId());
                if (role.getName().equals("ROLE_ADMIN")) {
                    roleVM.setName("Quản trị viên");
                }
                if (role.getName().equals("ROLE_USER")) {
                    roleVM.setName("Nhân viên thu ngân");
                }
                if (role.getName().equals("ROLE_CUSTOMER")) {
                    roleVM.setName("Khách hàng");
                }
                if (role.getName().equals("ROLE_SUPPORTER")) {
                    roleVM.setName("Nhân viên tư vấn");
                }

                roleVMList.add(roleVM);
            }
            vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
            vm.setUserVMList(userVMList);
            vm.setRoleVMList(roleVMList);

            model.addAttribute("vm", vm);

            return "/admin/user";
        } else {
            for (User user1 : userService.getListAllUsers()) {
                UserVM userVM = new UserVM();
                userVM.setId(user1.getId());
                userVM.setUserName(user1.getUserName());
                userVM.setFullName(user1.getFullName());
                userVM.setAddressName(user1.getAddressName());
                userVM.setGender(user1.getGender());
                userVM.setPhoneNumber(user1.getPhoneNumber());
                userVM.setEmail(user1.getEmail());

                userVMList.add(userVM);
            }

            List<RoleVM> roleVMList = new ArrayList<>();
            for (Role role : roleService.getListAllRole()) {
                RoleVM roleVM = new RoleVM();
                roleVM.setId(role.getId());
                if (role.getName().equals("ROLE_ADMIN")) {
                    roleVM.setName("Quản trị viên");
                }
                if (role.getName().equals("ROLE_USER")) {
                    roleVM.setName("Nhân viên thu ngân");
                }
                if (role.getName().equals("ROLE_CUSTOMER")) {
                    roleVM.setName("Khách hàng");
                }
                if (role.getName().equals("ROLE_SUPPORTER")) {
                    roleVM.setName("Nhân viên tư vấn");
                }

                roleVMList.add(roleVM);
            }
            vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
            vm.setUserVMList(userVMList);
            vm.setRoleVMList(roleVMList);

            model.addAttribute("vm", vm);

            return "/admin/user";
        }
    }
}

package application.controller.web.admin;

import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.AccessaryTypeService;
import application.data.service.AppointmentBookSevice;
import application.data.service.UserRoleService;
import application.data.service.UserService;
import application.model.viewmodel.chart.ChartDataVM;
import application.model.viewmodel.chart.ChartDataVM1;
import application.model.viewmodel.chart.ChartVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ChartAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @Autowired
    private AppointmentBookSevice appointmentBookSevice;

    @GetMapping("/chart")
    public String chart(Model model, HttpServletRequest request) {

        ChartVM vm = new ChartVM();

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

        List<ChartDataVM> chartAccessaryTypeVMS = accessaryTypeService.getAllAccessaryType();
        List<ChartDataVM1> totalPriceInMonthOfYear2020 = accessaryTypeService.getTotalPriceInMonthOfYear2020();
        List<ChartDataVM> countAppointmentBookInDay = appointmentBookSevice.countAppointmentBookInDay();
        List<ChartDataVM1> chartTotalPriceInDayOfMonth = accessaryTypeService.getTotalPriceInDayOfMonth();

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setChartAccessaryTypeVMS(chartAccessaryTypeVMS);
        vm.setTotalPriceInMonthOfYear2020(totalPriceInMonthOfYear2020);
        vm.setCountAppointmentBookInDay(countAppointmentBookInDay);
        vm.setChartTotalPriceInDayOfMonth(chartTotalPriceInDayOfMonth);

        model.addAttribute("vm", vm);

        return "admin/chart";
    }
}

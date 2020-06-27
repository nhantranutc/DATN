package application.controller.web.admin;

import application.constant.FormatDate;
import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.AppointmentBook;
import application.data.entity.User;
import application.data.entity.UserRole;
import application.data.service.AppointmentBookSevice;
import application.data.service.UserRoleService;
import application.data.service.UserService;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.book.BookVM;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class BookAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AppointmentBookSevice appointmentBookSevice;

    @GetMapping("book")
    public String book(Model model, HttpServletRequest request,
                       @Valid @ModelAttribute("appointmentDate") BookVM appointmentDate,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) throws ParseException {
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
        Page<AppointmentBook> appointmentBookPage = null;
        if (appointmentDate.getAppointmentDate() != null) {
            appointmentBookPage = appointmentBookSevice.getListAllAppointmentBookByCreateDateWithPageable(pageable, FormatDate.formatDate(appointmentDate.getAppointmentDate().trim()));
        } else {
            appointmentBookPage = appointmentBookSevice.getListAllAppointmentBookByCreateDateWithPageable(pageable, null);
        }

        List<BookVM> bookVMList = new ArrayList<>();
        for (AppointmentBook appointmentBook : appointmentBookPage.getContent()) {
            BookVM bookVM = new BookVM();
            bookVM.setId(appointmentBook.getId());
            bookVM.setFullName(appointmentBook.getFullName());
            bookVM.setPhoneNumber(appointmentBook.getPhoneNumber());
            bookVM.setVehicleBrand(appointmentBook.getVehicleBrand());
            bookVM.setContent(appointmentBook.getContent());
            bookVM.setEmail(appointmentBook.getEmail());
            bookVM.setAppointmentDate(FormatDate.formatDate(appointmentBook.getAppointmentDate()));
            bookVM.setStatusName(appointmentBook.getStatus().getName());
            bookVM.setIsSend(appointmentBook.getIsSend());
            bookVM.setActionType(appointmentBook.getActionType());

            bookVMList.add(bookVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setBookVMList(bookVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", appointmentBookPage);

        return "/admin/book";
    }
}

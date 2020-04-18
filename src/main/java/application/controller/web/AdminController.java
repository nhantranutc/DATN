package application.controller.web;

import application.constant.FormatDate;
import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.data.entity.*;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.book.BookVM;
import application.model.viewmodel.chart.ChartDataVM;
import application.model.viewmodel.chart.ChartVM;
import application.model.viewmodel.news.NewsVM;
import application.model.viewmodel.role.RoleVM;
import application.model.viewmodel.services.ServicesVM;
import application.model.viewmodel.user.UserVM;
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
public class AdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AppointmentBookSevice appointmentBookSevice;

    @GetMapping("")
    public String home(Model model,
                       HttpServletRequest request) {

        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));

        model.addAttribute("vm", vm);

        return "/admin/home";
    }

    @GetMapping("/accessary")
    public String accessary(Model model,
                       HttpServletRequest request,
                        @Valid @ModelAttribute("accessaryName")AccessaryVM accessaryName,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        Pageable pageable =new PageRequest(page, size);
        Page<Accessary> accessaryPage= null;

        if(accessaryName.getName() != null && !accessaryName.getName().isEmpty()) {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, null, accessaryName.getName().trim());
        }else {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, null, null);
        }

        List<AccessaryVM> accessaryVMList = new ArrayList<>();
        for(Accessary accessary : accessaryPage.getContent()) {
            AccessaryVM accessaryVM = new AccessaryVM();
            if(accessary.getAccessaryType() == null ) {
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
        for(AccessaryType accessaryType : accessaryTypeService.getListAllAccessaryTypes()) {
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

    @GetMapping("service")
    public String service(Model model, HttpServletRequest request,
                          @Valid @ModelAttribute("serviceName") ServicesVM serviceName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {

        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        Pageable pageable =new PageRequest(page, size);
        Page<Services> servicesPage= null;

        if(serviceName.getServiceName() != null && !serviceName.getServiceName().isEmpty()) {
            servicesPage = servicesService.getListAllServicesByServiceNameContaining(pageable, serviceName.getServiceName().trim());
        }else {
            servicesPage = servicesService.getListAllServicesByServiceNameContaining(pageable, null);
        }

        List<ServicesVM> servicesVMList = new ArrayList<>();
        for(Services services : servicesPage.getContent()) {
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

    @GetMapping("news")
    public String news(Model model, HttpServletRequest request,
                          @Valid @ModelAttribute("title") NewsVM title,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {

        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        Pageable pageable =new PageRequest(page, size);
        Page<News> newsPage= null;

        if(title.getTitle() != null && !title.getTitle().isEmpty()) {
            newsPage = newsService.getListAllNewsByTitleContaining(pageable, title.getTitle().trim());
        }else {
            newsPage = newsService.getListAllNewsByTitleContaining(pageable, null);
        }

        List<NewsVM> newsVMList = new ArrayList<>();
        for(News news : newsPage.getContent()) {
            NewsVM newsVM = new NewsVM();
            newsVM.setNewsId(news.getId());
            newsVM.setTitle(news.getTitle());
            newsVM.setShortDesc(news.getShortDesc());
            newsVM.setAuthor(news.getAuthor());
            newsVM.setCreateDate(news.getCreateDate());

            newsVMList.add(newsVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setNewsVMS(newsVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", newsPage);

        return "/admin/news";
    }

    @GetMapping("accessaryType")
    public String accessaryType(Model model, HttpServletRequest request,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        Pageable pageable =new PageRequest(page, size);
        Page<AccessaryType> accessaryTypePage= accessaryTypeService.getListAllAccessaryTypeByContaining(pageable);
        List<AccessaryTypeVM> accessaryTypeVMList = new ArrayList<>();
        for(AccessaryType accessaryType : accessaryTypePage.getContent() ) {
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

    @GetMapping("book")
    public String book(Model model, HttpServletRequest request,
                                @Valid @ModelAttribute("appointmentDate") BookVM appointmentDate,
                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) throws ParseException {
        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        Pageable pageable =new PageRequest(page, size);
        Page<AppointmentBook> appointmentBookPage = null;
        if(appointmentDate.getAppointmentDate() != null) {
            appointmentBookPage = appointmentBookSevice.getListAllAppointmentBookByCreateDateWithPageable(pageable, FormatDate.formatDate(appointmentDate.getAppointmentDate().trim()));
        } else {
            appointmentBookPage = appointmentBookSevice.getListAllAppointmentBookByCreateDateWithPageable(pageable, null);
        }

        List<BookVM> bookVMList = new ArrayList<>();
        for(AppointmentBook appointmentBook : appointmentBookPage.getContent()) {
            BookVM bookVM = new BookVM();
            bookVM.setId(appointmentBook.getId());
            bookVM.setFullName(appointmentBook.getFullName());
            bookVM.setPhoneNumber(appointmentBook.getPhoneNumber());
            bookVM.setVehicleBrand(appointmentBook.getVehicleBrand());
            bookVM.setContent(appointmentBook.getContent());
            bookVM.setAppointmentDate(FormatDate.formatDate(appointmentBook.getAppointmentDate()));

            bookVMList.add(bookVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setBookVMList(bookVMList);

        model.addAttribute("vm", vm);
        model.addAttribute("page", appointmentBookPage);

        return "/admin/book";
    }

    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request,
                       @RequestParam(name = "roleId", required = false) Integer roleId) {
        HomeAdminVM vm =new HomeAdminVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        List<UserVM> userVMList = new ArrayList<>();
        if(roleId != null ){
            List<UserRole> userRoleList = userRoleService.findUserRolebyRoleId(roleId);
            for(UserRole userRole1 : userRoleList) {
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
            for(Role role : roleService.getListAllRole()) {
                RoleVM roleVM = new RoleVM();
                roleVM.setId(role.getId());
                if(role.getName().equals("ROLE_ADMIN")) {
                    roleVM.setName("Quản trị viên");
                }
                if(role.getName().equals("ROLE_USER")) {
                    roleVM.setName("Nhân viên thu ngân");
                }

                roleVMList.add(roleVM);
            }
            vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
            vm.setUserVMList(userVMList);
            vm.setRoleVMList(roleVMList);

            model.addAttribute("vm", vm);

            return "/admin/user";
        } else {
            for(User user1 : userService.getListAllUsers()) {
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
            for(Role role : roleService.getListAllRole()) {
                RoleVM roleVM = new RoleVM();
                roleVM.setId(role.getId());
                if(role.getName().equals("ROLE_ADMIN")) {
                    roleVM.setName("Quản trị viên");
                }
                if(role.getName().equals("ROLE_USER")) {
                    roleVM.setName("Nhân viên thu ngân");
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

    @GetMapping("/chart")
    public String chart(Model model, HttpServletRequest request) {

        ChartVM vm = new ChartVM();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        UserRole userRole = userRoleService.findUserRolebyRoleIdAndUserId(RoleIdConstant.Role_Admin, user.getId());
        if(userRole != null) {
            vm.setRoleId(1);
        }

        List<ChartDataVM> chartAccessaryTypeVMS = accessaryTypeService.getAllAccessaryType();

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setChartAccessaryTypeVMS(chartAccessaryTypeVMS);

        model.addAttribute("vm", vm);

        return "admin/chart";
    }
}

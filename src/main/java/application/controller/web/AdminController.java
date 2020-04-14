package application.controller.web;

import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.data.entity.*;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.news.NewsVM;
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
public class AdminController extends BaseController {

    @Autowired
    private UserService userService;

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
}

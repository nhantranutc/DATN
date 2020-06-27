package application.controller.web.admin;

import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.*;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.services.ServicesVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CartAdminController extends BaseController {

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

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {
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

        double totalPrice = 0;

        List<AccessaryVM> accessaryVMList = new ArrayList<>();
        List<Cart> cartList = cartService.getListAllAccessary();
        if(cartList != null) {
            for(Cart cart : cartList) {
                AccessaryVM accessaryVM = new AccessaryVM();

                Accessary accessary = accessaryService.findOne(cart.getAccessaryId());
                accessaryVM.setCartId(cart.getId());
                accessaryVM.setImage(accessary.getImage());
                accessaryVM.setName(accessary.getName());
                accessaryVM.setPrice(FormatPrice.formatPrice(accessary.getPrice()));
                accessaryVM.setQuantity(cart.getAmount());
                double price = cart.getAmount() * accessary.getPrice();
                accessaryVM.setPay(FormatPrice.formatPrice(price));
                totalPrice += price;
                accessaryVMList.add(accessaryVM);
            }
        }

        List<ServicesVM> servicesVMList = new ArrayList<>();
        List<Cart> cartList1 = cartService.getListAllService();
        if(cartList1 != null) {
            for(Cart cart : cartList1) {
                ServicesVM servicesVM = new ServicesVM();

                Services services = servicesService.findOne(cart.getServiceId());
                servicesVM.setCartId(cart.getId());
                servicesVM.setImage(services.getImage());
                servicesVM.setServiceName(services.getServiceName());
                servicesVM.setPrice(FormatPrice.formatPrice(services.getPrice()));
                servicesVM.setAmount(cart.getAmount());
                double price = cart.getAmount() * services.getPrice();
                servicesVM.setPay(FormatPrice.formatPrice(price));
                totalPrice += price;

                servicesVMList.add(servicesVM);
            }
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setServicesVMList(servicesVMList);
        vm.setAccessaryVMList(accessaryVMList);
        vm.setAccessaryAmount(accessaryVMList.size());
        vm.setServiceAmount(servicesVMList.size());
        vm.setTotalPrice(FormatPrice.formatPrice(totalPrice));

        model.addAttribute("vm", vm);
        model.addAttribute("count", cartService.getListAllCarts().size());

        return "/admin/cart";
    }
}

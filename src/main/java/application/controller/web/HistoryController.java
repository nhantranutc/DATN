package application.controller.web;

import application.constant.FormatDate;
import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.data.entity.*;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.order.OrderHistoryVM;
import application.model.viewmodel.order.OrderVM;
import application.model.viewmodel.services.ServicesVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/history")
public class HistoryController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private ServicesService servicesService;

    @GetMapping("")
    public String history (Model model,
                           @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName,
                           final Principal principal ) throws ParseException {
        OrderHistoryVM vm = new OrderHistoryVM();

        List<OrderVM> orderVMList = new ArrayList<>();
        List<Order> orderList = null;

        if(principal != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByUsername(username);

            orderList = orderService.findOrderByEmail(user.getEmail());
        }

        for(Order order : orderList) {
            OrderVM orderVM = new OrderVM();
            orderVM.setId(order.getId());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setEmail(order.getEmail());
            orderVM.setAddress((order.getAddress()));
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setPrice(FormatPrice.formatPrice(order.getPrice()));
            orderVM.setCreatedDate(FormatDate.formatDate(order.getCreatedDate()));

            orderVMList.add(orderVM);
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());
        vm.setOrderVMList(orderVMList);

        model.addAttribute("vm", vm);
        return "/history";
    }

    @GetMapping("detail/{id}")
    public String detail(Model model,
                         @PathVariable int id,
                         @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName) {
        HomeAdminVM vm = new HomeAdminVM();

        double totalPrice = 0;

        List<AccessaryVM> accessaryVMList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDetailService.getListOrderDetailByAccessaryId(id);
        if(orderDetailList != null) {
            for(OrderDetail orderDetail : orderDetailList) {
                AccessaryVM accessaryVM = new AccessaryVM();

                Accessary accessary = accessaryService.findOne(orderDetail.getAccessaryId());
                accessaryVM.setImage(accessary.getImage());
                accessaryVM.setName(accessary.getName());
                accessaryVM.setPrice(FormatPrice.formatPrice(accessary.getPrice()));
                accessaryVM.setQuantity(orderDetail.getAmount());
                double price = orderDetail.getAmount() * accessary.getPrice();
                accessaryVM.setPay(FormatPrice.formatPrice(price));
                totalPrice += price;
                accessaryVMList.add(accessaryVM);
            }
        }

        List<ServicesVM> servicesVMList = new ArrayList<>();
        List<OrderDetail> orderDetailList1 = orderDetailService.getListOrderDetailByServiceId(id);
        if(orderDetailList1 != null) {
            for(OrderDetail orderDetail : orderDetailList1) {
                ServicesVM servicesVM = new ServicesVM();

                Services services = servicesService.findOne(orderDetail.getServiceId());
                servicesVM.setImage(services.getImage());
                servicesVM.setServiceName(services.getServiceName());
                servicesVM.setPrice(FormatPrice.formatPrice(services.getPrice()));
                servicesVM.setAmount(orderDetail.getAmount());
                double price = orderDetail.getAmount() * services.getPrice();
                servicesVM.setPay(FormatPrice.formatPrice(price));
                totalPrice += price;

                servicesVMList.add(servicesVM);
            }
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setAccessaryTypeVMList(super.getAccessaryTypeVM());
        vm.setServicesVMList(servicesVMList);
        vm.setAccessaryVMList(accessaryVMList);
        vm.setAccessaryAmount(accessaryVMList.size());
        vm.setServiceAmount(servicesVMList.size());
        vm.setTotalPrice(FormatPrice.formatPrice(totalPrice));

        model.addAttribute("vm", vm);

        return "/history-detail";
    }
}

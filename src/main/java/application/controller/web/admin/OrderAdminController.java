package application.controller.web.admin;

import application.constant.FormatDate;
import application.constant.FormatPrice;
import application.constant.RoleIdConstant;
import application.controller.web.BaseController;
import application.data.entity.*;
import application.data.service.*;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.admin.HomeAdminVM;
import application.model.viewmodel.admin.InfoCustomerVM;
import application.model.viewmodel.mail.MailVM;
import application.model.viewmodel.order.OrderHistoryVM;
import application.model.viewmodel.order.OrderVM;
import application.model.viewmodel.services.ServicesVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class OrderAdminController extends BaseController {

    private static final Logger logger = LogManager.getLogger(OrderAdminController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/getcheckout")
    public String getCheckout(Model model) {

        OrderVM order = new OrderVM();

        model.addAttribute("order", order);

        return "/admin/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("order") OrderVM orderVM,
                           final Principal principal) throws ParseException {

        Order order = new Order();

        double totalPrice = 0;

        if (principal != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            order.setUserName(username);
        }

        order.setAddress(orderVM.getAddress());
        order.setCustomerName(orderVM.getCustomerName());
        order.setEmail(orderVM.getEmail());
        order.setPhoneNumber(orderVM.getPhoneNumber());
        String strDate = FormatDate.formatDate(new Date());
        order.setCreatedDate(FormatDate.formatDate(strDate));

        List<Cart> cartServiceListAllAccessary = cartService.getListAllAccessary();
        for (Cart cart : cartServiceListAllAccessary) {
            Accessary accessary = accessaryService.findOne(cart.getAccessaryId());

            accessary.setQuantity(accessary.getQuantity() - cart.getAmount());
            accessaryService.addNewAccessary(accessary);

            double price = accessary.getPrice() * cart.getAmount();
            totalPrice += price;
        }
        List<Cart> cartServiceListAllService = cartService.getListAllService();
        for (Cart cart : cartServiceListAllService) {
            Services services = servicesService.findOne(cart.getServiceId());

            double price = cart.getAmount() * services.getPrice();
            totalPrice += price;
        }

        order.setPrice(totalPrice);
        orderService.addNewOrder(order);

        for (Cart cart : cartService.getListAllCarts()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            if (cart.getAccessaryId() != 0) {
                Accessary accessary = accessaryService.findOne(cart.getAccessaryId());
                orderDetail.setAccessaryId(cart.getAccessaryId());
                orderDetail.setPrice(accessary.getPrice());
            }
            if (cart.getServiceId() != 0) {
                Services services = servicesService.findOne(cart.getServiceId());
                orderDetail.setServiceId(cart.getServiceId());
                orderDetail.setPrice(services.getPrice());
            }

            orderDetail.setAmount(cart.getAmount());
            orderDetailService.addNewOrderDetail(orderDetail);
        }
        cartService.deleteListCarts(cartService.getListAllCarts());

        return "redirect:/admin/order";
    }

    @GetMapping("/order")
    public String history(Model model,
                          HttpServletRequest request,
                          @Valid @ModelAttribute("order") OrderVM order,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                          final Principal principal) throws ParseException {
        OrderHistoryVM vm = new OrderHistoryVM();

        List<OrderVM> orderVMList = new ArrayList<>();
        Pageable pageable = new PageRequest(page, size);
        Page<Order> orderPage = null;

        if (principal != null) {
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
        }

        if (order.getId() > 0) {
            orderPage = orderService.getListOrder(pageable, order.getId());
        } else {
            orderPage = orderService.getListOrder(pageable, null);
        }

        for (Order order1 : orderPage.getContent()) {
            OrderVM orderVM = new OrderVM();
            orderVM.setId(order1.getId());
            orderVM.setPhoneNumber(order1.getPhoneNumber());
            orderVM.setEmail(order1.getEmail());
            orderVM.setAddress((order1.getAddress()));
            orderVM.setCustomerName(order1.getCustomerName());
            orderVM.setPrice(FormatPrice.formatPrice(order1.getPrice()));
            orderVM.setCreatedDate(FormatDate.formatDate(order1.getCreatedDate()));

            orderVMList.add(orderVM);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);
        Date date = cal.getTime();
        String strDate = FormatDate.formatDate(date);
        List<Order> orderList = orderService.getListOrder10DayAgo(FormatDate.formatDate(strDate));
        List<MailVM> sendMailList = new ArrayList<>();
        for(Order order1 : orderList) {
            MailVM mailVM = new MailVM();
            mailVM.setName(order1.getCustomerName());
            mailVM.setEmail(order1.getEmail());

            sendMailList.add(mailVM);
        }

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setOrderVMList(orderVMList);
        vm.setSendMailList(sendMailList);

        model.addAttribute("vm", vm);

        return "/admin/order";
    }

    @GetMapping("detail/{id}")
    public String detail(Model model,
                         @PathVariable int id,
                         HttpServletRequest request) {
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

        Order order = orderService.findOne(id);
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");

        InfoCustomerVM infoCustomerVM = new InfoCustomerVM();
        infoCustomerVM.setId(id);
        infoCustomerVM.setAddress(order.getAddress());
        infoCustomerVM.setEmail(order.getEmail());
        infoCustomerVM.setName(order.getCustomerName());
        infoCustomerVM.setNumberPhone(order.getPhoneNumber());
        infoCustomerVM.setDate(formatterDate.format(new Date()));
        infoCustomerVM.setTime(formatterTime.format(new Date()));

        List<AccessaryVM> accessaryVMList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDetailService.getListOrderDetailByAccessaryId(id);
        if (orderDetailList != null) {
            for (OrderDetail orderDetail : orderDetailList) {
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
        if (orderDetailList1 != null) {
            for (OrderDetail orderDetail : orderDetailList1) {
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

        vm.setLayoutHeaderAdminVM(super.getLayoutHeaderAdminVM(request));
        vm.setAccessaryTypeVMList(super.getAccessaryTypeVM());
        vm.setServicesVMList(servicesVMList);
        vm.setAccessaryVMList(accessaryVMList);
        vm.setAccessaryAmount(accessaryVMList.size());
        vm.setServiceAmount(servicesVMList.size());
        vm.setTotalPrice(FormatPrice.formatPrice(totalPrice));
        vm.setInfoCustomerVM(infoCustomerVM);

        model.addAttribute("vm", vm);

        return "/admin/history-detail";
    }
}

package application.controller.api;

import application.data.entity.Accessary;
import application.data.entity.Cart;
import application.data.entity.Services;
import application.data.service.AccessaryService;
import application.data.service.CartService;
import application.data.service.ServicesService;
import application.model.api.BaseApiResult;
import application.model.dto.CartDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/cart")
public class CartApiController {
    private static final Logger logger = LogManager.getLogger(CartApiController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private ServicesService servicesService;

    @PostMapping("/add-accessary")
    public BaseApiResult addAccessary(@RequestBody CartDTO dto) {
        BaseApiResult result = new BaseApiResult();
        Accessary accessary = accessaryService.findOne(dto.getAccessaryId());

        try {
            if (accessary.getQuantity() == 0) {
                result.setMessage("Phụ tùng này tạm thời hết hàng");
                result.setSuccess(false);
                return result;
            }
            if(dto.getAmount() > 0 && dto.getAccessaryId() > 0 && accessary.getQuantity() >= dto.getAmount()) {
                if(accessary != null) {
                    Cart cart = cartService.findcartByAccessaryId(accessary.getId());
                    if(cart != null) {
                        cart.setAmount(cart.getAmount() + dto.getAmount());
                        cartService.updateCart(cart);
                    } else {
                        Cart cart1 = new Cart();
                        cart1.setAmount(dto.getAmount());
                        cart1.setAccessaryId(accessary.getId());

                        cartService.addNewCart(cart1);
                    }
                    result.setMessage("Thêm Thành Công!");
                    result.setSuccess(true);
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        result.setMessage("Thêm Bị Lỗi");
        result.setSuccess(false);
        return result;
    }

    @PostMapping("/add-service")
    public BaseApiResult addService(@RequestBody CartDTO dto) {
        BaseApiResult result = new BaseApiResult();
        Services services = servicesService.findOne(dto.getServiceId());

        try {
            if(dto.getAmount() > 0 && dto.getServiceId() > 0) {
                if(services != null) {
                    Cart cart = cartService.findcartByServiceId(services.getId());
                    if(cart != null) {
                        cart.setAmount(cart.getAmount() + dto.getAmount());
                        cartService.updateCart(cart);
                    } else {
                        Cart cart1 = new Cart();
                        cart1.setAmount(dto.getAmount());
                        cart1.setServiceId(services.getId());

                        cartService.addNewCart(cart1);
                    }
                    result.setMessage("Thêm Thành Công!");
                    result.setSuccess(true);
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        result.setMessage("Thêm Bị Lỗi");
        result.setSuccess(false);
        return result;
    }

    @PostMapping("/update")
    public BaseApiResult updateCart(@RequestBody CartDTO dto) {
        BaseApiResult result = new BaseApiResult();
        try {
            if(dto.getId() > 0 && dto.getAmount() > 0) {
                Cart cart = cartService.findOne(dto.getId());
                if(cart != null) {
                    cart.setAmount(dto.getAmount());
                    cartService.updateCart(cart);

                    result.setMessage("Cập nhật thành công!");
                    result.setSuccess(true);
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        result.setMessage("Cập nhật bị lỗi");
        result.setSuccess(false);
        return result;
    }

    @PostMapping("/delete")
    public BaseApiResult deleteCart( @RequestBody CartDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            if(cartService.deleteCart(dto.getId()) == true) {

                result.setMessage("Xóa Thành Công");
                result.setSuccess(true);
                return result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        result.setSuccess(false);
        result.setMessage("Xóa bị lỗi!");
        return result;
    }
}

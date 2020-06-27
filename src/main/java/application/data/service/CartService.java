package application.data.service;

import application.data.entity.Cart;
import application.data.repository.CartRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private static final Logger logger = LogManager.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    public void addNewCart(Cart cart) {
        cartRepository.save(cart);
    }

    public boolean updateCart(Cart cart) {
        try {
            cartRepository.save(cart);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public Cart findOne(int id) {
        return cartRepository.findOne(id);
    }

    public boolean deleteCart(int id) {
        try {
            cartRepository.delete(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Transactional
    public boolean deleteListCarts(List<Cart> carts) {
        try {
            cartRepository.delete(carts);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public Cart findcartByAccessaryId(int accessaryId) {
        try {
            return cartRepository.findcartByAccessaryId(accessaryId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public Cart findcartByServiceId(int serviceId) {
        try {
            return cartRepository.findcartByServiceId(serviceId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public List<Cart> getListAllAccessary() {
        return cartRepository.getListAllAccessary();
    }

    public List<Cart> getListAllService() {
        return cartRepository.getListAllService();
    }

    public List<Cart> getListAllCarts() {
        try {
            return cartRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}

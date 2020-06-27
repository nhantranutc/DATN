package application.data.service;

import application.data.entity.Order;
import application.data.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public void addNewOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOne(int orderId) {
        return orderRepository.findOne(orderId);
    }

    public List<Order> findOrderByUserName(String userName) {
        return orderRepository.findOrderByUserName(userName);
    }

    public List<Order> findOrderByEmail(String email) {
        return orderRepository.findOrderByEmail(email);
    }

    public Page<Order> getListOrder(Pageable pageable, Integer id) {
        return orderRepository.getListOrder(pageable,id);
    }

    public List<Order> getListAll() {
        return orderRepository.findAll();
    }

    public List<Order> getListOrder10DayAgo(Date createdDate) {
        return orderRepository.getListOrder10DayAgo(createdDate);
    }
}

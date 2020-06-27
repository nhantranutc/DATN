package application.data.service;

import application.data.entity.OrderDetail;
import application.data.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void addNewOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    public OrderDetail findOne(int id) {
        return orderDetailRepository.findOne(id);
    }

    public List<OrderDetail> getListOrderDetailByServiceId(Integer id) {
        return orderDetailRepository.getListOrderDetailByServiceId(id);
    }

    public List<OrderDetail> getListOrderDetailByAccessaryId(Integer id) {
        return orderDetailRepository.getListOrderDetailByAccessaryId(id);
    }
}

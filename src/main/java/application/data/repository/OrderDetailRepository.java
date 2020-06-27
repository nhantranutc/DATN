package application.data.repository;

import application.data.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query("SELECT c FROM dbo_order_detail c WHERE c.orderId = :id AND c.accessaryId NOT IN (0)")
    List<OrderDetail> getListOrderDetailByAccessaryId(@Param("id") Integer id);

    @Query("SELECT c FROM dbo_order_detail c WHERE c.orderId = :id AND c.serviceId NOT IN (0)")
    List<OrderDetail> getListOrderDetailByServiceId(@Param("id") Integer id);
}

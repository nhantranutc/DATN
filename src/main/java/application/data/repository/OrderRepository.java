package application.data.repository;

import application.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM dbo_order o WHERE (:userName IS NULL OR (o.userName = :userName))")
    List<Order> findOrderByUserName(@Param("userName") String userName);

    @Query("SELECT o FROM dbo_order o WHERE (:email IS NULL OR (o.email = :email))")
    List<Order> findOrderByEmail(@Param("email") String email);

    @Query("SELECT o FROM dbo_order o WHERE (:id IS NULL OR (o.id = :id))")
    Page<Order> getListOrder(Pageable pageable, @Param("id") Integer id);

    @Query("SELECT o FROM dbo_order o WHERE o.createdDate = :createdDate")
    List<Order> getListOrder10DayAgo(@Param("createdDate") Date createdDate);
}

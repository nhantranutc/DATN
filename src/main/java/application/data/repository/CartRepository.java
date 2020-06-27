package application.data.repository;

import application.data.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT c FROM dbo_cart c WHERE c.accessaryId = :accessaryId")
    Cart findcartByAccessaryId(@Param("accessaryId") int accessaryId);

    @Query("SELECT c FROM dbo_cart c WHERE c.serviceId = :serviceId")
    Cart findcartByServiceId(@Param("serviceId") int serviceId);

    @Query("SELECT c FROM dbo_cart c WHERE c.accessaryId NOT IN (0)")
    List<Cart> getListAllAccessary();

    @Query("SELECT c FROM dbo_cart c WHERE c.serviceId NOT IN (0)")
    List<Cart> getListAllService();
}

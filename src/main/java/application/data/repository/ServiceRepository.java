package application.data.repository;

import application.data.entity.Accessary;
import application.data.entity.News;
import application.data.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Services, Integer> {

    @Query(value = "SELECT s.* FROM dbo_service s WHERE s.service_id NOT IN (:servicesId) LIMIT 4", nativeQuery = true)
    List<Services> getListServicesHot(@Param("servicesId") int servicesId );

    @Query("SELECT s FROM dbo_service s ")
    Page<Services> getListAllServices(Pageable pageable);

    @Query(value = "SELECT s.* FROM dbo_service s WHERE s.service_id IN (1, 5, 7, 8)", nativeQuery = true)
    List<Services> getListServices();

    @Query("SELECT p FROM dbo_service p " +
            "WHERE (:serviceName IS NULL OR UPPER(p.serviceName) LIKE CONCAT('%',UPPER(:serviceName),'%'))")
    Page<Services> getListAllServicesByServiceNameContaining(Pageable pageable, @Param("serviceName") String serviceName);

}

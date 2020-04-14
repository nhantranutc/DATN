package application.data.repository;

import application.data.entity.Accessary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessaryRepository extends JpaRepository<Accessary, Integer> {

    @Query("SELECT p FROM dbo_accessary p " +
            "WHERE (:accessaryTypeId IS NULL OR (p.accessaryTypeId = :accessaryTypeId))" +
            "AND (:accessarytName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:accessarytName),'%'))")
    Page<Accessary> getListAccessaryByAccessaryTypeOrAccessaryNameContaining(Pageable pageable, @Param("accessaryTypeId") Integer accessaryTypeId, @Param("accessarytName") String accessarytName);

    @Query("SELECT p FROM dbo_accessary p WHERE (p.price BETWEEN :startPrice AND :endPrice )")
    Page<Accessary> filterListAccessaryBetweenStartPriceAndEndPrice(Pageable pageable, @Param("startPrice") Double startPrice, @Param("endPrice") Double endPrice);

    @Query("SELECT p FROM dbo_accessary p WHERE (p.price <= :currentPrice )")
    Page<Accessary> filterListAccessaryLessThanCurrentPrice(Pageable pageable, @Param("currentPrice") Double currentPrice);

    @Query("SELECT p FROM dbo_accessary p WHERE (p.price >= :currentPrice )")
    Page<Accessary> filterListProductGreatThanCurrentPrice(Pageable pageable, @Param("currentPrice") Double currentPrice);

    @Query(value = "SELECT p.* FROM dbo_accessary p WHERE (p.name LIKE '%Lop%') LIMIT 3", nativeQuery = true)
    List<Accessary> getListAccessarybyName1();

    @Query(value = "SELECT p.* FROM dbo_accessary p WHERE (p.name LIKE '%Ac%') LIMIT 3", nativeQuery = true)
    List<Accessary> getListAccessarybyName2();
}

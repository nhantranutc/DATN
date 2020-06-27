package application.data.repository;

import application.data.entity.AccessaryType;
import application.model.viewmodel.chart.ChartDataVM;
import application.model.viewmodel.chart.ChartDataVM1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessaryTypeRepository extends JpaRepository<AccessaryType, Integer> {

    @Query("SELECT n FROM dbo_accessary_type n ")
    Page<AccessaryType> getListAllAccessaryTypeByContaining(Pageable pageable);


    @Query("SELECT new application.model.viewmodel.chart.ChartDataVM(c.name, count(p.id)) " +
            "FROM dbo_accessary_type c " +
            "INNER JOIN c.accessaryList p " +
            "GROUP BY c.id")
    List<ChartDataVM> getAllAccessaryType();

    @Query("SELECT new application.model.viewmodel.chart.ChartDataVM1(DATE_FORMAT(o.createdDate, '%Y-%m'),sum(o.price)) " +
            "FROM dbo_order o " +
            "WHERE o.createdDate BETWEEN '2020-01-01 0:0:0' AND '2020-12-31 16:26:39' " +
            "GROUP BY DATE_FORMAT(o.createdDate, '%Y-%m')")
    List<ChartDataVM1> getTotalPriceInMonthOfYear2020();

    @Query("SELECT new application.model.viewmodel.chart.ChartDataVM1(DATE_FORMAT(o.createdDate, '%Y-%m-%d'),sum(o.price)) " +
            "FROM dbo_order o " +
            "WHERE o.createdDate BETWEEN '2020-01-01 0:0:0' AND '2020-12-31 16:26:39' " +
            "GROUP BY DATE_FORMAT(o.createdDate, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(o.createdDate, '%Y-%m-%d') ASC")
    List<ChartDataVM1> getTotalPriceInDayOfMonth();
}

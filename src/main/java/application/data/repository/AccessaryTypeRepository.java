package application.data.repository;

import application.data.entity.AccessaryType;
import application.model.viewmodel.chart.ChartDataVM;
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
}

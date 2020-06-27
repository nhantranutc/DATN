package application.data.repository;

import application.data.entity.AppointmentBook;
import application.model.viewmodel.chart.ChartDataVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentBookRepository extends JpaRepository<AppointmentBook, Integer> {

    @Query("SELECT b FROM dbo_appointment_book b WHERE b.appointmentDate = :createDate AND b.statusId = 1 AND UPPER(b.actionType) LIKE CONCAT('%',UPPER('Bao Duong'),'%') ")
    List<AppointmentBook> getListAllAppointmentBookByCreateDate1(@Param("createDate") Date createDate);

    @Query("SELECT b FROM dbo_appointment_book b WHERE b.appointmentDate = :createDate AND b.statusId = 1 AND UPPER(b.actionType) LIKE CONCAT('%',UPPER('Thay phu tung'),'%') ")
    List<AppointmentBook> getListAllAppointmentBookByCreateDate2(@Param("createDate") Date createDate);

    @Query("SELECT b FROM dbo_appointment_book b WHERE (:createDate IS NULL OR b.appointmentDate = :createDate)")
    Page<AppointmentBook> getListAllAppointmentBookByCreateDateWithPageable(Pageable pageable, @Param("createDate") Date createDate);

    @Query("SELECT new application.model.viewmodel.chart.ChartDataVM(DATE_FORMAT(o.appointmentDate, '%Y-%m-%d'),count(o.id)) " +
            "FROM dbo_appointment_book o " +
            "WHERE o.appointmentDate BETWEEN '2020-01-01 0:0:0' AND '2020-12-31 16:26:39' " +
            "GROUP BY DATE_FORMAT(o.appointmentDate, '%Y-%m-%d')")
    List<ChartDataVM> countAppointmentBookInDay();

    @Query("SELECT b FROM dbo_appointment_book b WHERE b.email = :email")
    AppointmentBook findAppointmentBookByEmail(@Param("email") String email);
}

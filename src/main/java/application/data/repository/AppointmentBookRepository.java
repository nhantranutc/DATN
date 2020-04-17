package application.data.repository;

import application.data.entity.AppointmentBook;
import application.data.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentBookRepository extends JpaRepository<AppointmentBook, Integer> {

    @Query("SELECT b FROM dbo_appointment_book b WHERE b.appointmentDate = :createDate")
    List<AppointmentBook> getListAllAppointmentBookByCreateDate(@Param("createDate") Date createDate);
}

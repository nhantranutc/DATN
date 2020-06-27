package application.data.service;

import application.data.entity.AppointmentBook;
import application.data.repository.AppointmentBookRepository;
import application.model.viewmodel.chart.ChartDataVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentBookSevice {

    private static final Logger logger = LogManager.getLogger(AccessaryTypeService.class);

    @Autowired
    private AppointmentBookRepository appointmentBookRepository;

    @Transactional
    public void addNewListAppointmentBooks(List<AppointmentBook> appointmentBook) {
        try {
            appointmentBookRepository.save(appointmentBook);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewAppointmentBook(AppointmentBook appointmentBook) {
        try {
            appointmentBookRepository.save(appointmentBook);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public AppointmentBook findOne(int id) {
        return appointmentBookRepository.findOne(id);
    }

    public boolean updateAppointmentBook(AppointmentBook appointmentBook) {
        try {
            appointmentBookRepository.save(appointmentBook);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<AppointmentBook> getListAllAppointmentBooks() {
        try {
            return appointmentBookRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<AppointmentBook> getListAllAppointmentBookByCreateDate1(Date createDate) {
        return appointmentBookRepository.getListAllAppointmentBookByCreateDate1(createDate);
    }

    public List<AppointmentBook> getListAllAppointmentBookByCreateDate2(Date createDate) {
        return appointmentBookRepository.getListAllAppointmentBookByCreateDate2(createDate);
    }

    public Page<AppointmentBook> getListAllAppointmentBookByCreateDateWithPageable(Pageable pageable, Date createDate) {
        return appointmentBookRepository.getListAllAppointmentBookByCreateDateWithPageable(pageable, createDate);
    }

    public List<ChartDataVM> countAppointmentBookInDay() {
        return appointmentBookRepository.countAppointmentBookInDay();
    }

    public AppointmentBook findAppointmentBookByEmail(String email) {
        return appointmentBookRepository.findAppointmentBookByEmail(email);
    }
}

package application.controller.api;

import application.data.entity.AppointmentBook;
import application.data.service.AppointmentBookSevice;
import application.data.service.VehicleService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/api/book")
public class AppointmentBookApiController {

    @Autowired
    private AppointmentBookSevice appointmentBookSevice;

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(value = "/create")
    public BaseApiResult create(@RequestBody BookDTO dto) {
        DataApiResult result = new DataApiResult();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<AppointmentBook> appointmentBookList = appointmentBookSevice.getListAllAppointmentBookByCreateDate(formatter.parse(dto.getAppointmentDate()));
            if(appointmentBookList.size() >= 2) {
                result.setSuccess(false);
                result.setMessage("Bạn vui lòng đặt hẹn vào ngày khác! Mỗi ngày chúng tôi chỉ nhận 2 đơn. Xin cảm ơn!");
            } else {
                AppointmentBook appointmentBook = new AppointmentBook();
                appointmentBook.setFullName(dto.getFullName());
                appointmentBook.setPhoneNumber(dto.getPhoneNumber());
                appointmentBook.setVehicleBrand(vehicleService.findOne(dto.getVehicleBrand()).getVehicleName());
                appointmentBook.setContent(dto.getContent());
                appointmentBook.setAppointmentDate(formatter.parse(dto.getAppointmentDate()));

                appointmentBookSevice.addNewAppointmentBook(appointmentBook);
                result.setMessage("Bạn đã đặt lịch thành công!");
                result.setSuccess(true);
            }
        } catch (ParseException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}

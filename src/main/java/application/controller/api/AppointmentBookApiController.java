package application.controller.api;

import application.data.entity.AppointmentBook;
import application.data.service.AppointmentBookSevice;
import application.data.service.StatusService;
import application.data.service.VehicleService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.BookDTO;
import application.model.dto.StatusDTO;
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

    @Autowired
    private StatusService statusService;

    @PostMapping(value = "/create")
    public BaseApiResult create(@RequestBody BookDTO dto) {
        DataApiResult result = new DataApiResult();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<AppointmentBook> appointmentBookList1 = appointmentBookSevice.getListAllAppointmentBookByCreateDate1(formatter.parse(dto.getAppointmentDate()));
            List<AppointmentBook> appointmentBookList2 = appointmentBookSevice.getListAllAppointmentBookByCreateDate2(formatter.parse(dto.getAppointmentDate()));
            if(appointmentBookList1.size() + ( appointmentBookList2.size() * 2 ) >= 5) {
                result.setSuccess(false);
                result.setMessage("Bạn vui lòng đặt hẹn vào ngày khác! Xin cảm ơn!");
            } else if(dto.getActionType() == 1 && (5 - (appointmentBookList1.size() + ( appointmentBookList2.size() * 2 ))) < 1) {
                result.setSuccess(false);
                result.setMessage("Bạn vui lòng đặt hẹn vào ngày khác! Xin cảm ơn!");
            } else if (dto.getActionType() == 2 && (5 - (appointmentBookList1.size() + ( appointmentBookList2.size() * 2 ))) < 2) {
                result.setSuccess(false);
                result.setMessage("Bạn vui lòng đặt hẹn vào ngày khác! Xin cảm ơn!");
            } else {
                AppointmentBook appointmentBook = new AppointmentBook();
                appointmentBook.setFullName(dto.getFullName());
                appointmentBook.setPhoneNumber(dto.getPhoneNumber());
                appointmentBook.setVehicleBrand(vehicleService.findOne(dto.getVehicleBrand()).getVehicleName());
                appointmentBook.setContent(dto.getContent());
                appointmentBook.setEmail(dto.getEmail());
                appointmentBook.setAppointmentDate(formatter.parse(dto.getAppointmentDate()));
                appointmentBook.setStatus(statusService.findOne(1));
                if(dto.getActionType() == 1) {
                    appointmentBook.setActionType("Bảo dưỡng");
                }
                if(dto.getActionType() == 2) {
                    appointmentBook.setActionType("Thay phụ tùng");
                }

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

    @PostMapping("accept")
    public BaseApiResult accept ( @RequestBody StatusDTO dto) {

        BaseApiResult result = new BaseApiResult();

        try {
            AppointmentBook appointmentBook = appointmentBookSevice.findOne(dto.getId());
            appointmentBook.setStatus(statusService.findOne(2));

            appointmentBookSevice.addNewAppointmentBook(appointmentBook);
            result.setSuccess(true);
            result.setMessage("Lịch hẹn này đã được xử lý. Khách hàng đã đến đúng hẹn!!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("remove")
    public BaseApiResult remove ( @RequestBody StatusDTO dto) {

        BaseApiResult result = new BaseApiResult();

        try {
            AppointmentBook appointmentBook = appointmentBookSevice.findOne(dto.getId());
            appointmentBook.setStatus(statusService.findOne(3));

            appointmentBookSevice.addNewAppointmentBook(appointmentBook);
            result.setSuccess(true);
            result.setMessage("Lịch hẹn này đã bị hủy!!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}
package application.controller.web;

import application.data.service.MailService;
import application.model.api.BaseApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class MailController {
    @Autowired
    MailService mailService;

    @GetMapping("/mail/{email}/{name}")
    public BaseApiResult sendMail(@PathVariable String email ,@PathVariable String name) {
        BaseApiResult result = new BaseApiResult();
        try {
            mailService.sendMail(email, getMailTest(name,"Bao Duong Xe"));
            result.setMessage("Gửi mail thành công");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    public String getMailTest(String name,String project_name){
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<body>\n" +
                "<p style=\"color: red; font-weight: bold\">Dear "+ name +",</p>\n" +
                "<p>" + project_name + "</p>\n" +
                "<p>Chúng tôi đã nhận được lịch hẹn của bạn.Mong bạn đến đúng lịch hẹn.Cảm ơn!</p>\n" +
                "</body>\n" +
                "</html>";
    }
}

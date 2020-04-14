package application.controller.api;

import application.data.entity.Services;
import application.data.service.ServicesService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ServiceDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/service")
public class ServiceApiController {

    private static final Logger logger = LogManager.getLogger(ServiceApiController.class);

    @Autowired
    private ServicesService servicesService;

    @PostMapping(value = "/create")
    public BaseApiResult create(@RequestBody ServiceDTO dto) {
        DataApiResult result = new DataApiResult();

        try {
            Services services = new Services();
            services.setServiceName(dto.getName());
            services.setServiceTypeId(1);
            services.setDescription(dto.getDescription());
            services.setContent(dto.getContent());
            services.setImage(dto.getImage());
            services.setIsDelete(0);

            servicesService.addNewServices(services);
            result.setMessage("Thêm thành công dịch vụ có mã: " + services.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/update/{id}")
    public BaseApiResult update(@PathVariable int id,
                                       @RequestBody ServiceDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            Services services = servicesService.findOne(id);
            services.setServiceName(dto.getName());
            services.setDescription(dto.getDescription());
            services.setContent(dto.getContent());
            services.setImage(dto.getImage());

            servicesService.addNewServices(services);
            result.setSuccess(true);
            result.setMessage("Cập nhật thành công");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping("/detail/{id}")
    public BaseApiResult detailMaterial(@PathVariable int id) {
        DataApiResult result= new DataApiResult();

        try {
            Services services = servicesService.findOne(id);
            if(services == null) {
                result.setSuccess(false);
                result.setMessage("Không thể tìm thấy dịch vụ!");
            } else {
                ServiceDTO dto = new ServiceDTO();
                dto.setId(services.getId());
                dto.setName(services.getServiceName());
                dto.setDescription(services.getDescription());
                dto.setContent(services.getContent());
                dto.setImage(services.getImage());

                result.setSuccess(true);
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;

    }
}

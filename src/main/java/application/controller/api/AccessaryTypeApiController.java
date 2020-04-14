package application.controller.api;

import application.data.entity.AccessaryType;
import application.data.service.AccessaryTypeService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.AccessaryTypeDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accessaryType")
public class AccessaryTypeApiController {

    private static final Logger logger = LogManager.getLogger(AccessaryTypeApiController.class);

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @PostMapping(value = "/create")
    public BaseApiResult create(@RequestBody AccessaryTypeDTO dto) {
        DataApiResult result = new DataApiResult();

        try {
            AccessaryType accessaryType = new AccessaryType();
            accessaryType.setName(dto.getName());
            accessaryType.setDescription(dto.getDescription());
            accessaryType.setDisplayOrder(0);
            accessaryType.setIsActive(0);
            accessaryType.setIsDelete(0);

            accessaryTypeService.addNewAccessaryType(accessaryType);
            result.setMessage("Thêm thành công loại phụ tùng có mã: " + accessaryType.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/update/{id}")
    public BaseApiResult update(@PathVariable int id,
                                @RequestBody AccessaryTypeDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            AccessaryType accessaryType = accessaryTypeService.findOne(id);
            accessaryType.setName(dto.getName());
            accessaryType.setDescription(dto.getDescription());

            accessaryTypeService.addNewAccessaryType(accessaryType);
            result.setMessage("Cập nhật thành công");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping("/detail/{id}")
    public BaseApiResult detail(@PathVariable int id) {
        DataApiResult result= new DataApiResult();

        try {
            AccessaryType accessaryType = accessaryTypeService.findOne(id);
            if(accessaryType == null) {
                result.setSuccess(false);
                result.setMessage("Không thể tìm thấy sản phẩm!");
            } else {
                AccessaryTypeDTO dto = new AccessaryTypeDTO();
                dto.setId(accessaryType.getId());
                dto.setName(accessaryType.getName());
                dto.setDescription(accessaryType.getDescription());

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

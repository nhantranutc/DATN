package application.controller.api;

import application.data.entity.Accessary;
import application.data.service.AccessaryService;
import application.data.service.AccessaryTypeService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.AccessaryDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accessary")
public class AccessaryApiController {

    private static final Logger logger = LogManager.getLogger(AccessaryApiController.class);

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @Autowired
    private AccessaryService accessaryService;

    @PostMapping(value = "/create")
    public BaseApiResult create(@RequestBody AccessaryDTO dto) {
        DataApiResult result = new DataApiResult();

        try {
            Accessary accessary = new Accessary();
            accessary.setName(dto.getName());
            accessary.setAccessaryType(accessaryTypeService.findOne(dto.getAccessaryTypeId()));
            accessary.setPrice(dto.getPrice());
            accessary.setOriginalPrice(dto.getOriginalPrice());
            accessary.setPromotionPrice(dto.getPromotionPrice());
            accessary.setQuantity(dto.getQuantity());
            accessary.setWarranty(dto.getWarranty());
            accessary.setImage(dto.getImage());
            accessary.setOrigin(dto.getOrigin());
            accessary.setIsVAT(0);
            accessary.setDescription(dto.getDescription());
            accessary.setIsDelete(0);

            accessaryService.addNewAccessary(accessary);
            result.setMessage("Thêm thành công sản phẩm có mã: " + accessary.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/update/{id}")
    public BaseApiResult update(@PathVariable int id,
                                       @RequestBody AccessaryDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            Accessary accessary = accessaryService.findOne(id);
            accessary.setName(dto.getName());
            accessary.setAccessaryType(accessaryTypeService.findOne(dto.getAccessaryTypeId()));
            accessary.setPrice(dto.getPrice());
            accessary.setOriginalPrice(dto.getOriginalPrice());
            accessary.setPromotionPrice(dto.getPromotionPrice());
            accessary.setQuantity(dto.getQuantity());
            accessary.setWarranty(dto.getWarranty());
            accessary.setImage(dto.getImage());
            accessary.setOrigin(dto.getOrigin());
            accessary.setDescription(dto.getDescription());

            accessaryService.addNewAccessary(accessary);
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
            Accessary accessary = accessaryService.findOne(id);
            if(accessary == null) {
                result.setSuccess(false);
                result.setMessage("Không thể tìm thấy sản phẩm!");
            } else {
                AccessaryDTO dto = new AccessaryDTO();
                dto.setId(accessary.getId());
                if(accessary.getAccessaryType() != null) {
                    dto.setAccessaryTypeId(accessary.getAccessaryType().getId());
                }
                dto.setName(accessary.getName());
                dto.setPrice(accessary.getPrice());
                dto.setOriginalPrice(accessary.getOriginalPrice());
                dto.setPromotionPrice(accessary.getPromotionPrice());
                dto.setQuantity(accessary.getQuantity());
                dto.setWarranty(accessary.getWarranty());
                dto.setImage(accessary.getImage());
                dto.setOrigin(accessary.getOrigin());
                dto.setDescription(accessary.getDescription());

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

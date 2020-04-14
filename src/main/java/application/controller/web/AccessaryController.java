package application.controller.web;

import application.constant.FormatPrice;
import application.data.entity.Accessary;
import application.data.entity.AccessaryType;
import application.data.service.AccessaryService;
import application.data.service.AccessaryTypeService;
import application.model.viewmodel.accessary.AccessaryLanding;
import application.model.viewmodel.accessary.AccessaryVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/accessary")
public class AccessaryController extends BaseController {

    @Autowired
    private AccessaryService accessaryService;

    @Autowired
    private AccessaryTypeService accessaryTypeService;

    @GetMapping(path = "")
    public String product(Model model,
                          @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName,
                          @RequestParam(name = "accessaryTypeId", required = false) Integer accessaryTypeId,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(name = "startPrice", required = false) String startPrice,
                          @RequestParam(name = "endPrice", required = false) String endPrice,
                          @RequestParam(name = "lessPrice", required = false) String lessPrice,
                          @RequestParam(name = "greatPrice", required = false) String greatPrice,
                          @RequestParam(name = "sortByPrice", required = false) String sort) {
        AccessaryLanding vm = new AccessaryLanding();

        Sort sortable = new Sort(Sort.Direction.ASC, "id");
        if(sort != null) {
            if(sort.equals("ASC")) {
                sortable = new Sort(Sort.Direction.ASC, "price");
            }else {
                sortable = new Sort(Sort.Direction.DESC, "price");
            }
        }

        Pageable pageable = new PageRequest(page, size, sortable);
        Page<Accessary> accessaryPage = null;

        if(accessaryTypeId != null) {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, accessaryTypeId, null);
            AccessaryType accessaryType = accessaryTypeService.findOne(accessaryTypeId);
            vm.setKeyWord("Danh Mục: " + accessaryType.getName());
        } else if(accessarytName.getName() != null && !accessarytName.getName().isEmpty()) {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, null, accessarytName.getName().trim());
            vm.setKeyWord("Kết Quả Từ Khóa: " + accessarytName.getName());
        } else if(startPrice != null && endPrice != null) {
            accessaryPage = accessaryService.filterListAccessaryBetweenStartPriceAndEndPrice(pageable, Double.valueOf(startPrice), Double.valueOf(endPrice));
        } else if( lessPrice != null ) {
            accessaryPage = accessaryService.filterListAccessaryLessThanCurrentPrice(pageable, Double.valueOf(lessPrice));
        } else if( greatPrice != null ) {
            accessaryPage = accessaryService.filterListProductGreatThanCurrentPrice(pageable, Double.valueOf(greatPrice));
        } else {
            accessaryPage = accessaryService.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, null, null);
            vm.setKeyWord("Tất cả sản phẩm ");
        }

        List<AccessaryVM> accessaryVMS = new ArrayList<>();
        for(Accessary accessary : accessaryPage.getContent()) {
            AccessaryVM accessaryVM = new AccessaryVM();

            if(accessary.getAccessaryType() == null) {
                accessaryVM.setAccessaryTypeName("Không xác định!");
            } else {
                accessaryVM.setAccessaryTypeName(accessary.getAccessaryType().getName());
            }
            accessaryVM.setId(accessary.getId());
            accessaryVM.setDescription(accessary.getDescription());
            accessaryVM.setName(accessary.getName());
            accessaryVM.setPrice(FormatPrice.formatPrice(accessary.getPrice()));
            accessaryVM.setWarranty(accessary.getWarranty());
            accessaryVM.setImage(accessary.getImage());
            accessaryVM.setOrigin(accessary.getOrigin());

            accessaryVMS.add(accessaryVM);
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setAccessaryVMS(accessaryVMS);
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        if(accessaryVMS.size() == 0) {
            vm.setKeyWord("Không có sản phẩm nào! ");
        }

        model.addAttribute("vm", vm);
        model.addAttribute("page", accessaryPage);

        return "accessary";
    }
}

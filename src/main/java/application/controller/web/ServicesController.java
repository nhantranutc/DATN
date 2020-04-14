package application.controller.web;

import application.data.entity.News;
import application.data.entity.Services;
import application.data.service.ServicesService;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.services.HomeServicesVM;
import application.model.viewmodel.services.ServicesVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/services")
public class ServicesController extends BaseController {
    @Autowired
    private ServicesService servicesService;

    @GetMapping("")
    public String service(Model model,
                          @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "4") Integer size) {
        HomeServicesVM vm = new HomeServicesVM();
        Pageable pageable = new PageRequest(page, size);

        Page<Services> listAllServices = servicesService.getListAllServices(pageable);
        List<ServicesVM> servicesVMList = new ArrayList<>();
        for(Services services : listAllServices) {
            ServicesVM servicesVM = new ServicesVM();
            servicesVM.setId(services.getId());
            servicesVM.setContent(services.getContent());
            servicesVM.setImage(services.getImage());
            servicesVM.setDescription(services.getDescription());
            servicesVM.setServiceName(services.getServiceName());

            servicesVMList.add(servicesVM);
        }

        List<Services> servicesList = servicesService.getListServices();
        List<ServicesVM> servicesVMList1 = new ArrayList<>();
        for(Services services1 : servicesList) {
            ServicesVM servicesVM1 = new ServicesVM();
            servicesVM1.setId(services1.getId());
            servicesVM1.setContent(services1.getContent());
            servicesVM1.setImage(services1.getImage());
            servicesVM1.setDescription(services1.getDescription());
            servicesVM1.setServiceName(services1.getServiceName());

            servicesVMList1.add(servicesVM1);
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setServicesVMS(servicesVMList);
        vm.setServicesVMList(servicesVMList1);
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        model.addAttribute("vm", vm);
        model.addAttribute("page", listAllServices);

        return "services";

    }

    @GetMapping("detail/{servicesId}")
    public String servicesDetail(Model model, @PathVariable int servicesId,
                                 @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName) {
        HomeServicesVM vm = new HomeServicesVM();

        Services services = servicesService.findOne(servicesId);

        ServicesVM servicesVM = new ServicesVM();

        servicesVM.setServiceName(services.getServiceName());
        servicesVM.setContent(services.getContent());
        servicesVM.setDescription(services.getDescription());
        servicesVM.setImage(services.getImage());

        List<Services> servicesList = servicesService.getListServicesHot(servicesId);
        List<ServicesVM> servicesVMList = new ArrayList<>();

        for (Services services1 : servicesList){
            ServicesVM servicesVM1 = new ServicesVM();
            servicesVM1.setId(services1.getId());
            servicesVM1.setServiceName(services1.getServiceName());
            servicesVM1.setDescription(services1.getDescription());
            servicesVM1.setImage(services1.getImage());
            servicesVM1.setContent(services1.getContent());
            servicesVMList.add(servicesVM1);
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setServicesVM(servicesVM);
        vm.setServicesVMS(servicesVMList);
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        model.addAttribute("vm", vm);

        return "services-detail";
    }

}

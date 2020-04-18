package application.controller.web;

import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.home.HomeLanding;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/about")
public class AboutController extends BaseController{

    @GetMapping("")
    public String about(Model model,
                        @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName) {
        HomeLanding vm = new HomeLanding();
        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        model.addAttribute("vm", vm);

        return "about";
    }
}

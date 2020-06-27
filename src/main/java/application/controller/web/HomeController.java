package application.controller.web;

import application.constant.FormatDate;
import application.constant.FormatPrice;
import application.data.entity.Accessary;
import application.data.entity.News;
import application.data.entity.Services;
import application.data.service.AccessaryService;
import application.data.service.NewsService;
import application.data.service.ServicesService;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.home.HomeLanding;
import application.model.viewmodel.news.NewsVM;
import application.model.viewmodel.services.ServicesVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AccessaryService accessaryService;

    @GetMapping("")
    public String home(Model model,
                       @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName) throws ParseException {
        HomeLanding vm = new HomeLanding();
        List<Services> servicesList = servicesService.getListAllServices();
        List<ServicesVM> servicesVMList = new ArrayList<>();
        for (Services services : servicesList) {
            ServicesVM servicesVM = new ServicesVM();
            servicesVM.setId(services.getId());
            servicesVM.setServiceName(services.getServiceName());
            servicesVM.setDescription(services.getDescription());
            servicesVM.setImage(services.getImage());
            servicesVM.setContent(services.getContent());
            servicesVMList.add(servicesVM);
        }

        List<News> newsList = newsService.getListAllNews();
        List<NewsVM> newsVMS = new ArrayList<>();
        for (News news : newsList) {
            NewsVM newsVM = new NewsVM();
            newsVM.setNewsId(news.getId());
            newsVM.setTitle(news.getTitle());
            newsVM.setContent(news.getContent());
            newsVM.setMainImage(news.getMainImage());
            newsVM.setShortDesc(news.getShortDesc());
            newsVM.setAuthor(news.getAuthor());
            newsVM.setCreateDate(FormatDate.formatDate(news.getCreateDate()));
            newsVMS.add(newsVM);
        }

        List<Accessary> accessaryList = accessaryService.getListAccessarybyName1();
        List<AccessaryVM> accessaryVMList = new ArrayList<>();
        for(Accessary accessary : accessaryList) {
            AccessaryVM accessaryVM = new AccessaryVM();
            accessaryVM.setId(accessary.getId());
            accessaryVM.setName(accessary.getName());
            accessaryVM.setImage(accessary.getImage());
            accessaryVM.setDescription(accessary.getDescription());
            accessaryVM.setPrice(FormatPrice.formatPrice(accessary.getPrice()));
            accessaryVM.setOrigin(accessary.getOrigin());

            accessaryVMList.add(accessaryVM);
        }

        List<Accessary> accessaryList2 = accessaryService.getListAccessarybyName2();
        List<AccessaryVM> accessaryVMS2 = new ArrayList<>();
        for (Accessary accessary1 : accessaryList2) {
            AccessaryVM accessaryVM1 = new AccessaryVM();
            accessaryVM1.setId(accessary1.getId());
            accessaryVM1.setName(accessary1.getName());
            accessaryVM1.setImage(accessary1.getImage());
            accessaryVM1.setDescription(accessary1.getDescription());
            accessaryVM1.setPrice(String.valueOf(accessary1.getPrice()));
            accessaryVM1.setOrigin(accessary1.getOrigin());
            accessaryVMS2.add(accessaryVM1);
        }

        vm.setServicesVMS(servicesVMList);
        vm.setNewsVMS(newsVMS);
        vm.setAccessaryVMS1(accessaryVMList);
        vm.setAccessaryVMS2(accessaryVMS2);
        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        model.addAttribute("vm", vm);
        return "home";
    }
}

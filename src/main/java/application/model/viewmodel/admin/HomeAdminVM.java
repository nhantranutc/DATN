package application.model.viewmodel.admin;

import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.news.NewsVM;
import application.model.viewmodel.services.ServicesVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeAdminVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<AccessaryVM> accessaryVMList;
    private List<ServicesVM> servicesVMList;
    private List<NewsVM> newsVMS;
    private List<AccessaryTypeVM> accessaryTypeVMList;
    private int roleId;
}

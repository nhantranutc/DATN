package application.model.viewmodel.home;

import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import application.model.viewmodel.news.NewsVM;
import application.model.viewmodel.services.ServicesVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeLanding {
    private List<ServicesVM> servicesVMS;
    private List<NewsVM> newsVMS;
    private List<AccessaryVM> accessaryVMS1;
    private List<AccessaryVM> accessaryVMS2;
    private LayoutHeaderVM layoutHeaderVM;
    private List<AccessaryTypeVM> accessaryTypeVMS;
}

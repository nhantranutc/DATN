package application.model.viewmodel.news;

import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeNewsVM {

    private List<NewsVM> newsVMList;
    private List<NewsVM> hotNewsVMList;
    private LayoutHeaderVM layoutHeaderVM;
    private List<AccessaryTypeVM> accessaryTypeVMS;
    private NewsVM newsVM;
}

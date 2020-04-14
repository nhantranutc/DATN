package application.model.viewmodel.accessary;

import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccessaryLanding {
    private LayoutHeaderVM layoutHeaderVM;
    private List<AccessaryVM> accessaryVMS;
    private List<AccessaryTypeVM> accessaryTypeVMS;
    private String keyWord;
}

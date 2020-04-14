package application.model.viewmodel.services;

import application.data.entity.Services;
import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeServicesVM {
    private List<ServicesVM> servicesVMS;
    private List<ServicesVM> servicesVMList;
    private LayoutHeaderVM layoutHeaderVM;
    private List<AccessaryTypeVM> accessaryTypeVMS;
    private ServicesVM servicesVM;
}

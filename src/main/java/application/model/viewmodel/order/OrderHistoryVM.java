package application.model.viewmodel.order;

import application.model.viewmodel.accessaryType.AccessaryTypeVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import application.model.viewmodel.mail.MailVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderHistoryVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private LayoutHeaderVM layoutHeaderVM;
    private List<AccessaryTypeVM> accessaryTypeVMS;
    private List<OrderVM> orderVMList;
    private int roleId;
    private List<MailVM> sendMailList;
}

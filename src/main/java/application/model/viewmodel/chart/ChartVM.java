package application.model.viewmodel.chart;

import application.model.viewmodel.common.LayoutHeaderAdminVM;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartVM {

    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<ChartDataVM> chartAccessaryTypeVMS;
    private int roleId;
}

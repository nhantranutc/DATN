package application.data.service;

import application.data.entity.AccessaryType;
import application.data.repository.AccessaryTypeRepository;
import application.model.viewmodel.chart.ChartDataVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessaryTypeService {

    private static final Logger logger = LogManager.getLogger(AccessaryTypeService.class);

    @Autowired
    private AccessaryTypeRepository accessaryTypeRepository;

    @Transactional
    public void addNewListAccessaryTypes(List<AccessaryType> accessaryTypes) {
        try {
            accessaryTypeRepository.save(accessaryTypes);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewAccessaryType(AccessaryType accessaryType) {
        try {
            accessaryTypeRepository.save(accessaryType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public AccessaryType findOne(int id) {
        return accessaryTypeRepository.findOne(id);
    }

    public boolean updateAccessaryType(AccessaryType accessaryType) {
        try {
            accessaryTypeRepository.save(accessaryType);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<AccessaryType> getListAllAccessaryTypes() {
        try {
            return accessaryTypeRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Page<AccessaryType> getListAllAccessaryTypeByContaining(Pageable pageable) {
        return accessaryTypeRepository.getListAllAccessaryTypeByContaining(pageable);
    }

    public List<ChartDataVM> getAllAccessaryType() {
        return accessaryTypeRepository.getAllAccessaryType();
    }

}

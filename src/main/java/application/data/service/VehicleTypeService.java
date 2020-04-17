package application.data.service;

import application.data.entity.AccessaryType;
import application.data.entity.VehicleType;
import application.data.repository.AccessaryTypeRepository;
import application.data.repository.VehicleTypeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleTypeService {

    private static final Logger logger = LogManager.getLogger(VehicleTypeService.class);

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Transactional
    public void addNewListVehicleTypes(List<VehicleType> vehicleType) {
        try {
            vehicleTypeRepository.save(vehicleType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewVehicleType(VehicleType vehicleType) {
        try {
            vehicleTypeRepository.save(vehicleType);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public VehicleType findOne(int id) {
        return vehicleTypeRepository.findOne(id);
    }

    public boolean updateVehicleType(VehicleType vehicleType) {
        try {
            vehicleTypeRepository.save(vehicleType);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<VehicleType> getListAllVehicleTypes() {
        try {
            return vehicleTypeRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}

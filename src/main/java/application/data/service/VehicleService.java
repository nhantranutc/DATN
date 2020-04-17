package application.data.service;

import application.data.entity.Vehicle;
import application.data.repository.VehicleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private static final Logger logger = LogManager.getLogger(VehicleService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public void addNewListVehicles(List<Vehicle> vehicleList) {
        try {
            vehicleRepository.save(vehicleList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewVehicle(Vehicle vehicle) {
        try {
            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Vehicle findOne(int id) {
        return vehicleRepository.findOne(id);
    }

    public boolean updateVehicle(Vehicle vehicle) {
        try {
            vehicleRepository.save(vehicle);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Vehicle> getListAllVehicles() {
        try {
            return vehicleRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}

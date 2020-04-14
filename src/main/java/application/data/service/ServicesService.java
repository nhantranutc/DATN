package application.data.service;

import application.data.entity.News;
import application.data.entity.Services;
import application.data.repository.ServiceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesService  {
    private static final Logger logger = LogManager.getLogger(ServicesService.class);

        @Autowired
        private ServiceRepository serviceRepository;

        public List<Services> getListAllServices() {
            try {
                return serviceRepository.findAll();
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ArrayList<>();
            }
        }

        public Services findOne(int id) {
            return serviceRepository.findOne(id);
        }

        public void addNewServices(Services services) {
            serviceRepository.save(services);
        }

        public List<Services> getListServicesHot(int sevicesId) {
            return serviceRepository.getListServicesHot(sevicesId);
        }

        public Page<Services> getListAllServices(Pageable pageable) {
            return serviceRepository.getListAllServices(pageable);
        }

        public List<Services> getListServices() {
            return serviceRepository.getListServices();
        }

        public Page<Services> getListAllServicesByServiceNameContaining(Pageable pageable, String serviceName) {
            return serviceRepository.getListAllServicesByServiceNameContaining(pageable, serviceName);
        }

}

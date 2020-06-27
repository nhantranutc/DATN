package application.data.service;

import application.data.entity.Status;
import application.data.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status findOne(int statusId) {
        return statusRepository.findOne(statusId);
    }
}

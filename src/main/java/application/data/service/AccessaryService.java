package application.data.service;

import application.data.entity.Accessary;
import application.data.repository.AccessaryRepository;
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
public class AccessaryService {

    private static final Logger logger = LogManager.getLogger(NewsService.class);

    @Autowired
    private AccessaryRepository accessaryRepository;

    public void addNewAccessary(Accessary accessary) {
        accessaryRepository.save(accessary);
    }

    @Transactional
    public void addNewListAccessaries(List<Accessary> accessaryList) {
        accessaryRepository.save(accessaryList);
    }

    public Accessary findOne(int id) {
        return accessaryRepository.findOne(id);
    }


    public boolean updateAccessary(Accessary accessary) {
        try {
            accessaryRepository.save(accessary);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteAccessary(int id) {
        try {
            accessaryRepository.delete(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Accessary> getListAllAccessaries() {
        try {
            return accessaryRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Page<Accessary> getListAccessaryByAccessaryTypeOrAccessaryNameContaining(Pageable pageable, Integer accessaryTypeId, String accessarytName){
        return accessaryRepository.getListAccessaryByAccessaryTypeOrAccessaryNameContaining(pageable, accessaryTypeId, accessarytName);
    }

    public Page<Accessary> filterListAccessaryBetweenStartPriceAndEndPrice(Pageable pageable, Double startPrice, Double endPrice) {
        return accessaryRepository.filterListAccessaryBetweenStartPriceAndEndPrice(pageable, startPrice, endPrice);
    }

    public Page<Accessary> filterListAccessaryLessThanCurrentPrice(Pageable pageable, Double currentPrice) {
        return accessaryRepository.filterListAccessaryLessThanCurrentPrice(pageable, currentPrice);
    }

    public Page<Accessary> filterListProductGreatThanCurrentPrice(Pageable pageable, Double currentPrice) {
        return accessaryRepository.filterListProductGreatThanCurrentPrice(pageable, currentPrice);
    }

    public List<Accessary> getListAccessarybyName1() {
        return accessaryRepository.getListAccessarybyName1();
    }

    public List<Accessary> getListAccessarybyName2() {
        return accessaryRepository.getListAccessarybyName2();
    }
}

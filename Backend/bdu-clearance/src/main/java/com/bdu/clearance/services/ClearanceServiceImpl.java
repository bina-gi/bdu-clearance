package com.bdu.clearance.services;

import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.repositories.ClearanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClearanceServiceImpl implements ClearanceService{

    private Long nextId=1L;
    @Autowired
    private ClearanceRepository clearanceRepository;

    @Override
    public List<Clearance> getAllClearances() {
        return clearanceRepository.findAll();
    }

    @Override
    public void createClearance(Clearance clearance) {
        clearance.setId(nextId++);
        clearanceRepository.save(clearance);
    }

    @Override
    public String deleteClearance(Long clearanceId) {
        List<Clearance> allClearances=clearanceRepository.findAll();
        Clearance clearance=allClearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        clearanceRepository.delete(clearance);
        return "Clearance with ID: "+ clearanceId +" deleted Successfully!";
    }

    @Override
    public Clearance updateLibraryStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> allClearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=allClearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setLibraryStatus(clearance.getLibraryStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }
     @Override
    public Clearance updateFinanceStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setFinanceStatus(clearance.getFinanceStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }@Override
    public Clearance updateRegistrarStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setRegistrarStatus(clearance.getRegistrarStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }@Override
    public Clearance updateCafteriaStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setCafteriaStatus(clearance.getCafteriaStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }@Override
    public Clearance updateDormitoryStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setDormitoryStatus(clearance.getDormitoryStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }@Override
    public Clearance updateFacultyStoreStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setFacultyStoreStatus(clearance.getFacultyStoreStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }@Override
    public Clearance updateAdvisorStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setAdvisorStatus(clearance.getAdvisorStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }@Override
    public Clearance updateFacultyStatus(Clearance clearance, Long clearanceId) {
        List<Clearance> clearances=clearanceRepository.findAll();

        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
                .findFirst();
        if(optionalClearance.isPresent()){
            Clearance existingClearance=optionalClearance.get();
            existingClearance.setFacultyStatus(clearance.getFacultyStatus());
            Clearance savedClearance=clearanceRepository.save(existingClearance);
            return savedClearance;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
        }

     }

}

package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.ClearanceMapper;
import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.repositories.ClearanceRepository;
import com.bdu.clearance.services.ClearanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClearanceServiceImpl implements ClearanceService {


    private final ClearanceRepository clearanceRepository;
    private final ClearanceMapper clearanceMapper;

    @Override
    public void createClearance(StudentClearanceRequestDto clearance) {
        Clearance newClearance=clearanceMapper.toEntity(clearance);
        clearanceRepository.save(newClearance);
    }

    @Override
    public List<ClearanceResponseDto> getAllClearances() {
        List<Clearance> allClearances=clearanceRepository.findAll();
        if (allClearances.isEmpty()){
            throw new APIException("There is no clearance found");
        }
        List<ClearanceResponseDto> responseClearances=clearanceMapper.toResponse(allClearances);
        return responseClearances;
    }

    @Override
    public List<ClearanceResponseDto> getClearanceByStudentId(String studentId) {
        List<ClearanceResponseDto> clearances=clearanceRepository.findByStudentId(studentId);
        if(clearances.isEmpty()){
            throw new APIException("There is no clearance");
        }
        return clearances;

    }


    @Override
    public void deleteClearance(Long clearanceId) {
        Optional<Clearance> optionalClearance=clearanceRepository.findById(clearanceId);
        if(optionalClearance.isEmpty()){
            throw new APIException("Student not found");
        }
        Clearance existingClearance=optionalClearance.get();

        clearanceRepository.delete(existingClearance);
    }
//Update Sector impls
//    @Override
//    public Clearance updateLibrary(ClearanceStatus status , Long clearanceId) {
//        List<Clearance> allClearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=allClearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setLibrary(status);
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }
//     @Override
//    public Clearance updateFinanceStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setFinanceStatus(clearance.getFinanceStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }@Override
//    public Clearance updateRegistrarStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setRegistrarStatus(clearance.getRegistrarStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }@Override
//    public Clearance updateCafteriaStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setCafteriaStatus(clearance.getCafteriaStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }@Override
//    public Clearance updateDormitoryStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setDormitoryStatus(clearance.getDormitoryStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }@Override
//    public Clearance updateFacultyStoreStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setFacultyStoreStatus(clearance.getFacultyStoreStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }@Override
//    public Clearance updateAdvisorStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setAdvisorStatus(clearance.getAdvisorStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }@Override
//    public Clearance updateFacultyStatus(Clearance clearance, Long clearanceId) {
//        List<Clearance> clearances=clearanceRepository.findAll();
//
//        Optional<Clearance> optionalClearance=clearances.stream().filter(c->c.getId().equals(clearanceId))
//                .findFirst();
//        if(optionalClearance.isPresent()){
//            Clearance existingClearance=optionalClearance.get();
//            existingClearance.setFacultyStatus(clearance.getFacultyStatus());
//            Clearance savedClearance=clearanceRepository.save(existingClearance);
//            return savedClearance;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clearance Not_found" );
//        }
//
//     }

}

package com.bdu.clearance.services;

import com.bdu.clearance.models.Clearance;

import java.util.List;

public interface ClearanceService {
    List<Clearance> getAllClearances();
    void requestClearance(Clearance clearance);
    String deleteClearance(Long clearanceId);
    Clearance updateLibraryStatus(Clearance clearance,Long clearanceId);
    Clearance updateFinanceStatus(Clearance clearance,Long clearanceId);
    Clearance updateRegistrarStatus(Clearance clearance,Long clearanceId);
    Clearance updateCafteriaStatus(Clearance clearance,Long clearanceId);
    Clearance updateDormitoryStatus(Clearance clearance,Long clearanceId);
    Clearance updateFacultyStoreStatus(Clearance clearance,Long clearanceId);
    Clearance updateAdvisorStatus(Clearance clearance,Long clearanceId);
    Clearance updateFacultyStatus(Clearance clearance,Long clearanceId);

}

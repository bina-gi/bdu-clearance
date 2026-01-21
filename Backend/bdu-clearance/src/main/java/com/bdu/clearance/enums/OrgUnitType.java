package com.bdu.clearance.enums;

public enum OrgUnitType {
    LIBRARY("LIBRARY"),
    STORE("STORE"),
    DORMITORY("DORMITORY"),
    CAFETERIA("CAFETERIA"),
    REGISTRAR("REGISTRAR"),
    FACULTY("FACULTY"),
    DEPARTMENT("DEPARTMENT"),
    CAMPUS("CAMPUS");

    private final String value;

    OrgUnitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

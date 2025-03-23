package com.example.medmate;

public class Medicine {
    private String medicinesID;
    private String name;
    private String daysFrequency;
    private String dosage;
    private String lowStockReminder;
    private String medicineStock;
    private String medicineType;
    private String timeSelection;
    private boolean taken;

    public Medicine(String medicinesID, int name, String daysFrequency, String dosage,
                    boolean lowStockReminder, String medicineStock, String medicineType,
                    String timeSelection, String taken) {
        this.medicinesID = medicinesID;
        this.name = String.valueOf(name);
        this.daysFrequency = daysFrequency;
        this.dosage = dosage;
        this.lowStockReminder = String.valueOf(lowStockReminder);
        this.medicineStock = medicineStock;
        this.medicineType = medicineType;
        this.timeSelection = timeSelection;
        this.taken = Boolean.parseBoolean(taken);
    }

    public String getMedicinesID() {
        return medicinesID;
    }

    public void setMedicinesID(String medicinesID) {
        this.medicinesID = medicinesID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDaysFrequency() {
        return daysFrequency;
    }

    public void setDaysFrequency(String daysFrequency) {
        this.daysFrequency = daysFrequency;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getLowStockReminder() {
        return lowStockReminder;
    }

    public void setLowStockReminder(String lowStockReminder) {
        this.lowStockReminder = lowStockReminder;
    }

    public String getMedicineStock() {
        return medicineStock;
    }

    public void setMedicineStock(String medicineStock) {
        this.medicineStock = medicineStock;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getTimeSelection() {
        return timeSelection;
    }

    public void setTimeSelection(String timeSelection) {
        this.timeSelection = timeSelection;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}

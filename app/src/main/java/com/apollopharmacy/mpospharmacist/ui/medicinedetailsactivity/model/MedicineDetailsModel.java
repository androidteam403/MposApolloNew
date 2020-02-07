package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.model;

public class MedicineDetailsModel {
    private String medicineMrp;
    private String medicineQty;
    private String medicineTotal;
    private String medicineTax;

    public MedicineDetailsModel(String medicineMrp, String medicineQty, String medicineTotal, String medicineTax) {
        this.medicineMrp = medicineMrp;
        this.medicineQty = medicineQty;
        this.medicineTotal = medicineTotal;
        this.medicineTax = medicineTax;
    }

    public String getMedicineMrp() {
        return medicineMrp;
    }

    public void setMedicineMrp(String medicineMrp) {
        this.medicineMrp = medicineMrp;
    }

    public String getMedicineQty() {
        return medicineQty;
    }

    public void setMedicineQty(String medicineQty) {
        this.medicineQty = medicineQty;
    }

    public String getMedicineTotal() {
        return medicineTotal;
    }

    public void setMedicineTotal(String medicineTotal) {
        this.medicineTotal = medicineTotal;
    }

    public String getMedicineTax() {
        return medicineTax;
    }

    public void setMedicineTax(String medicineTax) {
        this.medicineTax = medicineTax;
    }
}

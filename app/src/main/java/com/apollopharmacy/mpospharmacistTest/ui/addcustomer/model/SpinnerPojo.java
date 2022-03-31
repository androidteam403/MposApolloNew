package com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model;

public class SpinnerPojo {
    String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }

    public static class City {
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return city;
        }
    }

    public static class State {
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return state;
        }
    }

    public static class District {
        private String district;

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        @Override
        public String toString() {
            return district;
        }
    }

    public static class MaritalStatus {
        private String maritalStatus;

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        @Override
        public String toString() {
            return maritalStatus;
        }
    }
}

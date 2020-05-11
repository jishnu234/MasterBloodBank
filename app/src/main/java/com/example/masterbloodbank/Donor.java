package com.example.masterbloodbank;

public class Donor {

    String name;
    String age;
    String phone;
    String blood_group;
    String district;

    public Donor(String name, String age, String phone, String blood_group, String district) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.blood_group = blood_group;
        this.district = district;
    }

    public Donor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

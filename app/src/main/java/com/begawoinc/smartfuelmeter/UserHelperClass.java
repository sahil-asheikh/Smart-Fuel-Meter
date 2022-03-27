package com.begawoinc.smartfuelmeter;

public class UserHelperClass {

    String userName,email,phoneNo,password;
    double petrolFilled;
    int counter;

    public UserHelperClass() {

    }

    public UserHelperClass(String userName, String email, String phoneNo, String password,double petrolFilled,int counter) {
        this.userName = userName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.petrolFilled = petrolFilled;
        this.counter =  counter;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPetrolFilled() {
        return petrolFilled;
    }

    public void setPetrolFilled(double petrolFilled) {
        this.petrolFilled = petrolFilled;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}

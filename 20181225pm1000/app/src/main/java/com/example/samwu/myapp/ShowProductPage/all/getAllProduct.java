package com.example.samwu.myapp.ShowProductPage.all;

public class getAllProduct {
    String producttitle;
    String status;
    String email;
    String details;
    String a;
    String uploadtime;

    public getAllProduct(){}

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public getAllProduct(String producttitle, String status, String email, String details, String a, String uploadtime) {
        this.producttitle = producttitle;
        this.status = status;
        this.email = email;
        this.details = details;
        this.a = a;
        this.uploadtime = uploadtime;
    }
}

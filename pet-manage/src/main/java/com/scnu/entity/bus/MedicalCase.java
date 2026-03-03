package com.scnu.entity.bus;

import lombok.Data;

@Data
public class MedicalCase {
    private Integer id;
    private String registerId;
    private String name;
    private String gender;
    private String age;
    private String phone;
    private String visitDate;
    private String doctorId;
    private String medicalInfo;
    private String diagnosis;
    private String adviceInfo;

    private String docName;
    private String userId;
}

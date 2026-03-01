package com.xzj.entity.bus;

import lombok.Data;

@Data
public class RegisterInfo {
    private Integer id;
    private String clinicId;
    private String userId;
    private String docId;
    private String status;
    private String time;
    private Integer receiveId;
    private String receiveTime;


    private String clinicName;
    private String userName;
    private String docName;
    private String receiveName;
}

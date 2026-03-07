package com.scnu.entity.bus;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RegisterInfo {

    // ===== 基本信息 =====
    private Integer id;

    private String orderNo;     // 订单号

    private Integer clinicId;   // 诊所服务ID

    private Integer userId;     // 用户ID

    private Integer docId;      // 服务人员ID

    private BigDecimal price;   // 服务价格


    // ===== 状态信息 =====
    private String status;      // 订单状态


    // ===== 时间信息 =====
    private String time;        // 预约时间

    private String createTime;  // 创建时间

    private String payTime;     // 支付时间

    private Integer receiveId;  // 接诊人ID

    private String receiveTime; // 接诊时间

    private String startTime;   // 服务开始时间

    private String finishTime;  // 服务完成时间


    // ===== 联表查询字段（不在数据库） =====
    private String clinicName;
    private String userName;
    private String docName;
    private String receiveName;
}
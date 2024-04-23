package com.ctc.library.control.dto;

import lombok.Data;

@Data
public class CirculationDTO {
    Integer circulationId;//流通ID (PK)
    String bookId;//图书ID(FK)
    String unitFrom;//所属单位
    String unitTo;//流通账户ID（FK）（流入单位）
    String circulationTime;//流通时间
    String returnTime;//归还时间
    String reason;//流通理由
    String userName;//申请人
    String phone;//申请人联系方式
    String circulationStatus;//流通状态（待审核，已审核，归还中，已结束）
    String comment;//备注
}

package com.ssafy.trip.tosspayment.mapper;

import com.ssafy.trip.tosspayment.entity.CashToDotori;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TossPaymentMapper {
    void savePayment(CashToDotori cashToDotori);

}

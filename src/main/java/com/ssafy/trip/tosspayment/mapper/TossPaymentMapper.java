package com.ssafy.trip.tosspayment.mapper;

import com.ssafy.trip.tosspayment.dto.response.BillsResponse;
import com.ssafy.trip.tosspayment.entity.CashToDotori;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TossPaymentMapper {

    void savePayment(CashToDotori cashToDotori);

    @Update("update cash_to_dotori set status = 'CANCEL' where id = #{id}")
    void cancelPayment(Long id);

    List<BillsResponse> searchBillsByUserId(Long userId);

}

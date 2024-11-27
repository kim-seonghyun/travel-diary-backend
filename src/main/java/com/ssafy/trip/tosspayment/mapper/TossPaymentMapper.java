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

    @Update("update user set dotori = dotori + #{quantity} where id = #{userId}")
    void addDotoriToUser(Long userId, Long quantity);

    @Update("update user set dotori = dotori - #{quantity} where id = #{userId}")
    void deleteDotoriToUser(Long userId, Long quantity);

    @Select("select quantity from cash_to_dotori where id = #{id}")
    Long getQuantityDotori(Long id);
}

package com.ssafy.trip.purchase.mapper;

import com.ssafy.trip.purchase.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    void addPurchaseHistory(Purchase purchase);

    Purchase isVerifyPurchase(@Param("traveldiaryId") Long traveldiaryId, @Param("userId") Long userId);
    List<Purchase> getList(Long userId);
}

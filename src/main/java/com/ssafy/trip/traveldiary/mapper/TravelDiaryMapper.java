package com.ssafy.trip.traveldiary.mapper;

import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegistRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TravelDiaryMapper {

    @Insert("INSERT INTO travel_diary (field, title, description, created_at, updated_at, user_id) VALUES (#{field}, #{title}, #{description}, #{createdAt}, #{updatedAt}, #{userId})")
    void regist(TravelDiaryRegistRequest request);
    @Select("select * from travel_diary")
    List<TravelDiaryListResponse> selectAll();

    @Select("select * from travel_diary where id = #{travelDiaryId}")
    TravelDiaryDetailResponse selectById(long travelDiaryId);

    @Update("update travel_diary set title = #{title}, description = #{description} where id = #{id}")
    void update(TravelDiaryUpdateRequest request);

    @Delete("delete from travel_diary where id = #{travelDiaryId}")
    void deleteById(long travelDiaryId);
}
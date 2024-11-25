package com.ssafy.trip.traveldiary.mapper;

import com.ssafy.trip.traveldiary.dto.entity.TravelDiary;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegisterRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TravelDiaryMapper {

    @Insert("INSERT INTO travel_diary (field, title, description, created_at, updated_at, user_id) VALUES (#{field}, #{title}, #{description}, #{createdAt}, #{updatedAt}, #{userId})")
    void regist(TravelDiaryRegisterRequest request);

    @Select("select td.*, u.name as username from travel_diary td JOIN user u on td.user_id = u.id")
    List<TravelDiaryListResponse> selectAll();

    @Select("select * from travel_diary where id = #{travelDiaryId}")
    TravelDiaryDetailResponse selectById(long travelDiaryId);

    @Update("update travel_diary set title = #{title}, description = #{description} where id = #{id}")
    void update(TravelDiaryUpdateRequest request);

    @Delete("delete from travel_diary where id = #{travelDiaryId}")
    void deleteById(long travelDiaryId);

    @Insert("INSERT INTO travel_diary (title, location_id, description, dotori_price, for_sale, created_at, updated_at, user_id, image_name) VALUES (#{title}, #{locationId}, #{description}, #{dotoriPrice}, #{forSale}, #{createdAt}, #{updatedAt}, #{userId}, #{imageName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TravelDiary request);

    @Insert({
            "<script>",
            "INSERT INTO travel_diary_post (travel_diary_id, post_id) VALUES ",
            "<foreach collection='selectedPosts' item='postId' separator=','>",
            "(#{travelDiaryId}, #{postId})",
            "</foreach>",
            "</script>"
    })
    void insertTravelDiaryPosts(@Param("travelDiaryId") long travelDiaryId,
                                @Param("selectedPosts") List<Long> selectedPosts);
}

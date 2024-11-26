package com.ssafy.trip.traveldiary.mapper;

import com.ssafy.trip.traveldiary.dto.entity.TravelDiary;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegisterRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;

import java.util.List;
import java.util.Map;

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

    @Select("""
                SELECT
                                       td.id,
                                       td.user_id AS userId,
                                       u.name AS username,
                                       td.title,
                                       td.image_name AS imageName,
                                       td.description,
                                       td.created_at AS createdAt,
                                       (COALESCE(ug.sea, 0) * COALESCE(tg.sea, 0) +
                                        COALESCE(ug.mountain, 0) * COALESCE(tg.mountain, 0) +
                                        COALESCE(ug.valley, 0) * COALESCE(tg.valley, 0) +
                                        COALESCE(ug.city, 0) * COALESCE(tg.city, 0) +
                                        COALESCE(ug.festival, 0) * COALESCE(tg.festival, 0)) AS matchScore
                                   FROM
                                       travel_diary td
                                   JOIN
                                       user u ON td.user_id = u.id
                                   JOIN
                                       travel_diary_graph tg ON tg.travel_diary_id = td.id
                                   JOIN
                                       (SELECT * FROM travel_graph WHERE user_id = #{userId}) ug ON 1=1
                                   ORDER BY
                                       matchScore DESC;
            """)
    List<TravelDiaryListResponse> selectAll(Long userId);

    @Select("SELECT td.*, " +
            "CASE WHEN p.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS isPurchased, " +
            "u.name AS username " +
            "FROM travel_diary td " +
            "LEFT JOIN purchase p ON td.id = p.traveldiary_id AND p.user_id = #{userId} " +
            "JOIN user u ON td.user_id = u.id " +
            "WHERE td.id = #{travelDiaryId}")
    TravelDiaryDetailResponse selectById(@Param("travelDiaryId") long travelDiaryId, @Param("userId") long userId);

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

    @Select("select post_id from travel_diary_post where travel_diary_id=#{travelDiaryId}")
    String selectTravelDiaryPost(Long travelDiaryId);

    @Insert("insert into travel_diary_graph(travel_diary_id, sea, mountain, valley, city, festival)" +
            "values (#{diaryId}, #{sea}, #{mountain},#{valley}, #{city}, #{festival})")
    void saveDiaryGraph(Map<String, Long> map);

}

package com.ssafy.trip.post.mapper;

import com.ssafy.trip.post.dto.response.PostListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TravelDiaryPostMapper {

    @Select("""
                SELECT p.post_image AS postImage,
                    p.trip_id AS tripId,
                    p.created_at AS createdAt,
                    t.facility_name AS facilityName,
                    p.user_id AS userId,
                    u.name AS username,
                    p.id AS id,
                    p.content AS content,
                    (SELECT COUNT(DISTINCT pl.user_id)
                     FROM post_like pl
                     WHERE pl.post_id = p.id) AS postLikes,
                    (SELECT COALESCE(SUM(pv.views_count), 0)
                     FROM post_view pv
                     WHERE pv.post_id = p.id) AS viewsCount
                FROM 
                    post p
                JOIN 
                    trip t ON p.trip_id = t.id
                JOIN 
                    user u ON p.user_id = u.id
                JOIN
                        travel_diary_post tdp ON tdp.post_id = p.id
                WHERE
                    tdp.travel_diary_id = #{travelDiaryId}
            """)
    List<PostListResponse> getListByTravelDiaryId(long travelDiaryId);
}

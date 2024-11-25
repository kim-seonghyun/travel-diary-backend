package com.ssafy.trip.post.mapper;

import com.ssafy.trip.post.dto.entity.Post;
import com.ssafy.trip.post.dto.request.PostUpdateRequest;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.dto.response.PostListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper {
    @Delete("delete from post where diary_id = #{travelDiaryId}")
    void deleteByTravelDiaryId(long travelDiaryId);

    @Insert("insert into post(content, user_id, trip_id, post_image) values (#{content},#{userId},#{tripId}, #{postImage})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void regist(Post request);

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
                GROUP BY 
                    p.id, p.post_image, p.trip_id, p.created_at, 
                    t.facility_name, p.user_id, u.name, p.content
            """)
    List<PostListResponse> list();

    @Select("select * from post where trip_id = #{tripId}")
    List<PostListResponse> getListOnlyThree(Long tripId);

    @Select("select * from post where trip_id = #{tripId} order by created_at desc limit 3")
    List<PostListResponse> getListByTripId(Long tripId);

    @Select("SELECT \n" +
            "    p.post_image AS postImage,\n" +
            "    p.trip_id AS tripId,\n" +
            "    p.created_at AS createdAt,\n" +
            "    t.facility_name AS facilityName,\n" +
            "    p.user_id AS userId,\n" +
            "    u.name AS username,\n" +
            "    p.content AS content,\n" +  // 쉼표 추가
            "    p.id AS id\n" +
            "FROM \n" +
            "    post p\n" +
            "JOIN \n" +
            "    trip t ON p.trip_id = t.id\n" +
            "JOIN \n" +
            "    user u ON p.user_id = u.id\n" +
            "WHERE \n" +
            "    p.id = #{postId};")
    PostDetailResponse selectById(Long postId);

    @Update("update post set title = #{title}, content = #{content} where id = #{id}")
    void update(PostUpdateRequest request);

    @Delete("delete from post where id = #{postId}")
    void deleteByPostId(Long postId);

    @Insert("INSERT INTO post_view(post_id, user_id) VALUES(#{postId}, #{userId}) ON DUPLICATE KEY UPDATE views_count = views_count + 1")
    void incrementView(Long postId, Long userId);


    @Insert("INSERT INTO post_like(post_id, user_id) VALUES(#{postId}, #{userId}) ")
    void incrementLikes(Long postId, Long userId);

    @Select("SELECT COUNT(*) FROM post_like WHERE post_id = #{postId} AND user_id = #{userId}")
    int isLiked(Long postId, Long userId);

    @Delete("DELETE FROM post_like WHERE post_id = #{postId} AND user_id = #{userId}")
    void decrementLikes(Long postId, Long userId);
}

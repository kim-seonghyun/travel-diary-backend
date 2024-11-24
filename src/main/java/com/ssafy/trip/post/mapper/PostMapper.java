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

    @Select("SELECT " +
            "    p.post_image AS postImage, " +
            "    p.trip_id AS tripId, " +
            "    p.created_at AS createdAt, " +
            "    t.facility_name AS facilityName, " +
            "    p.user_id AS userId, " +
            "    u.name AS username, " +
            "    p.id AS id, " +
            "    p.content AS content " +
            "FROM " +
            "    post p " +
            "JOIN " +
            "    trip t ON p.trip_id = t.id " +
            "JOIN " +
            "    user u ON p.user_id = u.id")
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
            "    u.name AS username\n" +
            "FROM \n" +
            "    posts p\n" +
            "JOIN \n" +
            "    trips t ON p.trip_id = t.trip_id\n" +
            "JOIN \n" +
            "    users u ON p.user_id = u.user_id\n" +
            "WHERE \n" +
            "    p.post_id = #{postId};")
    PostDetailResponse selectById(Long postId);

    @Update("update post set title = #{title}, content = #{content} where id = #{id}")
    void update(PostUpdateRequest request);

    @Delete("delete from post where id = #{postId}")
    void deleteByPostId(Long postId);
}

package com.ssafy.trip.post.mapper;

import com.ssafy.trip.post.dto.request.PostRegistRequest;
import com.ssafy.trip.post.dto.request.PostUpdateRequest;
import com.ssafy.trip.post.dto.response.PostDetailResponse;
import com.ssafy.trip.post.dto.response.PostListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper {
    @Delete("delete from post where diary_id = #{travelDiaryId}")
    void deleteByTravelDiaryId(long travelDiaryId);

    @Insert("insert into post(title, content, created_at, updated_at, diary_id, user_id) values (#{title},#{content},#{createdAt},#{updatedAt},#{diaryId},#{userId})")
    void regist(PostRegistRequest request);

    @Select("select * from post")
    List<PostListResponse> list();
    @Select("select * from post where id = #{postId}")
    PostDetailResponse selectById(Long postId);

    @Update("update post set title = #{title}, content = #{content} where id = #{id}")
    void update(PostUpdateRequest request);

    @Delete("delete from post where id = #{postId}")
    void deleteByPostId(Long postId);
}

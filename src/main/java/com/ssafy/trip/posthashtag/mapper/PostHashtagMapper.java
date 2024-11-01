package com.ssafy.trip.posthashtag.mapper;

import com.ssafy.trip.post.dto.response.PostListResponse;
import com.ssafy.trip.posthashtag.dto.entity.PostHashtag;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostHashtagMapper {


    @Delete("delete from post_hashtag where post_id = #{postId}")
    void deleteByPostId(Long postId);

    @Insert("insert into post_hashtag(post_id, hashtag_id) values (#{postId}, #{hashtagId})")
    void regist(PostHashtag postHashtag);

    @Select("select * from post p where p.id in (select ht.post_id from post_hashtag ht where ht.hashtag_id = #{hashtagId})")
    List<PostListResponse> selectByHashtagId(Long hashtagId);
}

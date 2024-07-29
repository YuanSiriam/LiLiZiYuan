package com.ziyuan.serve.post.repository;


import com.ziyuan.serve.post.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * class description goes here
 *
 * @ClassName: PostMapper
 * @Description:
 */

@Mapper
public interface PostMapper {

    List<Post> findAllPost();

    void insertPost(int userId, String title, String topic, String content, Date createTime);

}

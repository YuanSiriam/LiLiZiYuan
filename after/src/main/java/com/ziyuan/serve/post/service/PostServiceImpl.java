package com.ziyuan.serve.post.service;


import com.ziyuan.serve.post.api.PostService;
import com.ziyuan.serve.post.entity.Post;
import com.ziyuan.serve.post.repository.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * class description goes here
 *
 * @ClassName: PostServiceImpl
 * @Description:
 */

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Map<String, Object>> showAllPost() {

        List<Post> posts = postMapper.findAllPost();

        List<Map<String, Object>> listPost = new ArrayList<>();

        for (Post post : posts) {
            Map<String, Object> mapPost = new HashMap<>();
            mapPost.put("postId", post.getPostId());
            mapPost.put("userId", post.getUserId());
            mapPost.put("title", post.getTitle());
            mapPost.put("topic", post.getTopic());
            mapPost.put("content", post.getContent());
            mapPost.put("createTime", post.getCreateTime());
            mapPost.put("heat", post.getHeat());

            mapPost.put("button","looklook");
            mapPost.put("expanded",false);

            listPost.add(mapPost);
        }
        return listPost;
    }

    @Override
    public void insertPost(Map<String, Object> map) {

        int userId = Integer.parseInt((String) map.get("userId"));
        String title = (String) map.get("title");
        String topic = (String) map.get("topic");
        String content = (String) map.get("content");
        Date createTime = new Date();
        try {
            postMapper.insertPost(userId, title, topic, content, createTime);
        } catch (Exception e) {
            log.error("插入帖子失败");
        }
    }

    @Override
    public void updatePost(int postId, int userId, String title, String topic, String content) {

    }

}

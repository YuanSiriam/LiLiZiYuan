package com.ziyuan.serve.post.api;



import java.util.List;
import java.util.Map;

/**
 * class description goes here
 *
 * @ClassName: PostService
 * @Description:
 */
public interface PostService {

    List<Map<String, Object>> showAllPost();

    void insertPost(Map<String, Object> map);

    void updatePost(int postId, int userId, String title, String topic, String content);
}

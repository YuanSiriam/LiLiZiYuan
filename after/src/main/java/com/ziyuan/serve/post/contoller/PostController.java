package com.ziyuan.serve.post.contoller;


import com.ziyuan.serve.post.entity.Comment;
import com.ziyuan.serve.post.service.CommentServiceImpl;
import com.ziyuan.serve.post.service.PostServiceImpl;
import com.ziyuan.serve.post.service.ReplyServiceImpl;
import com.ziyuan.serve.util.ServeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/23
 */

@Slf4j
@RequestMapping("/post")
@RestController
@Api(tags = "帖子模块")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private ReplyServiceImpl replyService;

    /**
     * 显示所有帖子
     */
    @GetMapping("/showAll")
    @ApiOperation(value = "显示所有帖子", tags = {"帖子模块"})
    public String showAllPost() {
        List<Map<String, Object>> listPost = postService.showAllPost();

        if (!listPost.isEmpty()) {

            return ServeUtil.getArrayJSONString(0, "获取成功", listPost);
        } else {

            return ServeUtil.getJSONString(0, "没有帖子");
        }

    }

    /**
     * 插入新贴
     */
    @ApiOperation(value = "发布帖子", tags = {"帖子模块"})
    @PostMapping("/upload")
    public String uploadPost(@RequestBody Map<String, Object> map) {
        try {
            postService.insertPost(map);
        } catch (Exception e) {
            return ServeUtil.getJSONString(0, "添加失败");
        }
        return ServeUtil.getJSONString(0, "添加成功");
    }

    /**
     * 查询所有评论
     */
    @ApiOperation(value = "显示所有评论", tags = {"帖子模块"})
    @GetMapping("/{postId}")
    public String showComment(@PathVariable("postId") int postId) {
        List<Map<String, Object>> list = commentService.selectAllComment(postId);
        return ServeUtil.getArrayJSONString(0, "获取成功", list);
    }

    /**
     * 查询所有回复
     */
    @ApiOperation(value = "显示所有回复", tags = {"帖子模块"})
    @GetMapping("/{postId}/getReply")
    public String showReply(@PathVariable("postId") int postId) {
        List<Map<String, Object>> list = replyService.selectAllReply(postId);
        return ServeUtil.getArrayJSONString(0, "查询成功", list);
    }

    /**
     * 上传评论
     */
    @ApiOperation(value = "上传评论", tags = {"帖子模块"})
    @PostMapping("/{postId}/uComment")
    public String uploadComment(@RequestBody Map<String, Object> map, @PathVariable("postId") int postId) {
        String username = (String) map.get("username");
        String content = (String) map.get("content");
        int userId = Integer.parseInt((String) map.get("userId"));
        commentService.insertComment(username, userId, postId, content, 0);

        return ServeUtil.getJSONString(0, "上传成功");
    }

    /**
     * 上传回复
     */
    @ApiOperation(value = "上传回复", tags = {"帖子模块"})
    @PostMapping("/{postId}/{toCommentId}/{toReplyUsername}/uReply")
    public String uploadReply(@RequestBody Map<String, Object> map,
                              @PathVariable("postId") int postId,
                              @PathVariable("toReplyUsername") String toReplyUsername,
                              @PathVariable("toCommentId") int toCommentId) {
        String username = (String) map.get("username");
        String content = (String) map.get("content");
        int userId = Integer.parseInt((String) map.get("userId"));
        replyService.insertReply(username, userId, postId, toCommentId, toReplyUsername, content);

        commentService.updateComment(toCommentId);

        return ServeUtil.getJSONString(0, "回复成功");
    }

}

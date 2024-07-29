package com.ziyuan.serve.message.contoller;



import com.ziyuan.serve.message.service.MessageServiceImpl;
import com.ziyuan.serve.util.ServeConstant;
import com.ziyuan.serve.util.ServeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/23
 */

@Slf4j
@RequestMapping("/message")
@RestController
@Api(tags = "消息模块")
public class MessageController implements ServeConstant{

    @Autowired
    private MessageServiceImpl messageService;

    /**
     * 获取所有有关消息
     */
    @GetMapping("/show")
    @ApiOperation(value = "显示所有消息", tags = {"消息模块"})
    public String showMessages(@RequestParam("userId") int userId){
        List<Map<String, Object>> mapList = messageService.showAllMessage(userId);

        if (!mapList.isEmpty()){
            return ServeUtil.getArrayJSONString(0,"获取成功",mapList);
        }else {
            return ServeUtil.getJSONString(1,"消息获取失败");
        }

    }

    /**
     * 发送消息
     */
    @PostMapping("/send")
    @ApiOperation(value = "发送消息", tags = {"消息模块"})
    public String sendMessage(int userId, String content, int toId){
        try{
            messageService.insertMessage(userId, userId == 7?0:toId, content, new Date(),userId == 7? MESSAGE_SYSTEM:MESSAGE_USER);
        }catch(Exception e){
            return ServeUtil.getJSONString(ERROR_CODE,"发送失败");
        }

        return ServeUtil.getJSONString(SUCCESS_CODE,"发送成功");
    }

}

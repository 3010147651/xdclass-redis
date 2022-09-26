package net.xdclass.xdclassredis.controller;


import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/rank")
public class RankController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String DAILY_RANK_KEY="video:rank:daily";

    @GetMapping("daily_rank")
    public JsonData videoDailyRank(){
        //获取redis集合
        List<VideoDO> list = redisTemplate.opsForList().range(DAILY_RANK_KEY,0,-1);
        return JsonData.buildSuccess(list);
    }


}

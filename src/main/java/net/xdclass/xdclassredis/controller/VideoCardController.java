package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.service.VideoCardService;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/card")
public class VideoCardController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoCardService videoCardService;

    private static final  String VIDEO_CARD_CACHE_KEY = "video:card:key";

    @GetMapping("list_cache")
    public JsonData listCard(){

        Object  cacheObj = redisTemplate.opsForValue().get(VIDEO_CARD_CACHE_KEY);
        if (cacheObj != null){
            return JsonData.buildSuccess(cacheObj);
        }
        List<VideoCardDO> list = videoCardService.list();
        redisTemplate.opsForValue().set(VIDEO_CARD_CACHE_KEY,list,10, TimeUnit.MINUTES);


        return JsonData.buildSuccess(list);
    }



    /**
     * 无缓存
     * @return
     */
    @GetMapping("list_nocache")
    public JsonData listCardNoCache(){

        List<VideoCardDO> list = videoCardService.list();
        return JsonData.buildSuccess(list);
    }

}

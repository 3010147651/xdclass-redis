package net.xdclass.xdclassredis.dao;

import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.model.VideoDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VideoCardDao {

     public List<VideoCardDO> list(){

         try {
             Thread.sleep(200);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         List<VideoCardDO> cardDaoList = new ArrayList<>();

         VideoCardDO videoCardDo = new VideoCardDO();
         videoCardDo.setId(1);
         videoCardDo.setTitle("热门视频");

         VideoDO videoDO1 = new VideoDO(1,"微服务","xdclass.net",25);
         VideoDO videoDO2 = new VideoDO(2,"java开发","xdclass.net",86);
         VideoDO videoDO3 = new VideoDO(3,"redis开发","xdclass.net",76);
         VideoDO videoDO4 = new VideoDO(4,"web前端","xdclass.net",65);

        List<VideoDO> videoDOS = new ArrayList<>();
        videoDOS.add(videoDO1);
        videoDOS.add(videoDO2);
         videoDOS.add(videoDO3);
         videoDOS.add(videoDO4);
         videoCardDo.setList(videoDOS);

         cardDaoList.add(videoCardDo);




         VideoCardDO videoCardDo2 = new VideoCardDO();
         videoCardDo2.setId(1);
         videoCardDo2.setTitle("热门视频");

         VideoDO videoDO5 = new VideoDO(1,"html开发","xdclass.net",75);
         VideoDO videoDO6 = new VideoDO(2,"前端开发","xdclass.net",24);
         VideoDO videoDO7 = new VideoDO(3,"后端开发","xdclass.net",41);
         VideoDO videoDO8 = new VideoDO(4,"数据库设计","xdclass.net",54);

         List<VideoDO> videoDOS2 = new ArrayList<>();
         videoDOS2.add(videoDO1);
         videoDOS2.add(videoDO2);
         videoDOS2.add(videoDO3);
         videoDOS2.add(videoDO4);
         videoCardDo2.setList(videoDOS);

         cardDaoList.add(videoCardDo2);

         return cardDaoList;
     }







}

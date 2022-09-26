package net.xdclass.xdclassredis.service;

import net.xdclass.xdclassredis.dao.VideoCardDao;
import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.model.VideoDO;

import java.util.List;

public interface VideoCardService {

    List<VideoCardDO> list();
}

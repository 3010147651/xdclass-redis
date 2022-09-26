package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.dao.VideoDao;
import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.util.JsonData;
import net.xdclass.xdclassredis.util.JsonUtil;
import net.xdclass.xdclassredis.vo.CartItemVO;
import net.xdclass.xdclassredis.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/v1/cart")
@RestController
public class CartController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoDao videoDao;

    /**
     * 购物车添加商品
     * @param videoId
     * @param buyNum
     * @return
     */
    @RequestMapping("add")
    public JsonData addCart(int videoId,int buyNum){
        //获取购物车
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();
        Object cacheObj = myCart.get(videoId + "");
        String result = "";
        if (cacheObj != null){
            result =(String)cacheObj;

        }
        //购物车没有这个商品时
        if (cacheObj ==  null){
            CartItemVO cartItem = new CartItemVO();
            VideoDO videoDO = videoDao.findDetailById(videoId);
            cartItem.setBuyNum(buyNum);
            cartItem.setProductId(videoId);
            cartItem.setPrice(videoDO.getPrice());
            cartItem.setProductImg(videoDO.getImg());
            cartItem.setTotalPrice(videoDO.getPrice());
            cartItem.setProductTitle(videoDO.getTitle());
            //存值
            myCart.put(videoId+"", JsonUtil.objectToJson(cartItem));
        }else {
            //增加商品购买数量
            CartItemVO cartItemVO = JsonUtil.jsonToPojo(result, CartItemVO.class);
            cartItemVO.setBuyNum(cartItemVO.getBuyNum()+buyNum);
            myCart.put(videoId+"", JsonUtil.objectToJson(cartItemVO));
        }
        return JsonData.buildSuccess();
    }


    /**
     * 查看我的购物车
     * @return
     */
    @RequestMapping("mycart")
    public JsonData getMycart(){
        //获取购物车
        BoundHashOperations<String, Object, Object> myCart =  getMyCartOps();
        //获取购物车数据
       List<Object> itemList =  myCart.values();

        List<CartItemVO> cartItemVOList = new ArrayList<>();

        for (Object item : itemList) {
            CartItemVO cartItemVO = JsonUtil.jsonToPojo((String) item, CartItemVO.class);
            cartItemVOList.add(cartItemVO);
        }
        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);
        return JsonData.buildSuccess(cartVO);

    }

    @RequestMapping("clear")
    public JsonData clear(){
        String cartkey = getCartkey();
        redisTemplate.delete(cartkey);
        return JsonData.buildSuccess();
    }


    /**
     * 抽取我的购物车通用方法
     * @return
     */
    private BoundHashOperations<String,Object,Object> getMyCartOps(){
        String key = getCartkey();
        //指定key
           return   redisTemplate.boundHashOps(key);
    }

    /**
     * 模拟用户
     * @return
     */
    public String getCartkey(){
        int userId = 88;
        return String.format("video:cart:%s",userId);
    }


}

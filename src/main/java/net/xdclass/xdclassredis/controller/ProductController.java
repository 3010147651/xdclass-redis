package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.model.ProductDO;
import net.xdclass.xdclassredis.service.ProductService;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增
     * @param productDO
     * @return
     */
    @PostMapping("add")
    public JsonData add(@RequestBody ProductDO productDO){
        productDO.setCreateTime(new Date());
        int save = productService.save(productDO);
        return JsonData.buildSuccess(save);
    }

    /**
     * 修改
     * @param productDO
     * @return
     */
    @PostMapping("update")
    public JsonData update(@RequestBody ProductDO productDO){
        int save = productService.updateById(productDO);
        return JsonData.buildSuccess(save);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("remove")
    public JsonData remove( int id){
        int i = productService.delById(id);
        return JsonData.buildSuccess(i);
    }

    @GetMapping("find")
    public JsonData findById( int id){
        ProductDO productDO = productService.findById(id);
        return JsonData.buildSuccess(productDO);
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @GetMapping("page")
    public JsonData page( int page,int size){
        Map<String,Object> data = productService.page(page,size);
        return JsonData.buildSuccess(data);
    }

}

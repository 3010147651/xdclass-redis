package net.xdclass.xdclassredis.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.xdclass.xdclassredis.dao.ProductMapper;
import net.xdclass.xdclassredis.model.ProductDO;
import net.xdclass.xdclassredis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public int save(ProductDO productDO) {
        return productMapper.insert(productDO);
    }

    @Override
    public int delById(int id) {
        return productMapper.deleteById(id);
    }

    @Override
    public int updateById(ProductDO productDO) {
        return productMapper.updateById(productDO);
    }

    @Override
    public ProductDO findById(int id) {
        return productMapper.selectById(id);
    }

    @Override
    public Map<String, Object> page(int page, int size) {
        Map<String,Object> data = new HashMap<>(3);// 不创建内部存储数组,内部空间开辟了 4 （数组长度=4）
        //创建分页对象
        Page<ProductDO> objectPage = new Page<>(page, size);
        //配置分页条件，查询条件不设置
        Page<ProductDO> productDOPage = productMapper.selectPage(objectPage, null);
        //总页数
        data.put("page",productDOPage.getPages());
        //总条数
        data.put("total",productDOPage.getTotal());
        //分页查询的数据
        data.put("data",productDOPage.getRecords());

        return data;
    }
}

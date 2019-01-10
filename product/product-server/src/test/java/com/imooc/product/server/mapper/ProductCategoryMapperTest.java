package com.imooc.product.server.mapper;

import com.imooc.product.server.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void findByCategoryTypeIn() {

        List<ProductCategory> productCategories = productCategoryMapper.findByCategoryTypeIn(Arrays.asList(1, 22));
        System.out.println(productCategories);
    }
}
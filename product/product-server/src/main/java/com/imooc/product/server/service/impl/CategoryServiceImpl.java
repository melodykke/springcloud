package com.imooc.product.server.service.impl;


import com.imooc.product.server.mapper.ProductCategoryMapper;
import com.imooc.product.server.model.ProductCategory;
import com.imooc.product.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes) {
        return productCategoryMapper.findByCategoryTypeIn(categoryTypes);
    }
}

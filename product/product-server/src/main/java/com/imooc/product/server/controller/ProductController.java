package com.imooc.product.server.controller;

import com.google.common.collect.Lists;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.server.VO.ProductInfoVO;
import com.imooc.product.server.VO.ProductVO;
import com.imooc.product.server.VO.ResultVO;
import com.imooc.product.server.model.ProductCategory;
import com.imooc.product.server.model.ProductInfo;
import com.imooc.product.server.service.CategoryService;
import com.imooc.product.server.service.ProductService;
import com.imooc.product.server.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 1.查询所有在架商品
     * 2.获取类目type列表
     * 3.查询类目
     * 4.构造数据
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list() {
        // 查找所有已上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        // 获取所有已上架商品的所属类目id
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        // 通过类目id查找类目实体
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOs = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOs = Lists.newArrayList();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOs.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOs);
            productVOs.add(productVO);
        }
        return ResultVOUtil.success(productVOs);
    }

    /**
     * 获取商品列表（给订单服务用）
     * @param productIds
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIds) {
        return productService.findList(productIds);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productService.decreaseStock(decreaseStockInputList);
    }
}

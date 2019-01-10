package com.imooc.order.server.utils;


import com.imooc.order.server.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object obj) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功！");
        resultVO.setData(obj);
        return resultVO;
    }
}

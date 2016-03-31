package cn.edu.ncut.doubanWebSpider.dao;

import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;

public interface SimpleBookInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SimpleBookInfo record);

    int insertSelective(SimpleBookInfo record);

    SimpleBookInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SimpleBookInfo record);

    int updateByPrimaryKey(SimpleBookInfo record);
}
package cn.edu.ncut.doubanWebSpider.dao;

import cn.edu.ncut.doubanWebSpider.model.BookTag;

public interface BookTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BookTag record);

    int insertSelective(BookTag record);

    BookTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BookTag record);

    int updateByPrimaryKey(BookTag record);
}
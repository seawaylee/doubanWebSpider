package cn.edu.ncut.doubanWebSpider.dao;

import cn.edu.ncut.doubanWebSpider.model.BookTag;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BookTagMapper extends Mapper<BookTag>{
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(BookTag record);
//
//    int insertSelective(BookTag record);
//
//    BookTag selectByPrimaryKey(Integer id);
//
//    List<BookTag> selectAll();
//    
      List<BookTag> selectByName(String tagName);
//    
//    int updateByPrimaryKeySelective(BookTag record);
//
//    int updateByPrimaryKey(BookTag record);
}
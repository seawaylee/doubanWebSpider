package cn.edu.ncut.doubanWebSpider.dao;

import cn.edu.ncut.doubanWebSpider.model.BookComment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BookCommentMapper extends Mapper<BookComment>{
	//    int deleteByPrimaryKey(Integer id);
	//
	//    int insert(BookComment record);
	//
	//    int insertSelective(BookComment record);
	//
	//    BookComment selectByPrimaryKey(Integer id);
	//
	//    	List<BookComment> selectAll();
	//    int updateByPrimaryKeySelective(BookComment record);
	//
	//    int updateByPrimaryKey(BookComment record);
	//
	List<BookComment> getCommentNumByUserNoStatistic(Integer selectLimitAmount);
	List<BookComment> getRatingStatistic();
	List<BookComment> getCommentTimeStatistic();
	List<BookComment> selectAllHasRatings();
	List<BookComment> selectAllNoRatings();
	List<String> findAllCommentersNo();
}
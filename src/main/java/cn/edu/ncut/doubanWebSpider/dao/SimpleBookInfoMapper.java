package cn.edu.ncut.doubanWebSpider.dao;

import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SimpleBookInfoMapper extends Mapper<SimpleBookInfo>
{
	List<SimpleBookInfo> getBookNumByTagStatic(Integer selectLimitAmount);
	List<SimpleBookInfo> getBookRatingSort(Integer selectLimitAmount);
	List<SimpleBookInfo> selectByName(String title);
	List<SimpleBookInfo> selectAllNames();
	List<SimpleBookInfo> selectAllByUrl(List<String> urls);
	List<SimpleBookInfo> selectByUrl(String url);
	List<SimpleBookInfo> selectAllUrls();
	int update(SimpleBookInfo simpleBookInfo);
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(SimpleBookInfo record);
//
//    int insertSelective(SimpleBookInfo record);
//
//    SimpleBookInfo selectByPrimaryKey(Integer id);
//    
//    List<SimpleBookInfo> selectByTagName(String[] tags);
//
//    int updateByPrimaryKeySelective(SimpleBookInfo record);
//
//    int updateByPrimaryKey(SimpleBookInfo record);
}
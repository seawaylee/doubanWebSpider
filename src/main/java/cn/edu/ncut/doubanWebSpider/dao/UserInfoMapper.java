package cn.edu.ncut.doubanWebSpider.dao;

import cn.edu.ncut.doubanWebSpider.model.UserInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo>{
    List<UserInfo> selectByUserno(String userno);
}
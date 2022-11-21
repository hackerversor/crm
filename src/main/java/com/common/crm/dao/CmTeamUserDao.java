package com.common.crm.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmTeamUserDao {

	/**
	    *      团队新增
	 * @param team_name 团队名称
	 * @param slogan 口号
	 * @param user_id 团队主管
	 * @return {retCode,retMsg}
	 */
	public void add(@Param ("team_id") String team_id,
			@Param("team_name")String team_name,
			@Param("slogan")String slogan,
			@Param("user_id")String user_id,
			@Param("ctime")Timestamp ctime); 
	
	/**
	 *	团队删除  需要把对应的团队成员删除
	 * @param team_id 团队编码
	 * @return {retCode,retMsg}
	 */
	public void delete(@Param("team_id")String team_id);
	
	/**
	 *	团队修改   
	 * @param team_id	团队编码
	 * @param team_name	团队名称
	 * @param slogan	口号
	 * @param user_id	团队主管
	 * @return {retCode,retMsg}
	 */
	
	public void modify(@Param("team_id")String team_id,
			@Param("team_name")String team_name,
			@Param("slogan")String slogan,
			@Param("user_id")String user_id);
	
	/**
	 *	团队查询
	 * @param team_id	团队编码
	 * @param team_name	团队名称
	 * @param user_id	团队主管
	 * @return {retCode,retMsg,list:team_id,slogan,user_id,user_name,ctime}
	 */
	public List<HashMap> query(@Param("team_id")String team_id,
			@Param("team_name")String team_name,
			@Param("user_id")String user_id);
	
	/**
	 *	团队成员查询
	 * @param team_id  团队编码	
	 * @return {retCode,retMsg,team_id,team_name,slogan,user_id,user_name,ctime,
	 * users:user_id,name,sex,birthday,hiredate,email,phone,leavedate,ctime,status,remark,user_no}
	 */
	public List<HashMap> queryTeam(@Param("team_id")String team_id);
	/**
	 *	团队成员查询
	 * @param team_id  团队编码	
	 * @return {retCode,retMsg,team_id,team_name,slogan,user_id,user_name,ctime,
	 * users:user_id,name,sex,birthday,hiredate,email,phone,leavedate,ctime,status,remark,user_no}
	 */
	public List<HashMap> queryUser(@Param("team_id")String team_id);
	
	/**
	 *	团队成员新增    团队成员新增  一个人只能加入一个团队  如果已经加入团队则拒绝并提示
	 * @param team_id	团队编码
	 * @param user_id	用户编码  json数组 [user_id1,user_id2,...]
	 * @return {retCode,retMsg}
	 */
	public void memberAdd(@Param("team_id")String team_id,
			@Param("user_id")String user_id);
	
	/**
	 *	团队成员删除
	 * @param team_id 团队编码
	 * @param user_id 用户编码  json数组 [user_id1,user_id2,...]
	 * @return {retCode,retMsg}
	 */
	public void memberDel(@Param("team_id")String team_id,
			@Param("user_id")String user_id);
	
	
	/**
	 * 	根据成员的user_id 获取team信息
	 * @param user_id
	 * @return{team_id,team_name,slogan,b.user_id, name,ctime }
	 */
	public HashMap queryByMemBer(@Param("user_id")String user_id);
	
	
	/**
	 *  	查询成员名称
	 * @param team_id 非必输
	 * @param user_id 员工编码数组
	 * @return [{user_id,name}]
	 */
	public List<HashMap> queryMerberName(@Param("team_id")String team_id,@Param("user_id")String[] user_id);
}

package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmPolicyDataInfoDao {

    /**
     * 	批量插入数据
     * @param list [{policy,kind,policy_holder,phone,sex,addr,car_no,frame_no,engine_no,car_owner,idno,car_reg_date,underwrite_date,s_date,e_date,written_premium,fx,source,cust_manager,status,insurance,record_used}]
     */
    public void batch_add(@Param("list")List<HashMap> list);
	
}

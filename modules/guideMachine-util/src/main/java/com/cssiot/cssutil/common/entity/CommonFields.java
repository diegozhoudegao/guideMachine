package com.cssiot.cssutil.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 共同字段
 * @author
 *	2017-10-09 athena 创建
 *	2018-10-05 athena 调整状态英文名
 */
@Data
@MappedSuperclass
public class CommonFields {

	@Id
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	@Column(name="id")
	private String id;	

	//创建人
	@Column(name="createUser")
	private String createUser;
	
	//创建时间
	@Column(name="createTime")
	private Date createTime;
	
	//最后修改人
	@Column(name="lastUpdateUser")
	private String lastUpdateUser;
	
	//最后修改时间
	@Column(name="lastUpdateTime")
	private Date lastUpdateTime;
	
	//其他状态(0正常、1废弃)---订单状态(0锁定、1租借中、2完成、3失败)--员工状态(0暂存、1离职、2在职)
	@Column(name="status")
	private String status;
}
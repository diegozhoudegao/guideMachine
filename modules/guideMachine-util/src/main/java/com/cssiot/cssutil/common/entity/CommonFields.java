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
 */
@Data
@MappedSuperclass
public class CommonFields {

	@Id
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	@Column(name="id_")
	private String id;	

	//创建人
	@Column(name="createUser_")
	private String createUser;
	
	//创建时间
	@Column(name="createTime_")
	private Date createTime;
	
	//最后修改人
	@Column(name="lastUpdateUser_")
	private String lastUpdateUser;
	
	//最后修改时间
	@Column(name="lastUpdateTime_")
	private Date lastUpdateTime;
	
	//状态
	@Column(name="state_")
	private String state;
}
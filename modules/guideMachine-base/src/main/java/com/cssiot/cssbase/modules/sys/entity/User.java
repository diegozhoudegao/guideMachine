package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.cssiot.cssutil.common.entity.CommonFields;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  用户表
 */

@Entity
@Data
@Table(name = "testUser")
public class User extends CommonFields{
	
	@ApiModelProperty(value="姓名")
	@Column(name="name_")
	private String name;			//姓名
	
}
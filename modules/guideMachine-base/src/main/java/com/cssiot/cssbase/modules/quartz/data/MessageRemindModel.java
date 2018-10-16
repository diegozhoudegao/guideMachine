package com.cssiot.cssbase.modules.quartz.data;

import java.io.Serializable;
import java.util.List;

import com.cssiot.cssutil.common.data.AccessModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息提醒设定表
 * @ClassName: MessageRemind
 * @Description: 
 * @author 
 * 2018年8月22日  tck 创建
 */
@Data
@NoArgsConstructor
public class MessageRemindModel implements Serializable{
    private static final long serialVersionUID = 1L;
    
	@ApiModelProperty(value="消息提醒的id(一般提醒时须有'消息提醒id',表单提醒则要'dataId,formId,formMessageRemindId')")
	private String messageRemindId;
	
	@ApiModelProperty(value="消息标题")
	private String title;
	
	@ApiModelProperty(value="消息内容")
	private String content;
	
	@ApiModelProperty(value="消息类型（0邮件，1短信，2系统内消息, 3微信, 4钉钉）")
	private String type;
	
	@ApiModelProperty(value="发送类型（0立即发送，1发送一次，2重复发送）")
	private String sendType;
	
	@ApiModelProperty(value="发送一次时,才有提醒时间")
	private String remindTime;
	
	@ApiModelProperty(value="重复类型(0每日,1每周,2每月,3每年)")
	private String repeatType;
	
	@ApiModelProperty(value="重复时间点(每日-00:00,每周-周一~周五,每月-1~28、最后一天,每年)")
	private String repeatTimePoint;
	
	@ApiModelProperty(value="具体重复的时间")
	private String repeatValue;
	
	@ApiModelProperty(value="设定范围（-1当前审批人；0人员；1组织；2角色；3部门；4职务；5直属上级；-2所有审批人；-3提出申请人）")
	private String setRange;
	
	@ApiModelProperty(value="范围里具体的id字符串集合")
	private List<String> rangeIdList;
	
	@ApiModelProperty(value="定时任务时的唯一名字,自动生成")
	private String uniqueName;
	
	@ApiModelProperty(value="操作权限")
	private AccessModel accessModel;
	
	/**********************表单提醒使用****************/
	@ApiModelProperty(value="客户id")
	private String customerId;
	
	@ApiModelProperty(value="发送者ID,定时任务中发送消息时使用")
	private String loginName;
	
	@ApiModelProperty(value="表单提醒使用,表单提醒设定表的id")
	private String formMessageRemindId;
	
	@ApiModelProperty(value="表单提醒使用,表单的id")
	private String formId;
	
	@ApiModelProperty(value="表单提醒使用,数据的id")
	private String dataId;

	//新加字段记得要重写该方法(集合字段不要放进来),写成json格式,为了后面定时任务Gson解析
	@Override
	public String toString() {
		return "{messageRemindId='" + messageRemindId + "', title='" + title + "', content='" + content
				+ "', type='" + type + "', sendType='" + sendType + "', remindTime='" + remindTime + "', repeatType='"
				+ repeatType + "', repeatTimePoint='" + repeatTimePoint + "', repeatValue='" + repeatValue + "', setRange='"
				+ setRange + "', uniqueName='" + uniqueName + "', customerId='"
				+ customerId + "', loginName='" + loginName + "', formMessageRemindId='" + formMessageRemindId + "', formId='"
				+ formId + "', dataId='" + dataId + "'}";
	}
}

package com.cssiot.cssutil.common.data;

import lombok.Data;

/**
 * 操作权限
 * @author 
 * 	2017-04-10 athena 创建
 * 	2018-01-05 athena 增加方案操作
 * 	2018-03-29 athena 增加应用赋角色操作
 * 	2018-05-14 athena 增加设置显示字段和设置流程操作
 * 	2018-08-08 shiniy 增加关注和取消关注操作
 * 	2018-08-08 athena 增加评论操作
 * 	2018-08-17 athena 增加任务转交操作
 *
 */
@Data
public class AccessModel {
	private String selectOp;			//查询，0：是；1：否
	private String saveOp;				//添加，0：是；1：否
	private String updateOp;			//修改，0：是；1：否
	private String deleteOp;			//删除，0：是；1：否
	private String detailOp;			//详情，0：是；1：否
	private String traceOp;				//跟踪，0：是；1：否
	private String opinionOp;			//意见，0：是；1：否
	private String disableOp;			//暂停，0：是；1：否，任务中对应权限是pause
	private String enableOp;			//启用，0：是；1：否，任务中对应权限是pause
	private String submitOp;			//提交，0：是；1：否
	private String backOp;				//取回，0：是；1：否
	private String retractOp;			//撤回，0：是；1：否
	private String printOp;				//打印，0：是；1：否
	private String urgeOp;				//催办，0：是；1：否
	private String copyOp;				//拷贝，0：是；1：否
	private String partUpdateOp;		//部分修改，0：是；1：否
	private String approveOp;			//审批，0：是；1：否
	private String delegateOp;			//转交，0：是；1：否
	private String flowDelegateOp;		//流程委派，0：是；1：否
	private String refuseDelegateOp;	//拒绝转交，0：是；1：否
	private String claimOp;				//接受，0：是；1：否
	private String unclaimOp;			//撤销接受，0：是；1：否
	private String tempLinkOp;			//加签，0：是；1：否
	private String returnOp;			//撤销审批，0：是；1：否
	private String retractDelegateOp;	//撤销转交，0：是；1：否
	private String flowRetractDelegateOp;//撤销流程委派，0：是；1：否
	private String fileOp;				//归档，0：是；1：否
	private String appLinkOp;			//环节维护，0：是；1：否
	private String businessFormOp;		//业务表单，0：是；1：否
	private String globalVarOp;			//设定全局变量，0：是；1：否
	private String checkSignOp;			//设定可勾选会签，0：是；1：否
	private String approvePersonOp;		//修改审批人，0：是；1：否
	private String linkVarOp;			//设定变量，0：是；1：否
	private String signPersonOp;		//修改会签人，0：是；1：否
	private String messageOp;			//设定消息，0：是；1：否
	private String judgingConOp;		//设定判断条件，0：是；1：否
	private String timingNodeOp;		//设定定时节点，0：是；1：否
	private String scriptNodeOp;		//设定脚本节点，0：是；1：否
	private String subFlowChartOp;		//查看子流程图，0：是；1：否
	private String subFlowVarOp;		//设定传入传出变量，0：是；1：否
	private String subFlowOp;			//设定子流程，0：是；1：否
	private String callMethodOp;		//设定调用方法，0：是；1：否
	private String rolePermissionOp;	//角色赋权限，0：是；1：否
	private String roleUserOp;			//角色赋用户，0：是；1：否
	private String initOp;				//初始化，0：是；1：否
	private String userRoleOp;			//用户角色，0：是；1：否
	private String userDepartOp;		//用户部门职务，0：是；1：否
	private String teamUserOp;			//组织成员，0：是；1：否
	private String addPositionOp;		//部门赋职务，0：是；1：否
	private String addMasPosOp;			//部门赋负责职务，0：是；1：否
	private String departUserOp;		//部门用户查看，0：是；1：否
	private String setDefalutOp;		//设置默认值，0：是；1：否
	private String cancelDefalutOp;		//取消默认值，0：是；1：否
	private String judgementUpdateOp;	//判定依据修改，0：是；1：否
	private String otherFinishUpdateOp;	//非检测完成桩数修改，0：是；1：否
	private String stateUpdateOp;		//状态修改，0：是；1：否
	private String pileNumberUpdateOp;	//桩号修改，0：是；1：否
	private String projectDeviceSaveOp;			//进场登记批量绑定，0：是；1：否
	private String projectDeviceDeleteOp;		//进场登记批量删除，0：是；1：否
	private String projectDeviceSelectOp;		//进场登记查询，0：是；1：否
	private String pileViewReportOp;			//桩查看现场报告，0：是；1：否
	private String pileBatchUpdateOp;			//桩批量改变状态，0：是；1：否
	private String pileImportOp;				//桩导入，0：是；1：否
	private String previewOp;					//预览，0：是；1：否
	private String setAdminOp;               	//设置管理员0：是；1：否
	private String setSysAdminOp;            	//设置系统管理员0：是；1：否
	private String companyRoleOp;				//公司赋角色，0：是；1：否
	private String companyMsgTemplateOp;		//公司赋消息模板，0：是；1：否
	private String departLookOp;
	private String formRoleOp;					//表单赋角色，0是1否
	private String companyDataDictionaryOp;		//公司赋数据字典，0：是；1：否
	private String applicationOp;				//应用，0：是；1：否
	private String groupSaveOp;					//新建分组、调整顺序、新建表单、分组修改、分组删除、表单修改、表单删除、表单复制、设置流程、设置为经常使用表单
	private String stopOp;						//暂停，0是1否
	private String adminPermissionOp;			//admin赋权专用，0是1否
	private String uploadOp;					//上传，0是1否
    private String shareConfirmOp;       		//共享数据确认
    private String schemeRoleOp;				//应用赋角色，0是1否
    private String rcmdOp;						//设为常用，0是1否
    private String cancelRcmdOp;				//取消常用，0是1否
    private String schemeFormOp;				//取消常用，0是1否
    private String shareCollOp;        	 		//设置收藏，0是1否
    private String displayFieldOp;				//设置显示字段，0是1否
    private String setFlowOp;					//设置流程，0是1否
    private String completeDelOp;            	//彻底删除0是1否
    private String retractDelOp;            	//撤回删除0是1否
    private String formUniteOp;                 //表单解绑0是1否
    private String translateOp;                 //图纸转换0是1否
    private String offLinkOp;                   //生成离线包 0 是1 否
    private String customerFunOp;               //客户功能0是1否
    private String checkOp;               		//选择0是1否
    private String cancelCollOp;       			//取消收藏0是1否
    private String retryOp;                     //重试操作0是1否
    private String downFileOp;                   //下载0是1否
    private String TransStateOp;                 //获取转换状态0是1否
    private String acceptOp;                     //采纳0是1否
    private String refuseOp;                     //拒绝0是1否
    private String exportOp;                     //导出0是1否
    private String unSetAdminOp;                 //取消管理员0是1否
    private String replyOp;                 	//消息回复0是1否
    private String payAttentionOp;              //关注0是1否
    private String cancelPayAttentionOp;     	//取消关注0是1否
    private String discussOp;					//评论0是1否
    private String setschemeGroupOp;            //设置方案分组权限
    private String taskDelegateOp;				//任务转交，0：是；1：否
    private String dealOp;						//处理

    private String bindingOp;                 	//绑定景点0是1否
    private String popOp;                 		//弹出0是1否
    private String openOp;                 		//开仓0是1否
    private String rebootOp;                 	//重启0是1否
}
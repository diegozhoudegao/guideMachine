package com.cssiot.cssutil.common.enums;

/**
 * 组件类型(-1:创建人、-2:创建时间、-3:修改人、-4:修改时间、-5:状态、0:单行文本、1:多行文本、2:数字、3:日期、4:单选、5:复选、6:金额、"
							+ "7:下拉框、8:搜索下拉框、9:复选下拉框、10:附件、11:分割线、12:子表单、"
							+ "14:按钮、15:定位")
 * @author
 *	2018-03-07 athena 创建
 *	2018-04-09 athena 增加类型对应默认长度限制，增加查询条件时对应类型
 *	2018-07-31 athena 增加类型弹窗查询
 *	2018-08-16 athena 增加超链接
 *	2018-09-11 athena 增加22:富文本编辑器组件、23:地址组件、24:bim组件
 */
public enum ElementTypeEnum {
    CREATEUSER("-1", "创建人",50,"7"),
    CREATETIME("-2", "创建时间",26,"3"),
    UPDATEUSER("-3", "修改人",50,"7"),
    UPDATETIME("-4", "修改时间",26,"3"),
    STATE("-5", "状态",5,"7"),
    TEXT("0", "单行文本",26,"0"),
    TEXTAREA("1", "多行文本",1000,"0"),
    NUMBER("2", "数字",14,"0"),
    DATETIME("3", "日期",26,"3"),
    RADIO("4", "单选",50,"7"),
    CHECKBOX("5", "复选",0,"7"),
    MONEY("6", "金额",14,"0"),
    SELECT("7", "下拉框",255,"7"),
    SEARCHSELECTOR("8", "搜索下拉框",255,"7"),
    DOUBLESELECTOR("9", "复选下拉框",0,"7"),
    FILEUPLOAD("10", "附件",0,"0"),
    SEPARATOR("11", "分割线",0,"0"),
    SUBFORM("12", "子表单",0,"0"),
    BUTTON("14", "按钮",0,"0"),
    LOCATION("15", "定位",80,"0"),
    TREE("16", "树结构",0,"16"),
    SERIAL("17", "流水号",80,"0"),
    TITLE("18", "标题",0,"0"),
    IMAGE("19","图片",0,"0"),
    DIALOGQUERY("20","弹窗查询",255,"7"),
    LINK("21", "超链接",0,"0"),
    EDITOR("22", "编辑器",0,"0"),
    ADDRESS("23", "地址",0,"0"),
    BIM("24", "bim",0,"0"),
    
    ;

    private String code;

    private String message;
    
    private int length;
    
    private String type;

    ElementTypeEnum(String code, String message,int length,String type) {
        this.code = code;
        this.message = message;
        this.length=length;
        this.type=type;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 校验组件类型是否存在
	 * @param type 组件类型
	 * @return
	 * @author 
	 *	2018-03-07 athena 创建
	 */
	 public static boolean checkDataType(String type){
		boolean flag=false;
		for(ElementTypeEnum operationTypeEnum:ElementTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}
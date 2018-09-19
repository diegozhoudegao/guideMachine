package com.cssiot.cssutil.common.utils;



/**
 * 基础公共枚举类
* @ClassName: BaseEnum 
* @Description: TODO （描述）
* @author 作者 jacking
* @date 2016-5-27 上午9:14:00
 */
public class BaseEnum {
	/**
	 * 状态枚举
	* @ClassName: STATE 
	* @Description: 
        0:正常；
		1:删除；
		2:禁用；
	* @author 作者 jacking
	* @date 2016-5-27 上午9:14:26
	 */
	 public static enum STATE {
		 	ENABLE(0, "正常"), DELETE(1,"删除"), DISABLE(2,"禁用");
			public int key;
			public String value;
			private STATE(int key, String value) {
				this.key = key;
				this.value = value;
			}
			public static STATE get(int key) {
				STATE[] values = STATE.values();
				for (STATE object : values) {
					if (object.key == key) {
						return object;
					}
				}
				return null;
			}
			public static STATE get(String value) {
				STATE[] values = STATE.values();
				for (STATE object : values) {
					if (object.value.equals(value)) {
						return object;
					}
				}
				return null;
			}
		}
	 
	 
	/**
	 *  性别枚举
	* @ClassName: SEX 
	* @Description: TODO （描述）
	* @author 作者 jacking
	* @date 2016-5-27 上午9:20:07
	 */
	 public static enum SEX {
		 	MAN(1, "男"), WOMEN(0,"女");
			public int key;
			public String value;
			private SEX(int key, String value) {
				this.key = key;
				this.value = value;
			}
			public static SEX get(int key) {
				SEX[] values = SEX.values();
				for (SEX object : values) {
					if (object.key == key) {
						return object;
					}
				}
				return null;
			}
			public static SEX get(String value) {
				SEX[] values = SEX.values();
				for (SEX object : values) {
					if (object.value.equals(value)) {
						return object;
					}
				}
				return null;
			}
		}
	 
	/**
	 * 枚举判断：是或否
	* @ClassName: isNot 
	* @Description: TODO （描述）
	* @author 作者 jacking
	* @date 2016-5-27 上午9:21:48
	 */
	 public static enum isNot {
		 	IS(1, "是"), NOT(0,"否");
			public int key;
			public String value;
			private isNot(int key, String value) {
				this.key = key;
				this.value = value;
			}
			
			public static isNot get(int key) {
				isNot[] values = isNot.values();
				for (isNot object : values) {
					if (object.key == key) {
						return object;
					}
				}
				return null;
			}
			public static isNot get(String value) {
				isNot[] values = isNot.values();
				for (isNot object : values) {
					if (object.value.equals(value)) {
						return object;
					}
				}
				return null;
			}
		}
	 /**
	  * 枚举判断：是否结案 (0:未结案 1:已结案)
	  * @ClassName: isNot 
	  * @Description: TODO （描述）
	  * @author 作者 jacking
	  * @date 2016-5-27 上午9:21:48
	  */
	 public static enum isCloseReason {
		 IS(1,"已结案"), NOT(0, "未结案");
		 public int key;
		 public String value;
		 private isCloseReason(int key, String value) {
			 this.key = key;
			 this.value = value;
		 }
		 
		 public static isCloseReason get(int key) {
			 isCloseReason[] values = isCloseReason.values();
			 for (isCloseReason object : values) {
				 if (object.key == key) {
					 return object;
				 }
			 }
			 return null;
		 }
		 public static isCloseReason get(String value) {
			 isCloseReason[] values = isCloseReason.values();
			 for (isCloseReason object : values) {
				 if (object.value.equals(value)) {
					 return object;
				 }
			 }
			 return null;
		 }
	 }
	 	
	 /**
	  * 枚举判断：特殊款项类别(0:扣款项 1:增补项)
	  * @ClassName: isNot 
	  * @Description: TODO （描述）
	  * @author 作者 jacking
	  * @date 2016-5-27 上午9:21:48
	  */
	 public static enum specialType {
		 ADD(1,"增补项"), MINUS(0, "扣款项");
		 public int key;
		 public String value;
		 private specialType(int key, String value) {
			 this.key = key;
			 this.value = value;
		 }
		 
		 public static specialType get(int key) {
			 specialType[] values = specialType.values();
			 for (specialType object : values) {
				 if (object.key == key) {
					 return object;
				 }
			 }
			 return null;
		 }
		 public static specialType get(String value) {
			 specialType[] values = specialType.values();
			 for (specialType object : values) {
				 if (object.value.equals(value)) {
					 return object;
				 }
			 }
			 return null;
		 }
	 }
	 



}

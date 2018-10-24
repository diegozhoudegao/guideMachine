package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserDao;
import com.cssiot.cssbase.modules.sys.entity.Permission;
import com.cssiot.cssbase.modules.sys.entity.PermissionDivide;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.entity.UserPermissionDivide;
import com.cssiot.cssbase.modules.sys.service.PermissionDivideService;
import com.cssiot.cssbase.modules.sys.service.PermissionService;
import com.cssiot.cssbase.modules.sys.service.UserPermissionDivideService;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.data.IsEnabledModel;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssutil.common.utils.ChkUtil;

/**
 * 员工表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Override
	public BaseDao<User> getBaseDao() {
		return userDao;
	}
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserPermissionDivideService userPermissionDivideService;
	
	@Autowired
	private PermissionDivideService permissionDivideService;
	
	@Autowired
	private PermissionService permissionService;

	/**
	 * 判断当前用户是否具有操作此功能的权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel isAccess(String userId,String content,String operate) throws Exception{
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		List<UserPermissionDivide> upd=userPermissionDivideService.findByHql("from UserPermissionDivide where userId='"+userId+"' and content='"+content+"' and keyName='"+operate+"'");
		if(ChkUtil.isEmptyAllObject(upd)){
			isEnabledModel.setCode("4008");
			PermissionDivide dc = permissionDivideService.getByHql("from PermissionDivide where content='"+content+"' and keyName='"+operate+"' and state<>'1'");
			String msg="";
			if(dc!=null){
				Permission per = permissionService.getByHql("from Permission where content='"+content+"' and state<>'1'");
				if(ChkUtil.isEmptyAllObject(per)){
					msg="失败，没有该功能的‘"+dc.getName()+"’操作权限！如果需要请联系管理员添加！";
				}else{
					msg="失败，没有‘"+per.getName()+"’功能的‘"+dc.getName()+"’操作权限！如果需要请联系管理员添加！";
				}
			}else if(dc==null){
				Permission per = permissionService.getByHql("from Permission where content='"+content+"' and state<>'1'");
				if(ChkUtil.isEmptyAllObject(per)){
					msg="失败，没有该功能的操作权限！如果需要请联系管理员添加！";
				}else{
					msg="失败，没有‘"+per.getName()+"’功能的操作权限！如果需要请联系管理员添加！";
				}
			}else{
				msg="失败，没有该功能的操作权限！如果需要请联系管理员添加！";
			}
			isEnabledModel.setMessage(msg);
			return isEnabledModel;
		}else{
			isEnabledModel.setCode("1");
			isEnabledModel.setMessage("查询成功！");
		}
		return isEnabledModel;
	}
	
	/**
	 * 查询当前用户具有哪些操作权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel hasAccess(String userId,String content) throws Exception{
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		List<UserPermissionDivide> upd=userPermissionDivideService.findByHql("from UserPermissionDivide where userId='"+userId+"' and content='"+content+"' ");
		if(!ChkUtil.isEmptyAllObject(upd)){
			List dataList=new ArrayList<>();
			for(UserPermissionDivide ud:upd){
				if(!dataList.contains(ud.getKeyName())){
					dataList.add(ud.getKeyName());
				}
			}
			isEnabledModel.setDataList(dataList);
			isEnabledModel.setMessage("查询成功！");
			isEnabledModel.setCode("1");
		}else{
			isEnabledModel.setMessage("失败，没有该功能的操作权限！");
			isEnabledModel.setCode("4008");
		}
		return isEnabledModel;
	}
}
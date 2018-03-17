package com.cn.car.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cn.car.entity.SysUser;
import com.cn.car.service.SysUserService;

import javax.annotation.Resource;

import java.util.Set;

/**
 * Created with IDEA
 * Created by ${jie.chen} on 2016/7/14.
 * Shiro�Զ�����
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    /**
     * ���ڵ�Ȩ�޵���֤��
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString() ;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo() ;
        Set<String> roleName = sysUserService.findRoles(username) ;
        Set<String> permissions = sysUserService.findPermissions(username) ;
        info.setRoles(roleName);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * ����ִ�������¼��֤
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //��ȡ�û��˺�
        String username = token.getPrincipal().toString() ;
        SysUser user = sysUserService.findUserByUsername(username) ;
        if (user != null){
            //����ѯ�����û��˺ź������ŵ� authenticationInfo���ں����Ȩ���жϡ���������������realName��
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),
                    "a") ;
            return authenticationInfo ;
        }else{
            return  null ;
        }
    }
}


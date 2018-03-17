package com.cn.car.service;

import com.cn.car.entity.SysUser;
import java.util.Set;


/**
 * Created with IDEA
 * Created by ${jie.chen} on 2016/7/14.
 * ��̨��¼Service
 */
public interface SysUserService {
    /**
     * Shiro�ĵ�¼��֤��ͨ���û�����ѯ�û���Ϣ
     * @param username
     * @return
     */
    public SysUser findUserByUsername(String username) ;

    /**
     * �����˺Ų��ҽ�ɫ����
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) ;

    /**
     * �����˺Ų���Ȩ��
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) ;
}


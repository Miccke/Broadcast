package com.cn.car.util;



import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cn.car.entity.RefreshToken;
import com.cn.car.entity.WeChatUserInfo;
@Component
public class RefreshTokenUtil {
	/**
     * ��ȡ��ҳ��Ȩƾ֤
     * 
     * @param appId �����˺ŵ�Ψһ��ʶ
     * @param appSecret �����˺ŵ���Կ
     * @param code
     * @return WeixinAouth2Token
     */
	private static Logger log = Logger.getLogger(RefreshTokenUtil.class);

    public static RefreshToken getRefreshToken(String appId, String appSecret, String code) {
        RefreshToken wat = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // ��ȡ��ҳ��Ȩƾ֤
        JSONObject jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new RefreshToken();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("��ȡ�û���Ϣʧ�� errcode:{\""+errorCode+"\"} errmsg:{+\""+errorMsg+"\"}");
            }
        }
        return wat;
    }
    /**
     * ͨ����ҳ��Ȩ��ȡ�û���Ϣ
     * 
     * @param accessToken ��ҳ��Ȩ�ӿڵ���ƾ֤
     * @param openId �û���ʶ
     * @return WeChatUserInfo
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
	public static WeChatUserInfo getWeChatUserInfo(String accessToken, String openId) {
        WeChatUserInfo weChatUserInfo = null;
        // ƴ�������ַ
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // ͨ����ҳ��Ȩ��ȡ�û���Ϣ
        JSONObject jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
            	weChatUserInfo = new WeChatUserInfo();
                // �û��ı�ʶ
            	weChatUserInfo.setOpenId(jsonObject.getString("openid"));
                // �ǳ�
            	weChatUserInfo.setNickname(jsonObject.getString("nickname"));
                // �Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
            	weChatUserInfo.setSex(jsonObject.getInt("sex"));
                // �û����ڹ���
            	weChatUserInfo.setCountry(jsonObject.getString("country"));
                // �û�����ʡ��
            	weChatUserInfo.setProvince(jsonObject.getString("province"));
                // �û����ڳ���
            	weChatUserInfo.setCity(jsonObject.getString("city"));
                // �û�ͷ��
            	weChatUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // �û���Ȩ��Ϣ
            	weChatUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
            	weChatUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("��ȡ�û���Ϣʧ�� errcode:{\""+errorCode+"\"} errmsg:{+\""+errorMsg+"\"}");
            }
        }
        return weChatUserInfo;
    }
}

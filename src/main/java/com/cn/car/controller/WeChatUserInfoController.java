package com.cn.car.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.car.entity.RefreshToken;
import com.cn.car.entity.WeChatUserInfo;
import com.cn.car.util.RefreshTokenUtil;
import com.cn.car.util.UrlEncodeUTF8;

@Controller
@RequestMapping("weChatUser")
public class WeChatUserInfoController {
	
	static Properties prop = new Properties();
	static {
        InputStream in = WeChatUserInfoController.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static final String redirect_uri = "http://www.miccke.top/weChatUser/";
	public static final String userURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+prop.getProperty("AppID")+"&redirect_uri="+UrlEncodeUTF8.urlEncodeUTF8(redirect_uri)+"&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
	public Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping("/clock")
	public void redirectPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		System.out.println(userURL);
		log.info(userURL);
		response.sendRedirect(userURL);
	}
	
	
	@RequestMapping("/")
	@ResponseBody
	public void getWeChatUserInfo(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// �û�ͬ����Ȩ���ܻ�ȡ��code
		String code = request.getParameter("code");
		String state = request.getParameter("state");

		// �û�ͬ����Ȩ
		if (!"authdeny".equals(code)) {
			// ��ȡ��ҳ��Ȩaccess_token
			RefreshToken refreshToken = RefreshTokenUtil.getRefreshToken(prop.getProperty("AppID"), prop.getProperty("AppSecret"),code);
			// ��ҳ��Ȩ�ӿڷ���ƾ֤
			String accessToken = refreshToken.getAccessToken();
			// �û���ʶ
			String openId = refreshToken.getOpenId();
			// ��ȡ�û���Ϣ
			WeChatUserInfo weChatUserInfo = RefreshTokenUtil.getWeChatUserInfo(
					accessToken, openId);

			// ����Ҫ���ݵĲ���
			request.setAttribute("weChatUserInfo", weChatUserInfo);
			request.setAttribute("state", state);
			session.setAttribute("weChatUserInfo", weChatUserInfo);
		}
		// ��ת��personal.jsp
		request.getRequestDispatcher("../WeChat/personal.jsp").forward(request, response);

	}
	
	
}

package com.cn.car.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.car.util.Decript;

/**
 * Servlet implementation class WeChatServlet
 */
public class WeChatServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// token
	private final String token = "m1209614483";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("开始签名校验");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		ArrayList<String> array = new ArrayList<String>();
		array.add(signature);
		array.add(timestamp);
		array.add(nonce);
		// 排序
		String sortString = sort(token, timestamp, nonce);
		// 加密
		String mytoken = Decript.SHA1(sortString);
		// 校验签名
		if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
			System.out.println("签名校验通过。");
			response.getWriter().println(echostr); // 如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
		} else {
			
			System.out.println("签名校验失败。");
		}
	}

	/**
	 * * 排序方法 * @param token * @param timestamp * @param nonce * @return
	 */
	public static String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sbuilder = new StringBuilder();
		for (String str : strArray) {
			sbuilder.append(str);
		}
		return sbuilder.toString();
	}

}

package com.cn.car.util;

public class Test {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		String abnormal = "����������", nodeName, option1 = "����";
		if (abnormal.equals("����������")) {
			System.out.println(abnormal);
			if (option1.equals("����") || option1.equals("����")) {
				System.out.println(option1);
				nodeName = "fork 5";
			}
		}
		if ((abnormal.equals("ԭ����") || abnormal.equals("��׼��")
				|| abnormal.equals("��Э�ӹ���") || abnormal.equals("����������")) && (option1.equals("�ز�") || option1.equals("�˻�"))) {
			if (option1.equals("�ز�")) {
				nodeName = "fork 9";
			} else if (option1.equals("�˻�")) {
				nodeName = "fork 7";
			}
		} else if (abnormal.equals("����������") && (option1.equals("����") || option1.equals("����"))) {
			System.out.println(option1);
			nodeName = "fork 5";
			
		} else if (abnormal.equals("���ڳ�����") && option1.equals("�ز�")) {
			nodeName = "fork 8";
		} else if (abnormal.equals("���ڳ�����")
				&& (option1.equals("����") || option1.equals("����"))) {
			nodeName = "fork 5";
		} else if (abnormal.equals("װ�䲿�߲�")) {
			nodeName = "fork 6";
		}
	}
}

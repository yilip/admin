package com.lip.admin.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.lip.core.service.SysConfigCache;
import com.lip.admin.common.model.MessageTemplateModel;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.lip.admin.common.model.SysConfigModel;
import com.lip.core.utils.SpringUtil;

public class MessageUtils {
	private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

	private static SqlSessionTemplate sqlSession = (SqlSessionTemplate) SpringUtil.getBean("sqlSessionTemplate");

	private static final String mapperSpace = "com.lip.admin.common.model.SysConfigModelMapper.";

	private static String sendSMS(String uri, String cdkey, String password, String message, String mobileNumber) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(uri);
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("cdkey", cdkey), new NameValuePair("password", password),
				new NameValuePair("phone", mobileNumber), new NameValuePair("message", message),
				new NameValuePair("addserial", ""), new NameValuePair("seqid", ""),
				new NameValuePair("smspriority", "5") };
		post.setRequestBody(data);
		try {
			client.executeMethod(post);
			String result = new String(post.getResponseBodyAsString().getBytes("UTF-8"));
			result = result.replace("\r\n", "");
			return result;
		} catch (HttpException e) {
			throw new RuntimeException("消息发送失败:" + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("消息发送失败:" + e.getMessage());
		}
	}

	/**
	 * 微网短信接口
	 * 
	 * @param uri
	 * @param cdkey
	 * @param password
	 * @param message
	 * @param mobileNumber
	 * @return
	 */
	private static String sendSMS2(String uri, String cdkey, String password, String message, String mobileNumber) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(uri);
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("sname", cdkey), new NameValuePair("spwd", password),
				new NameValuePair("sdst", mobileNumber), new NameValuePair("smsg", message),
				new NameValuePair("scorpid", ""), new NameValuePair("sprdid", "1012888") };
		post.setRequestBody(data);
		try {
			client.executeMethod(post);
			String result = new String(post.getResponseBodyAsString().getBytes("UTF-8"));
			result = result.replace("\r\n", "");
			return result;
		} catch (HttpException e) {
			throw new RuntimeException("消息发送失败:" + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("消息发送失败:" + e.getMessage());
		}
	}
	/**
	 * 微网短信接口
	 * 
	 * @param uri
	 * @param cdkey
	 * @param password
	 * @param message
	 * @param mobileNumber
	 * @return
	 */
	private static String sendSMSForVoice(String uri, String cdkey, String password, String message, String mobileNumber) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(uri);
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("sname", cdkey), new NameValuePair("spwd", password),
				new NameValuePair("sdst", mobileNumber), new NameValuePair("smsg", message),
				new NameValuePair("scorpid", ""), new NameValuePair("sprdid", "1012828") };
		post.setRequestBody(data);
		try {
			client.executeMethod(post);
			String result = new String(post.getResponseBodyAsString().getBytes("UTF-8"));
			result = result.replace("\r\n", "");
			return result;
		} catch (HttpException e) {
			throw new RuntimeException("消息发送失败:" + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("消息发送失败:" + e.getMessage());
		}
	}

	/**
	 * Description:
	 *
	 * @param nodeCode:消息节点编号
	 * @param params:消息节点参数Map列表
	 * @param mobileNumber:短信号码
	 * 
	 * @return boolean
	 * @throws @Author
	 *             lip Create Date: 2015-2-27 上午11:53:55
	 */
	public static boolean sendSMS(String nodeCode, Map<String, String> params, String mobileNumber) {
		MessageTemplateModel messageTpl = sqlSession
				.selectOne("com.lip.admin.common.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
		String content = messageTpl.getTplContent();
		if(params==null)
		{
			params=new HashMap<>();
		}
		if(!params.containsKey("date"))
		{
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			params.put("date", format.format(new Date()));
		}
		if (params != null) {
			for (String key : params.keySet()) {
				content = content.replace("#{" + key + "}", params.get(key));
			}
			logger.info(content);
		}
		return sendSMS(content, mobileNumber);
	}

	/**
	 * 
	 * Description: 发送邮件
	 *
	 * @param
	 * @return boolean
	 * @throws @Author
	 *             lip Create Date: 2015-3-2 下午3:20:35
	 */
	public static boolean sendEmail(String nodeCode, Map<String, String> params, String toAddress, String subject) {
		MessageTemplateModel messageTpl = sqlSession
				.selectOne("com.lip.admin.common.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
		String content = messageTpl.getTplContent();
		for (String key : params.keySet()) {
			content = content.replace("#{" + key + "}", params.get(key));
		}
		logger.info(content);
		return sendMail(toAddress, subject, content);
	}

	/**
	 * 
	 * Description: 获取发送内容
	 *
	 * @param
	 * @return String
	 * @throws @Author
	 *             lip Create Date: 2015-2-27 下午4:45:42
	 */
	public static String getContent(String nodeCode, Map<String, String> params) {
		MessageTemplateModel messageTpl = sqlSession
				.selectOne("com.lip.admin.common.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
		String content = messageTpl.getTplContent();
		for (String key : params.keySet()) {
			content = content.replace("#{" + key + "}", params.get(key));
		}
		return content;
	}

	/**
	 * Description: 发送短信
	 *
	 * @return void
	 * @throws @Author
	 *             Lip
	 */
	public static boolean sendSMS(String content, String mobileNumber) {
		String exchangeId = SysConfigCache.getInstance().findValue("exchange_id");
		String url = SysConfigCache.getInstance().findValue("sms_1_server");
		String account = SysConfigCache.getInstance().findValue("sms_1_account");
		String password = SysConfigCache.getInstance().findValue("sms_1_password");
		logger.info("send message:{}",mobileNumber+":"+content);
		logger.info("message url:{}", url);
		logger.info("message account:{}", account);
		String resultCode = "";
        if ("1".equals(exchangeId)) {
            resultCode = sendSMS(url, account, password, content, mobileNumber);
            logger.warn("message return:{}", resultCode);
            try {
                Document document = DocumentHelper.parseText(resultCode);
                Element root = document.getRootElement();
                resultCode = root.element("error").getText();
            } catch (DocumentException e) {
                logger.error("发送短信异常", e);
                return false;
            }
        }
        else // 使用微网短信接口
        {
        	if(!content.contains("【"))
        	{
        		content+="【上文艺术品邮币平台】";
        	}
            resultCode = sendSMS2(url, account, password, content, mobileNumber);
            logger.warn("message return :{}", resultCode);
            try {
                Document document = DocumentHelper.parseText(resultCode);
                Element root = document.getRootElement();
                resultCode = root.element("State").getText();
            } catch (DocumentException e) {
                logger.error("发送短信异常", e);
                return false;
            }
        }
		if (Long.valueOf(resultCode) == 0) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * Description: 发送语音短信
	 *
	 * @return void
	 * @throws @Author
	 *             Lip
	 */
	public static boolean sendSMSForVoice(String content, String mobileNumber) {
		String exchangeId = SysConfigCache.getInstance().findValue("exchange_id");
		String url = SysConfigCache.getInstance().findValue("sms_1_server");
		String account = SysConfigCache.getInstance().findValue("sms_1_account");
		String password = SysConfigCache.getInstance().findValue("sms_1_password");
		logger.info("send message:{}",mobileNumber+":"+content);
		logger.info("message url:{}", url);
		logger.info("message account:{}", account);
		String resultCode = "";
		if ("2".equals(exchangeId)) // 使用微网短信接口
		{
			resultCode = sendSMSForVoice(url, account, password, content, mobileNumber);
			logger.warn("message return :{}", resultCode);
			try {
				Document document = DocumentHelper.parseText(resultCode);
				Element root = document.getRootElement();
				resultCode = root.element("State").getText();
			} catch (DocumentException e) {
				logger.error("发送短信异常", e);
				return false;
			}
		} else {
			resultCode = sendSMS(url, account, password, content, mobileNumber);
			logger.warn("message return:{}", resultCode);
			try {
				Document document = DocumentHelper.parseText(resultCode);
				Element root = document.getRootElement();
				resultCode = root.element("error").getText();
			} catch (DocumentException e) {
			    logger.error("发送短信异常", e);
				return false;
			}
		}
		if (Long.valueOf(resultCode) == 0) {
			return true;
		} else {
			return false;
		}

	}
	public static void main(String[] args) {
		String uri1 = "http://sdk4rptws.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
		String userName1 = "SDK-NVF-010-00010";
		String userPassword1 = "SDK-NVF-010-00010Ecf21-80";
		String content = "尊敬的客户您好，欢迎使用东岳金融。【东岳金融】";
		String mobileNumber = "13552826035";
		String resultCode = sendSMS(uri1, userName1, userPassword1, content, mobileNumber);
		logger.info(resultCode);
	}

	/**
	 * Description: 发送携带附件和图片的邮件
	 *
	 * @param toAddress
	 *            收件人邮箱，多个邮箱以“;”分隔
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return boolean
	 * @throws @Author
	 *             lip Create Date: 2015-2-3 下午6:05:30
	 */
	public static boolean sendMail(String toAddress, String subject, String content) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		String host = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName", "email_stmp_server"))
				.getCode();
		String account = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName", "email_account"))
				.getCode();
		String name = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName", "email_name"))
				.getCode();
		String userPassword = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName",
				"email_password")).getCode();

		try {
			// 设定mail server
			senderImpl.setHost(host);

			// 建立邮件消息
			MimeMessage mailMessage = senderImpl.createMimeMessage();

			MimeMessageHelper messageHelper = null;
			messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
			// 设置发件人邮箱
			messageHelper.setFrom(name + " <" + account + ">");

			// 设置收件人邮箱
			String[] toEmailArray = toAddress.split(";");
			List<String> toEmailList = new ArrayList<String>();
			if (null == toEmailArray || toEmailArray.length <= 0) {
				throw new RuntimeException("收件人邮箱不得为空！");
			} else {
				for (String s : toEmailArray) {
					s = StringUtils.trimToEmpty(s);
					if (!s.equals("")) {
						toEmailList.add(s);
					}
				}
				if (null == toEmailList || toEmailList.size() <= 0) {
					throw new RuntimeException("收件人邮箱不得为空！");
				} else {
					toEmailArray = new String[toEmailList.size()];
					for (int i = 0; i < toEmailList.size(); i++) {
						toEmailArray[i] = toEmailList.get(i);
					}
				}
			}
			messageHelper.setTo(toEmailArray);
			// 邮件主题
			messageHelper.setSubject(subject);
			// true 表示启动HTML格式的邮件
			messageHelper.setText(content, true);

			senderImpl.setUsername(account); // 根据自己的情况,设置username
			senderImpl.setPassword(userPassword); // 根据自己的情况, 设置password
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
			prop.put("mail.smtp.timeout", "25000");
			senderImpl.setJavaMailProperties(prop);

			// 发送邮件
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	static class SimpleAuthenticator extends Authenticator {
		private String user;
		private String pwd;

		public SimpleAuthenticator(String user, String pwd) {
			this.user = user;
			this.pwd = pwd;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pwd);
		}
	}

	/**
	 * Description: 发送携带附件和图片的邮件
	 *
	 * @param toAddress
	 *            收件人邮箱，多个邮箱以“;”分隔
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachments
	 *            邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
	 * @param pictures
	 *            邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
	 * @return boolean
	 * @throws @Author
	 *             lip Create Date: 2015-2-3 下午6:05:30
	 */
	public boolean sendMail(String toAddress, String subject, String content, Map<String, String> attachments,
			Map<String, String> pictures) {
		String host = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName", "email_host"))
				.getCode();
		String userName = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName",
				"email_from_username")).getCode();
		String userPassword = ((SysConfigModel) sqlSession.selectOne(mapperSpace + "selectByDisplayName",
				"email_from_password")).getCode();

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		try {
			// 设定mail server
			senderImpl.setHost(host);

			// 建立邮件消息
			MimeMessage mailMessage = senderImpl.createMimeMessage();

			MimeMessageHelper messageHelper = null;
			messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
			// 设置发件人邮箱
			messageHelper.setFrom(userName);

			// 设置收件人邮箱
			String[] toEmailArray = toAddress.split(";");
			List<String> toEmailList = new ArrayList<String>();
			if (null == toEmailArray || toEmailArray.length <= 0) {
				throw new RuntimeException("收件人邮箱不得为空！");
			} else {
				for (String s : toEmailArray) {
					s = StringUtils.trimToEmpty(s);
					if (!s.equals("")) {
						toEmailList.add(s);
					}
				}
				if (null == toEmailList || toEmailList.size() <= 0) {
					throw new RuntimeException("收件人邮箱不得为空！");
				} else {
					toEmailArray = new String[toEmailList.size()];
					for (int i = 0; i < toEmailList.size(); i++) {
						toEmailArray[i] = toEmailList.get(i);
					}
				}
			}
			messageHelper.setTo(toEmailArray);

			// 邮件主题
			messageHelper.setSubject(subject);

			// true 表示启动HTML格式的邮件
			messageHelper.setText(content, true);

			// 添加图片
			if (null != pictures) {
				for (Iterator<Map.Entry<String, String>> it = pictures.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						throw new RuntimeException("请确认每张图片的ID和图片地址是否齐全！");
					}

					File file = new File(filePath);
					if (!file.exists()) {
						throw new RuntimeException("图片" + filePath + "不存在！");
					}

					FileSystemResource img = new FileSystemResource(file);
					messageHelper.addInline(cid, img);
				}
			}

			// 添加附件
			if (null != attachments) {
				for (Iterator<Map.Entry<String, String>> it = attachments.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");
					}

					File file = new File(filePath);
					if (!file.exists()) {
						throw new RuntimeException("附件" + filePath + "不存在！");
					}

					FileSystemResource fileResource = new FileSystemResource(file);
					messageHelper.addAttachment(cid, fileResource);
				}
			}

			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
			prop.put("mail.smtp.timeout", "25000");
			// 添加验证
			SimpleAuthenticator auth = new SimpleAuthenticator(userName, userPassword);

			Session session = Session.getDefaultInstance(prop, auth);
			senderImpl.setSession(session);

			// 发送邮件
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

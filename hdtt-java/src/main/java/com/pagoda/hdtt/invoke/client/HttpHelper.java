package com.pagoda.hdtt.invoke.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.pagoda.hdtt.common.exception.InvokeException;
import com.pagoda.hdtt.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Slf4j
public class HttpHelper {
	
	private static final int OK = 200;
	
	/**
	 * GET请求
	 * 
	 * @param apiUrl
	 * @param paramStr
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String apiUrl, String paramStr)  {

		log.info("外部接口URL  ==={}", apiUrl);
		log.info("外部接口入参  ==={}", paramStr);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(apiUrl);
		Response response = target.register(HttpAuthenticationFeature.basicBuilder().credentials("", "").build())
				.register(JacksonJsonProvider.class).request().buildGet().invoke();

		if (response.getStatus() != OK) {
			throw new InvokeException("请求失败！请求返回码：" + response.getStatus());
		}
		String resJson = (String) response.readEntity(String.class);
		response.close();
		log.info("外部接口出参  ==={}", resJson);
		return resJson;
	}

	/**
	 * POST请求
	 * 
	 * @param apiUrl
	 * @param paramStr
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String apiUrl, String paramStr) {
		return sendPostWithHeader(apiUrl,paramStr,null);
	}

	/**
	 * POST请求,需要设置header的时候调用
	 *
	 * @param apiUrl
	 * @param paramStr
	 * @return
	 * @throws Exception
	 */
	public static String sendPostWithHeader(String apiUrl, String paramStr, Map<String,Object> headerMap) {

		Client client = ClientBuilder.newClient();


		WebTarget target = client.target(apiUrl);
		Invocation.Builder request = target.register(HttpAuthenticationFeature.basicBuilder().credentials("", "").build())
				.register(JacksonJsonProvider.class).request();
		if(BeanUtil.checkIsNotEmpty(headerMap)){
			for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
				request.header(entry.getKey(),entry.getValue());
			}
		}
		log.info("外部接口URL  ==={}", apiUrl);
		log.info("外部接口入参  ==={}", paramStr);
		Response response = request.buildPost(Entity.entity(paramStr, MediaType.APPLICATION_JSON)).invoke();

		if (response.getStatus() != OK) {
			throw new InvokeException("请求失败！请求返回码：" + response.getStatus());
		}
		String resJson = response.readEntity(String.class);
		response.close();
		log.info("外部接口出参  ==={}", resJson);
		return resJson;
	}


}

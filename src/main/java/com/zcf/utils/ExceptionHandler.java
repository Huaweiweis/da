package com.zcf.utils;

import com.zcf.common.json.Body;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenkang
 * 
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if (isAjax(request)) {
				response.setHeader("content-type", "application/json;charset=UTF-8");
				writer.println(JsonUtils.objectToJson(Body.newInstance(201, ex.toString())));
			} else {
				response.setHeader("content-type", "text/html;charset=UTF-8");
				outScript(writer, ex);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.flush();
				writer.close();
			}
		}
		return null;
	}

	/**
	 * 是否是Ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}

	private void outScript(PrintWriter writer, Exception ex) {
		writer.println("<script>");
		writer.println("history.back();");
		writer.println(String.format("alert('%s');", ex.getMessage()));
		writer.println("</script>");
	}
}

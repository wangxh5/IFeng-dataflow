package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.ESClientHelperService;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/getLocTime")
public class LocRecognizeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(LocRecognizeServlet.class);

	private ESClientHelperService eSClientHelperService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LocRecognizeServlet() {
		super();
		try {
			eSClientHelperService = new ESClientHelperService();
		} catch (Exception e) {
			logger.error("create homeServlet failed,", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("处理Get()请求。。。");

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
//		PrintWriter out = response.getWriter();
//		out.print("<strong>Hello Servlet!</strong>");

		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			logger.info(startTime);
			logger.info(endTime);
			System.out.println(startTime);
			System.out.println(endTime);

			String respStr = eSClientHelperService.getResponse(startTime, endTime);
			
//			JSONObject json= JSONObject.fromObject(respStr); 
			System.out.println("respStr = " + respStr);
			if (respStr == null) {
				logger.error("get data is null");
				response.getWriter().append("error");
			} else {
				response.getWriter();
			}

		} catch (Exception e) {

			logger.error("get used time error,", e);
		}

	}
}

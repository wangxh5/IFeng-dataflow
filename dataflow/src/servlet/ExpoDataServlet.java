package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.ExpoDataService;

/**
 * Servlet implementation class DataStatisticsServlet
 */
@WebServlet("/expo")
public class ExpoDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ExpoDataServlet.class);
	private static ExpoDataService expoDataService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExpoDataServlet() {
		super();
		try {
			expoDataService = new ExpoDataService();
		} catch (Exception e) {
			logger.error("create ExpoDataServlet failed,", e);
		}
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("处理Get()请求。。。");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			System.out.println(startDate);
			System.out.println(endDate);

			String respStr = expoDataService.getData(startDate, endDate);
			System.out.println(respStr);
			// JSONObject json= JSONObject.fromObject(respStr);
			System.out.println("respStr = " + respStr);
			if (respStr == null) {
				logger.error("get data is null");
				response.getWriter().append("error");
			} else {
				response.getWriter().append(respStr);
			}
		} catch (Exception e) {
			logger.error("get data error,", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

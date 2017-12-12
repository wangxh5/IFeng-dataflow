package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.DataStatisticsService;

/**
 * Servlet implementation class DataStatisticsServlet
 */
@WebServlet("/data/statistics")
public class DataStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(DataStatisticsServlet.class);
	private static DataStatisticsService dataStatisticsService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataStatisticsServlet() {
		super();
		try {
			dataStatisticsService = new DataStatisticsService();
		} catch (Exception e) {
			logger.error("create DataStatisticsServlet failed,", e);
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

			String respStr = dataStatisticsService.getData(startDate, endDate);
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

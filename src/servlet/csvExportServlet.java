package servlet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;
import model.V_Timer;

@WebServlet("/csvExportServlet")
public class csvExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// 検索ユーザーID
		int user_id = Integer.parseInt(request.getParameter("userid"));
		UserDAO uDao = new UserDAO();
		User user = uDao.findByUserId(user_id);
		String user_name = user.getUserName();

		List<V_Timer> wMemory = (List<V_Timer>)session.getAttribute("wmemory");
		V_Timer memoryMonth = (V_Timer)session.getAttribute("memorymonth");
		// 出力データの日数
		int size = wMemory.size();
		// 出力するcsvファイル名
		String csvName = user_name +"("+ memoryMonth.getSmonth() +")勤務履歴.csv";

		try {
			FileWriter fw = new FileWriter(csvName);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
//			// 出力ファイル作成
//			FileOutputStream fos = new FileOutputStream(csvName+".csv");
//			// 文字コード変換
//			OutputStreamWriter osw = new OutputStreamWriter(fos, "Shift-JIS");
//			// 文字列バッファリング
//			BufferedWriter bw = new BufferedWriter(osw);
//			// テキスト出力
//			PrintWriter pw = new PrintWriter(bw);

			response.setContentType("text/csv; charset=UTF-8;");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(csvName.getBytes("UTF-8"),"ISO-8859-1"));
			pw = response.getWriter();

			// ヘッダー
			String[] header = {"勤務日", "勤務開始", "勤務終了", "勤務時間", "残業時間", "勤務内容"};
			for (int i=0; i<5; i++) {
				pw.print(header[i]);
				pw.print(",");
			}
			pw.print(header[5]);
			pw.println();

			// csv記載内容
			String[] day = new String[size];
			String[] start = new String[size];
			String[] stop = new String[size];
			String[] length = new String[size];
			String[] over = new String[size];
			String[] detail = new String[size];

			int ct = 0;
			// csv記載内容を配列化
			for (V_Timer vt : wMemory) {
				String dayStr = vt.getWorkDay().toString();
				String startStr = vt.getWorkStart();
				String stopStr = vt.getWorkStop();
				String lengthStr = vt.getWorkOver();
				String overStr = vt.getWorkOver();
				String detailStr = vt.getWorkDetail();
				day[ct] = dayStr;
				start[ct] = startStr;
				stop[ct] = stopStr;
				length[ct] = lengthStr;
				over[ct] = overStr;
				detail[ct] = detailStr;

				pw.print(day[ct]);
				pw.print(",");
				pw.print(start[ct]);
				pw.print(",");
				pw.print(stop[ct]);
				pw.print(",");
				pw.print(length[ct]);
				pw.print(",");
				pw.print(over[ct]);
				pw.print(",");
				pw.print(detail[ct]);
				pw.println();

				System.out.println("day["+ct+"]: "+ dayStr);
				ct++;
			}

			pw.flush();
			System.out.println("csvファイル出力完了");
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
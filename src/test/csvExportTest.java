package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.UserDAO;
import dao.V_TimerDAO;
import model.User;
import model.V_Timer;

public class csvExportTest {
	public static void main(String[] args) {

		UserDAO uDao = new UserDAO();
		V_TimerDAO vDao = new V_TimerDAO();

		User user = uDao.findByUserId(1);
		String user_name = user.getUserName();

		String sMonth = "2022年5月";
		// 出力するcsvファイル名
		String filename = user_name + "(" + sMonth + ")勤務履歴.csv";

		List<V_Timer> wMemory = vDao.findVtimerByDay(1, 2022, 5);

		// 出力データの日数
		int size = wMemory.size();

		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
	//		// 出力ファイル作成
	//		FileOutputStream fos = new FileOutputStream(csvName+".csv");
	//		// 文字コード変換
	//		OutputStreamWriter osw = new OutputStreamWriter(fos, "Shift-JIS");
	//		// 文字列バッファリング
	//		BufferedWriter bw = new BufferedWriter(osw);
	//		// テキスト出力
	//		PrintWriter pw = new PrintWriter(bw);

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
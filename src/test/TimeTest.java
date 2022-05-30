package test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.V_TimerDAO;
import model.V_Timer;

public class TimeTest {

	public static void main(String[] args) {
		// TimeAPI
		LocalDate today = LocalDate.now();
		LocalDate date1 = LocalDate.of(2022, 1, 31);
		// .parseで文字列をLocalDate型に変換
		LocalDate date2 = LocalDate.parse("2022-05-01");

		LocalTime now = LocalTime.now();
		LocalTime time1 = LocalTime.of(9, 00, 00);
		LocalTime time2 = LocalTime.of(18, 00, 00);

		// LocalDate型をsql.Date型に変換
		java.sql.Date sqlDate = java.sql.Date.valueOf(today);
		// sql.Date型をString型に変換
		String strDate = sqlDate.toString();
		// sql.Date型をLocalDate型に変換
		LocalDate lcDate = sqlDate.toLocalDate();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("y年MM月");
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("a hh:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");

		String strToday = dtf.format(today);
		String strTime1 = dtf1.format(now);
		String strTime2 = dtf2.format(now);

		// LocalDate型("yyyy-MM-dd")
		System.out.println("today: "+ today);
		System.out.println("date1: "+ date1);
		System.out.println("date2: "+ date2);

		System.out.println("strDate: "+ strDate);
		System.out.println("strTime1: "+ strTime1);
		System.out.println("strTime2: "+ strTime2);
		System.out.println("strToday: "+strToday);

		LocalTime localTime1 = LocalTime.parse(strTime2, dtf2);
		System.out.println("localTime1: " +localTime1);

		// 型(LocalTime)判定
		System.out.println(time1.getClass());
		System.out.println(sqlDate.getClass());
		System.out.println(strDate.getClass());
		System.out.println(lcDate.getClass());
		System.out.println(localTime1.getClass());

		// 経過時間Duration
		Duration d1 = Duration.between(time1, now);
		Duration d2 = d1.minusMinutes(90);
		Duration d3 = Duration.between(time1, time2);
		Duration over_work = d2.minus(d3);

		System.out.println("d1: "+ d1);

		if (d2.isNegative()) {
			d2 = Duration.ZERO;
		}
		if (over_work.isNegative()) {
			over_work = Duration.ZERO;
		}

		int workhour = d1.toHoursPart();
		int workminute = d1.toMinutesPart();
		String work_length = workhour +"時間"+workminute+"分";
		System.out.println(work_length);

		String st = "90分";
		String[] times = st.split("分");
		for (String t : times) {
			System.out.println("t: "+ t);
		}

		int overhour = over_work.toHoursPart();
		int overminute = over_work.toMinutesPart();
		System.out.println(overhour +"時間"+overminute+"分");

		// 年月ごとの勤務記録一覧
		V_TimerDAO vDao = new V_TimerDAO();
		List<V_Timer> wMemory = vDao.findVtimerByDay(1, 2022, 5);
		Duration wc = Duration.ZERO;

		System.out.println("勤務日数: "+ wMemory.size());

		String[] time = new String[wMemory.size()];
		int size = 0;

		for (V_Timer vt : wMemory) {
			Duration wl = Duration.between(LocalTime.parse(vt.getWorkStart()), LocalTime.parse(vt.getWorkStop()));
			Duration length = wl.minusMinutes(60);
			wc = wc.plus(length);
			int wh = length.toHoursPart();
			int wm = length.toMinutesPart();
			System.out.println("実勤務時間: "+ wh +"時間"+ wm +"分");

			// csv記載内容
			String vtStr = vt.getWorkDay().toString();
			System.out.println("time["+size+"]: "+ vtStr);
			time[size] = vtStr;
			size++;
		}
		int cd = (int) wc.toDaysPart();
		int cl = wc.toHoursPart();
		int cm = wc.toMinutesPart();
		System.out.println("総勤務時間: "+(cd*24+cl)+"時間"+cm+"分");
	}

}
package bo;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.WorkDefault;
import model.WorkStart;

// 勤務開始時間と終了時間から、休憩時間を除いた総勤務時間と残業時間を求める
public class LengthLogic {
	public String[] getWorkLength (LocalTime start_time, LocalTime stop_time, WorkStart ws, WorkDefault wd) {
		String [] lengths = new String[2];

		// LocalDate型とString型の変換
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

		String strSt = dtf.format(stop_time);
		System.out.println("勤務終了時間: "+ strSt);

		// 通常の勤務時間と休憩時間
		LocalTime dwakeup = LocalTime.parse(wd.getWakeup());
		LocalTime dsleep = LocalTime.parse(wd.getSleep());
		int dRest = wd.getRest();

		// 勤務時間を求める
		Duration d1 = Duration.between(start_time, stop_time);
		// 休憩時間を除いた勤務時間
		Duration d2 = d1.minusMinutes(dRest);
		// 通常の総勤務時間
		Duration d3 = Duration.between(dwakeup, dsleep);
		// 残業時間
		Duration over_work = d1.minus(d3);

		// 勤務時間が休憩時間より短いとき、0とみなす
		if (d2.isNegative()) {
			d2 = Duration.ZERO;
		}
		if (over_work.isNegative()) {
			over_work = Duration.ZERO;
		}

		int workhour = d2.toHoursPart();
		int workmin = d2.toMinutesPart();

		String work_length = workhour + "時間" + workmin + "分";

		int over_hour = over_work.toHoursPart();
		int over_min = over_work.toMinutesPart();
		int over_minute = (over_hour * 60) + over_min;

		String over_length = over_minute +"分";


		lengths[0] = work_length;
		lengths[1] = over_length;

		return lengths;
	}

}
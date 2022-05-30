package bo;

public class DetailLogic {
	public String execute(String detail) {

		// 入力チェック
		StringBuffer sb = new StringBuffer();

		// ユーザー名チェック
		if (detail.length() <= 0) {
			sb.append("仕事内容を入力してください。<br>");
		} else if (detail.length() > 51) {
			sb.append("仕事内容は50文字以内で入力してください。<br>");
		}
	return sb.toString();
	}
}

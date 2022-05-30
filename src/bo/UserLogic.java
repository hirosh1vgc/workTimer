package bo;

import dao.UserDAO;
import model.User;

public class UserLogic {
	public String execute(User user, String op) {

		// 入力チェック
		StringBuffer sb = new StringBuffer();

		// ユーザー名チェック
		if (user.getUserName().length() <= 0) {
			sb.append("ユーザー名を入力してください<br>");
		} else if (user.getUserName().length() > 10) {
			sb.append("ユーザー名は10文字以内で入力してください<br>");
		}

		// パスワードチェック
		if (user.getPassword().length() <= 0) {
			sb.append("パスワードを入力してください<br>");
		} else if (user.getPassword().length() > 10) {
			sb.append("パスワードは10文字以内で入力してください<br>");
		} else if (!user.getPassword().matches("([a-z0-9])*")) {
			sb.append("パスワードは半角英数字で入力してください");
		}

		// ログインIDチェック
		if (user.getLoginId().length() <= 0) {
			sb.append("ログインIDを入力してください<br>");
		} else if (user.getLoginId().length() > 100) {
			sb.append("ログインIDは10文字以内で入力してください<br>");
		} else if (!user.getLoginId().matches("([a-z0-9])*")) {
				sb.append("ログインIDは半角英数字で入力してください");
		}

		// データベース登録可能性チェック
		UserDAO uDao = new UserDAO();
		String findUser = uDao.findByLoginid(user.getLoginId());

		boolean result = (findUser != null);
		if(op.equals("register") && result) {
			sb.append("入力したログインID「"+user.getLoginId()+"」は既に登録されています。");
		} else if (op.equals("edit") && !result) {
			sb.append("ログインIDの重複なし");
		}
		return sb.toString();
	}
}
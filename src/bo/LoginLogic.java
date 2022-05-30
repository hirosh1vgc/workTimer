package bo;

import dao.UserDAO;
import model.Login;
import model.User;

public class LoginLogic {

	public User execute(Login login) {
		UserDAO dao = new UserDAO();
		User user=dao.findByLogin(login);

		return user ;
	}
}
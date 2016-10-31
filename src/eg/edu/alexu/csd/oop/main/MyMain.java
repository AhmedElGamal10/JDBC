package eg.edu.alexu.csd.oop.main;

import java.sql.SQLException;

public class MyMain {

	public static void main(String[] args) throws SQLException {

		JDBCUI userInterface = new JDBCUI();
		userInterface.showWelcomeMessage();
		String url = userInterface.getInput("Enter url of db:");
		try {
			userInterface.connectTo(url);
		} catch (Exception e) {
			userInterface.showError(e.getMessage());
		}
		while (userInterface.hasNext()){
			String sql = userInterface.getInput();
			try {
				userInterface.excuteQuery(sql);
			} catch (SQLException e) {
				userInterface.showError(e.getMessage());
			}
		}
		userInterface.close();
	}
}

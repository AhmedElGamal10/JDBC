package eg.edu.alexu.csd.oop.main;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.jdbc.DriverImpl;

public class JDBCUI {
	private Driver driver;
	private Connection connection;
	private Scanner scanner;

	public JDBCUI() {
		driver = new DriverImpl();
	}

	public void connectTo(String url) throws SQLException {
		Properties info = new Properties();
		File dbDir = new File(url);
		info.put("path", dbDir.getAbsoluteFile());
		this.connection = driver.connect("jdbc:xmldb://localhost", info);
	}

	public void excuteQuery(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			ResultSet res = statement.executeQuery(sql);
			showResult(res);
		} catch (Exception e0) {
			try {
				int res = statement.executeUpdate(sql);
				showResult(res);
			} catch (Exception e1) {
				boolean res = statement.execute(sql);
				showResult(res);
			}
		}
		statement.close();
	}

	public void showResult(boolean res) {
		if (res)
			System.out.println("succeeded!");
		else
			System.out.println("failed!");
	}

	public void showResult(int res) {
		System.out.println(res + " updated!");
	}

	public void showResult(ResultSet res) throws SQLException {
		ResultSetMetaData meta = res.getMetaData();
		int colNum = meta.getColumnCount();
		while (res.next()) {
			for (int i = 1; i <= colNum; ++i) {
				System.out.print(meta.getColumnLabel(i) + ": ");
				System.out.println(res.getObject(i));
			}
			System.out.println("=======================================");
		}
	}

	public String getInput(String msg) {
		Scanner in = getInputScanner();
		System.out.println(msg);
		System.out.println("> ");
		String inpt = in.nextLine();
		return inpt;
	}
	
	public String getInput() {
		return getInput("");
	}
	
	public boolean hasNext(){
		Scanner in = getInputScanner();
		System.out.println("> ");
		return in.hasNextLine();
	}
	
	public void showError(String error) {
		System.out.println("Error: " + error);
	}
	
	public void showWelcomeMessage() {
		System.out.println("> JDBC project");
		System.out.println("to close press ctrl + d\n\n\n");
	}
	
	public void close() throws SQLException {
		connection.close();
		if (scanner != null)
			scanner.close();
	}

	private Scanner getInputScanner() {
		if (scanner == null)
			scanner = new Scanner(System.in);
		return scanner;
	}

}

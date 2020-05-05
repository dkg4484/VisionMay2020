package TestScripts;

import helper.FunctionalLibrary;

public class TestCases extends FunctionalLibrary {
	
	public TestCases() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		FunctionalLibrary.initialization("browser");

		FunctionalLibrary.enterText("uname_tb", "username");

		FunctionalLibrary.enterText("pwd_tb", "password");

		FunctionalLibrary.clickButton("login_btn");

	}

}

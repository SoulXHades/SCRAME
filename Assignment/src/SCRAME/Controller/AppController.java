package SCRAME.Controller;

import SCRAME.Boundary.*;

public class AppController {
	//initialization
	private boolean quit;;
	private MainMenuUI mm;
	private MainController mainCtrl;
	
	public AppController()
	{
		quit = false;
		mm = new MainMenuUI();
		mainCtrl = new MainController();
		mm.setMainController(mainCtrl);
		
		mainCtrl.setContext(this);
	}
	
	//start running the app
	public void run()
	{
		while(!quit)
		{
			mm.displayMenu();
		}
	}
		
	//signal to stop the app
	protected void stop()
	{
		quit = true;
	}

}

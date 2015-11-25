package application;
	
import java.io.IOException;
import java.util.logging.Logger;

import application.database.DatabaseWorking;
import application.database.DatabaseWorkingInterface;
import application.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author KSA
 * @version 1.0
 * 
 * Application for work time calculation. Loader class.
 *
 */
public class Main extends Application {
    
	private Stage primaryStage;
	private BorderPane rootLayout;
	private Logger logger = getLogger();
	private RootLayoutController rootContainerController;
	private DatabaseWorkingInterface dbw;

	@Override
	public void start(Stage primaryStage) {
	    this.primaryStage = primaryStage;
	    
	    initDB();
	    loadRootLayout();
	    loadTimeCaptureContainer();
	    
	    primaryStage.show(); 

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * 
	 * Void loads RootLayout.fxml.
	 */
	private void loadRootLayout()
	{
	    try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
		rootLayout = (BorderPane) loader.load();
		
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		
		rootContainerController = loader.getController();

	    } catch (IOException e) {
		e.printStackTrace();
		logger.info(e.getMessage());
	    }
	}
	/**
	 * Void loads TimeCaptureContainer.fxml.
	 */
	private void loadTimeCaptureContainer() {
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("view/TimeCaptureContainer.fxml"));
	    
	    AnchorPane anchorPane;
	    TimeCaptureContainerController controller;
	    try {
		anchorPane = (AnchorPane) loader.load();
		rootLayout.setCenter(anchorPane);
		
		controller = (TimeCaptureContainerController) loader.getController();
		rootContainerController.addPropertyChangeListener(controller);
		controller.setDbwi(dbw);
		
	    } catch (IOException e) {
		e.printStackTrace();
		logger.info(e.getMessage());
	    }

	}
	/**
	 * method retrieves Logger object
	 * @return Logger
	 */
	private Logger getLogger()
	{
	    return Logger.getLogger(this.getClass().getName());
	}
	
	/**
	 * method create object, which has database API
	 */
	private void initDB()
	{
	    DatabaseWorkingInterface dbw = new DatabaseWorking();
	    dbw.setLogger(Logger.getLogger(dbw.getClass().getName()));
	    dbw.connectDatabase();
	    setDbw(dbw);
	    logger.info("Data base initialized");
	}
		
	/**
	 * method set DatabaseWorkingInterface in Main object
	 * @param dbw
	 */
	public void setDbw(DatabaseWorkingInterface dbw) {
	    this.dbw = dbw;
	}
	
	/**
	 * method retrieves DatabaseWorkingInterface
	 * @return
	 */
	public DatabaseWorkingInterface getDbw() {
	    return dbw;
	}



}

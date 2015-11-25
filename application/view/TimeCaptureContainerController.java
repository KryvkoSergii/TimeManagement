package application.view;



import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import application.database.DatabaseWorkingInterface;
import application.model.TimeStamp;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import sun.util.resources.LocaleData;

public class TimeCaptureContainerController implements PropertyChangeListener {

    private DatabaseWorkingInterface dbwi;
    
    @FXML 
    DatePicker currentDate;
    
    @FXML
    DatePicker startDatePoint;
    
    @FXML
    DatePicker endDatePoint;
    
 //   private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    @FXML
    private void initialize() {
	currentDate.setValue(LocalDate.now());
	startDatePoint.setValue(LocalDate.now().withDayOfMonth(1));
	endDatePoint.setValue(LocalDate.now().withDayOfMonth(1).plusMonths(1));
	
    }
    
   
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
	
    }
    
    @FXML
    private void incomingTime()
    {
	dbwi.insertNewTimeStamp(new TimeStamp(new Date().getTime(), 0));
    }
    
    @FXML
    private void fillTable()
    {
	System.out.println("started");
	ArrayList<TimeStamp> tsal = getFromDB();
	System.out.println(tsal.size());
	for (TimeStamp ts:tsal)
	{
	    System.out.println((new Date(ts.getTimeStamp())).getTime()+'\n');	    
	}
    }
    
    @FXML
    private void outcomingTime()
    {
	dbwi.insertNewTimeStamp(new TimeStamp(new Date().getTime(), 1));
    }

    public DatabaseWorkingInterface getDbwi() {
        return dbwi;
    }

    public void setDbwi(DatabaseWorkingInterface dbwi) {
        this.dbwi = dbwi;
    }
    
    private ArrayList<TimeStamp> getFromDB()
    {
	return dbwi.getArrayListByCriteria(startDatePoint.getValue().getMonthValue(),startDatePoint.getValue().getYear());
    }
}

package application.testing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import application.database.DatabaseWorking;
import application.database.DatabaseWorkingInterface;
import application.model.TimeStamp;

public class DatabaseWorkingTest {

    DatabaseWorkingInterface dbwi = new DatabaseWorking();
    
    @Before
    public void setUp() throws Exception {
	dbwi.setLogger(Logger.getLogger(DatabaseWorking.class.getName()));
    }
    
    @Test
    public void testConnectDatabase() {
	dbwi.connectDatabase();
    }

    @Test
    public void testInsertNewTimeStamp() {
	dbwi.connectDatabase();
	dbwi.insertNewTimeStamp(new TimeStamp((new Date()).getTime(), 0));
    }
    
    @Test
    public void getSelectedDate()
    {
	System.out.println(this.getClass());
	dbwi.connectDatabase();
	ArrayList<TimeStamp> tsal = dbwi.getArrayListByCriteria(11, 2015);
	for (TimeStamp ts:tsal)
	{
	    System.out.println((new Date(ts.getTimeStamp())).getTime()+'\n');	    
	}
    }
}

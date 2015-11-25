package application.database;

import java.util.ArrayList;
import java.util.logging.Logger;

import application.model.TimeStamp;

public interface DatabaseWorkingInterface {
    
    public void connectDatabase();
    public void connectDatabaseUsingFilepath(String filepath);
    public void disconnectDatabase();
    public void insertNewTimeStamp(TimeStamp timeStamp);
    public void changeTimeStamp(TimeStamp timeStampOld,TimeStamp timeStampNew);
    public void deleteTimeStampByDate(TimeStamp timeStamp);
    public ArrayList<TimeStamp> getArrayListByCriteria(int month, int year);
    public void setLogger(Logger logger); 

}

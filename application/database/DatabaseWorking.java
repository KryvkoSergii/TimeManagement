package application.database;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import application.model.TimeStamp;

/**
 * 
 * @author KSA
 * @version 1.0
 *
 * Used to working with database
 */
public class DatabaseWorking implements DatabaseWorkingInterface{
    private Logger logger;
    private String filepath = "timestamps.db";
    private Connection connection;

    /**
     * connect to database by default filepath timestamps.db
     * if file not exists it will be created
     */
    @Override
    public void connectDatabase() {
	try {
	    Class.forName("org.sqlite.JDBC");
	    
	    if (!(new File(filepath)).exists())
	    {
		logger.info("Creating table");
		checkOrCreateTable();
		
	    } else 
	    {
		initConnection();
	    }

//	    if (!connection.isClosed())
//	    {
//		logger.info(String.format("Database to file %s is connected",filepath));
//	    }
	    
	} catch (ClassNotFoundException e) {
	    logger.info(e.getMessage());
	}
	
	
    }

    /**
     * Close current connection Connection
     */
    @Override
    public void disconnectDatabase() {
	try {
	    connection.close();
	} catch (SQLException e) {
	    logger.info(e.getMessage());
	}
    }

    /**
     * Inserts into table TimeStamp object
     * 
     */
    @Override
    public void insertNewTimeStamp(TimeStamp timeStamp) {
	try {
	    if(connection==null)
	    {
		logger.info(String.format("connection is %s", connection));
	    }
	    PreparedStatement ps = connection.prepareStatement("INSERT INTO TIMESTAMPTABLE(TIMESTAMP,STATE) VALUES(?,?)");
	    ps.setLong(1, timeStamp.getTimeStamp());
	    ps.setInt(2, timeStamp.getState());
	    ps.execute();
	    ps.close();
	    logger.info(String.format("Values %d , %d added", timeStamp.getTimeStamp(), timeStamp.getState()));
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.info(e.getMessage());
	}

    }

    /**
     * Void updates TimeStamp and State of timeStampOld record on timeStampNew.
     * As a key is used timeStampOld.getTimeStamp of long type.
     */
    @Override
    public void changeTimeStamp(TimeStamp timeStampOld, TimeStamp timeStampNew) {
	try {
	PreparedStatement ps = connection.prepareStatement("UPDATE TIMESTAMPTABLE SET TIMESTAMP = ? , STATE = ? WHERE TIMESTAMP = ?");
	ps.setLong(1, timeStampNew.getTimeStamp());
	ps.setInt(2, timeStampNew.getState());
	ps.setLong(3, timeStampOld.getTimeStamp());
	ps.execute();
	ps.close();
	} catch (SQLException e) {
	    logger.info(e.getMessage());
	}
	
    }

    @Override
    public void deleteTimeStampByDate(TimeStamp timeStamp) {
	try {
	PreparedStatement ps = connection.prepareStatement("DELETE FROM TIMESTAMPTABLE WHERE TIMESTAMP = ?");
	ps.setLong(1, timeStamp.getTimeStamp());
	ps.execute();
	ps.close();
	} catch (SQLException e) {
	    logger.info(e.getMessage());
	}
    }

    /**
     * 
     * @param month
     * @param year
     * @return ArrayList of TimeStamp objects contained in database
     */
    @Override
    public ArrayList<TimeStamp> getArrayListByCriteria(int month ,int year) 
    {
	ArrayList<TimeStamp> list = null;
	try {
	    Statement st = connection.createStatement();
	    
	    Calendar startOfMonth = Calendar.getInstance();
	    startOfMonth.set(year, month-1, 1,0,0,0);
	    
	    Calendar endOfMonth = Calendar.getInstance();
	    endOfMonth.set(year, month-1, startOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH),23,59,59);
	    
	    ResultSet rs = st.executeQuery(String.format("SELECT TIMESTAMP, STATE FROM TIMESTAMPTABLE WHERE TIMESTAMP > %d%n AND TIMESTAMP < %d%n", 
		    startOfMonth.getTimeInMillis(),endOfMonth.getTimeInMillis()));
	    
	    list = new ArrayList<>();
	    while (rs.next())
	    {
		list.add(new TimeStamp(rs.getLong("TIMESTAMP"), rs.getInt("STATE")));
	    }

	} catch (SQLException e) {
	    logger.info(e.getMessage());
	}		
	return list;
    }
     

    /**
     * Set logger of class
     */
    @Override
    public void setLogger(Logger logger) {
	if (this.logger==null)
	{
	    this.logger=logger;
	}
	logger.info("logger is on");
    }

    /**
     * Method connects to database using filepath.
     * Method change filepath field and calls connectDatabase() method. 
     * @param
     */
    @Override
    public void connectDatabaseUsingFilepath(String filepath) {
	this.filepath = filepath;
	connectDatabase();
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void checkOrCreateTable()
    {
	try {
	    initConnection();
	    Statement st = connection.createStatement();
	    st.executeUpdate("CREATE TABLE IF NOT EXISTS TIMESTAMPTABLE(ID INTEGER AUTO_INCREMENT PRIMARY KEY, TIMESTAMP INTEGER, STATE INTEGER)");
	    st.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.info(e.getMessage());
	}
	
    }
    
    /**
     * Retrieves connection to database using filepath
     * @return Connection initialConnection
     */
    private void initConnection()
    {
	try {
	    this.connection = DriverManager.getConnection(String.format("jdbc:sqlite:"+filepath.toString()));
	    logger.info(String.format(" Connected jdbc:sqlite:%s",filepath));
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.info(e.getMessage());
	}
    }

}

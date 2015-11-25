package application.model;

/**
 * Class contains data of time stamp.
 * <tbody>
 * <tr><td>variable</td><td>purpose</td></tr>
 * <tr><td>long timeStamp</td><td>contains time in ms</td></tr>
 * <tr><td>int state</td><td>state 0 - incoming time, state 1 - outcoming time</td></tr>
 * </tbody>
 * 
 * @author KSA
 * @version 1.0
 * 
 * 
 */
public class TimeStamp {
    private long timeStamp;
    private int state;
    
    public long getTimeStamp() {
        return timeStamp;
    }
    
    /**
     * Class contains data of time stamp.
     * long timeStamp -	contains time in ms
     * int state - state 0 - incoming time, state 1 - outcoming time
     * 
     * @author KSA
     * @version 1.0
     */    
    public TimeStamp(long timeStamp, int state) {
	super();
	this.timeStamp = timeStamp;
	this.state = state;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

}

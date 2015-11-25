package application.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RootLayoutController {
    
    private Stage dialogStage;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Logger logger;
    
    @FXML
    private void initialize() {
	
    }
    
    @FXML
    private void handleClose() {       
        System.exit(0);
    }

    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
    public void setLogger(Logger logger)
    {
	this.logger = logger;
    }


}

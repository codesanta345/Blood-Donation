package allhistory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class allhistoryviewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void donation(ActionEvent event) {
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/donationHistory/donationHistoryView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ui.Screens.show(stage, root1, "Donation History");;
    		
    		
			//to hide the opened window
			 
			  /* Scene scene1=(Scene)btnComboApp.getScene();
			   scene1.getWindow().hide();
			 */

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

    }

    @FXML
    void donor(ActionEvent event) {
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/history/historyView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ui.Screens.show(stage, root1, "Donor History");
    		
    		
			//to hide the opened window
			 
			  /* Scene scene1=(Scene)btnComboApp.getScene();
			   scene1.getWindow().hide();
			 */

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void issue(ActionEvent event) {
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/issuedHistory/issuedHistoryView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ui.Screens.show(stage, root1, "Issued Blood History");
    		
    		
			//to hide the opened window
			 
			  /* Scene scene1=(Scene)btnComboApp.getScene();
			   scene1.getWindow().hide();
			 */

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {

    }

}

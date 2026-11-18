package issueBlood;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class issueBloodviewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> bgroup;

    @FXML
    private TextField hospital;

    @FXML
    private TextField mobile;

    @FXML
    private TextField name;

    @FXML
    private DatePicker picker;

    @FXML
    private TextField purpose;

    @FXML
    private TextField units;
    
	Connection con;
    PreparedStatement pst;
    
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    
    void clear()
    {
    	bgroup.setValue(null);
    	picker.setValue(null);
    	hospital.setText(null);
    	mobile.setText(null);
        name.setText(null);
        purpose.setText(null);
    	units.setText(null);
    }
    @FXML
    void onUpdate(ActionEvent event) {
    	String s=bgroup.getValue();
    	String m=db.Database.stockColumn(s);
    	if(m==null)
    	{
    		showMsg("Select a blood group");
    		return;
    	}
    	if(name.getText()==null||name.getText().trim().isEmpty())
    	{
    		showMsg("Enter the patient's name");
    		return;
    	}
    	if(picker.getValue()==null)
    	{
    		showMsg("Select the date of issue");
    		return;
    	}
    	int n;
    	try
    	{
    		n=Integer.parseInt(units.getText().trim());
    	}
    	catch(Exception e)
    	{
    		showMsg("Enter the number of units as a number");
    		return;
    	}
    	if(n<=0)
    	{
    		showMsg("Units must be more than 0");
    		return;
    	}
    	try
    	{
    		// m comes from a fixed list in Database.stockColumn, so it is safe to put in the SQL
    		pst=con.prepareStatement("select `"+m+"` from total_record");
    		ResultSet table1=pst.executeQuery();
    		int q=0;
    		while(table1.next())
    		{
    			q=table1.getInt(m);
    		}
    		if(q-n<0)
    		{
    			showMsg("Only "+q+" unit(s) of "+s+" available.");
    			return;
    		}
    		pst=con.prepareStatement("insert into issued (name,mobile,hospital,reason,issuedate,bgroup,units) values(?,?,?,?,?,?,?)");
    		pst.setString(1, name.getText());
    		pst.setString(2,mobile.getText());
    		pst.setString(3, hospital.getText());
    		pst.setString(4, purpose.getText());
    		pst.setDate(5, java.sql.Date.valueOf(picker.getValue()));
    		pst.setString(6, s);
    		pst.setInt(7, n);
    		pst.executeUpdate();

    		pst=con.prepareStatement("update total_record set `"+m+"`=`"+m+"`-?");
    		pst.setInt(1,n);
    		pst.executeUpdate();
    		showMsg(n+" unit(s) of "+s+" issued successfully");
    		clear();
    	}
    	catch(Exception e)
    	{
    		showMsg("Could not issue blood: "+e.getMessage());
    		e.printStackTrace();
    	}
    }

    @FXML
    void initialize() {
        assert bgroup != null : "fx:id=\"bgroup\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        assert hospital != null : "fx:id=\"hospital\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        assert mobile != null : "fx:id=\"mobile\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        assert picker != null : "fx:id=\"picker\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        assert purpose != null : "fx:id=\"purpose\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        assert units != null : "fx:id=\"units\" was not injected: check your FXML file 'issueBloodview.fxml'.";
        ArrayList<String> items2=new ArrayList<String>(Arrays.asList(db.Database.BLOOD_GROUPS));
        bgroup.getItems().setAll(items2);
        
        con=	databaseConnection.doConnect();
    }

}

package bloodUnitCollection;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Donor.databaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class bloodUnitCollectionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bgroup;
     
    @FXML
    private ComboBox<String> mobile;

    @FXML
    private ImageView picDisplay;

    @FXML
    private DatePicker picker;
     
    Connection con;
    PreparedStatement pst;
    String f1=null;
    ResultSet table;
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Title");
    	alert.setContentText(msg);
    	alert.show();
    }
    @FXML
    void onMobile(ActionEvent event) {
       
    }

    @FXML
    void onClear(ActionEvent event) {
     
    	mobile.setValue("");
    	bgroup.setText("");
    	picDisplay.setImage(null);
    	picker.setValue(null);
    }
    boolean findRecord(String mobileno)
    {
    	boolean jasoos=false;
    	try {
        	pst=con.prepareStatement("select * from donor_table where mobile=?");
        	pst.setString(1, mobileno);
        	
        	table=pst.executeQuery();
        	      	
        	
        	while(table.next())
        	{
        		jasoos=true;
        		
        		bgroup.setText(table.getString("bgroup"));
        		
        		picDisplay.setImage(null);
        		String path=table.getString("image");
        		if(path!=null&&new java.io.File(path).exists())
        		{
        			try(FileInputStream in=new FileInputStream(path))
        			{
        				picDisplay.setImage(new Image(in));
        			}
        		}
        		
        	}
        	}
        	catch(Exception exp)
        	{
        		exp.printStackTrace();
        	}
    	return jasoos;
    }
    @FXML
    void onSearch(ActionEvent event) {

    	String str= mobile.getSelectionModel().getSelectedItem();
    	if(findRecord(str)==false)
    			showMsg("Invalid mobile no....");
    	
    }
    boolean chkmobileno(String mobileno)
    {
    	boolean jasoos=false;
    	try {
        	pst=con.prepareStatement("select * from donor_table where mobile=?");
        	pst.setString(1, mobileno);
        	
        	table=pst.executeQuery();
        	      	
        	
        	while(table.next())
        	{
        		jasoos=true;
        	}
        	}
        	catch(Exception exp)
        	{
        		exp.printStackTrace();
        	}
    	return jasoos;
    }
    @FXML
    void onUpload(ActionEvent event) {
    	LocalDate local=picker.getValue();
    	String str=mobile.getValue();
    	if(str==null||chkmobileno(str)==false)
    	{
    		showMsg("Select a registered mobile number and click Search first");
    		return;
    	}
    	if(local==null)
    	{
    		showMsg("Select the date of donation");
    		return;
    	}
    	String bg1=db.Database.stockColumn(bgroup.getText());
    	if(bg1==null)
    	{
    		showMsg("Click Search to load the donor's blood group first");
    		return;
    	}
    	try {
			pst=con.prepareStatement("insert into donations (mobile,bgroup,dateofdonation) values(?,?,?)");
			pst.setString(1, str);
			pst.setString(2, bgroup.getText());
			pst.setDate(3, java.sql.Date.valueOf(local));
			pst.executeUpdate();

			// bg1 comes from a fixed list in Database.stockColumn, so it is safe to put in the SQL
			pst=con.prepareStatement("update total_record set `"+bg1+"`=`"+bg1+"`+1");
			pst.executeUpdate();
			showMsg("Donation recorded successfully");
		} catch (SQLException e) {
			showMsg("Could not save the donation: "+e.getMessage());
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert bgroup != null : "fx:id=\"bgroup\" was not injected: check your FXML file 'bloodUnitCollectionView.fxml'.";
        assert mobile != null : "fx:id=\"mobile\" was not injected: check your FXML file 'bloodUnitCollectionView.fxml'.";
        assert picDisplay != null : "fx:id=\"picDisplay\" was not injected: check your FXML file 'bloodUnitCollectionView.fxml'.";
        assert picker != null : "fx:id=\"picker\" was not injected: check your FXML file 'bloodUnitCollectionView.fxml'.";
        con=	databaseConnection.doConnect();
        try {
			pst=con.prepareStatement("select distinct mobile from donor_table");
			ArrayList<String> item=new ArrayList<String>();
	        table=pst.executeQuery();
	        while(table.next())
	        {
	        	item.add(table.getString("mobile"));
	        }
	        mobile.getItems().setAll(item);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
    }

}

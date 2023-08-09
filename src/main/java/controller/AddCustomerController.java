package controller;

import dao.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import model.Customer;
import model.ItemComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This is the Add Customer Controller, to add new Customers to the database.  It adds
 * customer name, phone, postal code, and the street.  There is also a method to add the
 * country and state to each record.
 *
 * @author Gavril Fofiu.
 */
public class AddCustomerController implements Initializable {
/**
 * This is the button to cancel adding a new customer.
 */
public Button addCustomerCancelButton;
/**
 * This is the button to save a new customer
 */
public Button addCustomerSaveButton;
/**
 * This the combo box to select the customer Country.
 */
public ComboBox<ItemComboBox> addCustomerCountry;
/**
 * This the combo box to select the customer's State based on the selected Country.
 */
public ComboBox<ItemComboBox> addCustomerState;
/**
 * This is the non-editable text field for the customer ID.
 */
@FXML
private TextField addCustomerCustomerID;
/**
 * This is the text field to enter the customer name.
 */
@FXML
private TextField addCustomerName;
/**
 * This is the text field to enter the customer phone.
 */
@FXML
private TextField addCustomerPhone;
/**
 * This is the text field to enter the customer postal code.
 */
@FXML
private TextField addCustomerPostal;
/**
 * This is the text field to enter the customer street address.
 */
@FXML
private TextField addCustomerStreet;

/**
 * This is a cancel method.  When clicked, this method closes the add customer stage.
 *
 * @param event cancel stage button is pressed.
 */
@FXML
void addCustomerCancelButtton(ActionEvent event) {

    Stage stage = (Stage) addCustomerCancelButton.getScene().getWindow();
    stage.close();
}

@FXML
void addCustomerCountry(MouseEvent event) {

}

/**
 * This a save method.  When this method is called, the new customer is saved to the database.
 * It saved the new customer name, street, postal code, and phone from the text boxes.  It
 * saves the country and state from the combo boxes.
 *
 * @param event the save button is clicked.
 */
@FXML
void addCustomerSaveButton(ActionEvent event) throws SQLException {
    Customer model = new Customer();
    model.setCustomer_Name(addCustomerName.getText());
    model.setAddress(addCustomerStreet.getText());
    model.setPostal_Code(addCustomerPostal.getText());
    model.setPhone(addCustomerPhone.getText());
    ItemComboBox selectedOption = addCustomerState.getSelectionModel().getSelectedItem();
    model.setDivision_ID(selectedOption.getId());
    CustomerDAO.AddCustomer(model);

    Stage stage = (Stage) addCustomerSaveButton.getScene().getWindow();
    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    stage.close();


}


private int id;

/**
 * This method set the customer ID.  It checks the database and returns the latest customer ID.
 *
 * @param _id is the customer ID.
 */

public void setNewCustomer(int _id) {
    id = _id;
    addCustomerCustomerID.setText(String.valueOf(id));
}

/***
 *
 * This is the main initialize method.  This method is called with the stage loads, and it loads
 * the combo boxes for the add customer method.  It loads the customer country, and then it loads the
 * state depending on the selected item in the country combo box.
 *
 */

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

    try {
        addCustomerCountry.setItems(CustomerDAO.getAllCountries());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    addCustomerCountry.setConverter(new StringConverter<>() {
        @Override
        public String toString(ItemComboBox object) {
            if (object == null) return "";
            return object.getName();
        }

        @Override
        public ItemComboBox fromString(String s) {
            return null;
        }
    });
}
/**
 * This is a method that checks the selection of the combo box.  When the user selects the county,
 * the method loads proper states depending on selection of the country.
 * */
public void changeSelection(ActionEvent actionEvent) {
    try {
        ItemComboBox selectedOption = addCustomerCountry.getSelectionModel().getSelectedItem();

        addCustomerState.setItems(CustomerDAO.getStates(selectedOption.getId()));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    addCustomerState.setConverter(new StringConverter<>() {
        @Override
        public String toString(ItemComboBox object) {
            if (object == null) return "";
            return object.getName();
        }

        @Override
        public ItemComboBox fromString(String s) {
            return null;
        }
    });
    addCustomerState.getSelectionModel().selectFirst();
}

}
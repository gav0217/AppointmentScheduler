package controller;

import dao.AppointmentDAO;
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
 * This is the edit customer controller class. It loads a selected customer into the window for
 * editing/updating.  Once it's saved, it saves it to the database.
 *
 * @author Gavril Fofiu.
 */
public class EditCustomer implements Initializable {
/**
 * This is the edit customer cancel button.
 */
public Button editCustomerCancelButton;
/**
 * This is the edit customer save button.
 */
public Button editCustomerSaveButton;
/**
 * This the edit customer country combo box.
 */
public ComboBox<ItemComboBox> editCustomerCountry;
/**
 * This is the edit customer state/province combo box.
 */
public ComboBox<ItemComboBox> editCustomerStateProvince;
/**
 * This is the non-editable customer ID text field.
 */
@FXML
private TextField editCustomerCustomerID;
/**
 * This is the edit customer name text field.
 */

@FXML
private TextField editCustomerName;
/**
 * This is the edit customer phone text field.
 */

@FXML
private TextField editCustomerPhone;
/**
 * This is the edit customer postal code text field.
 */

@FXML
private TextField editCustomerPostal;
/**
 * This is the edit customer street text field.
 */

@FXML
private TextField editCustomerStreet;

/**
 * This a cancel method.  It cancels editing the selected customer in the database.
 *
 * @param event when the cancel button is clicked.
 */

@FXML
void editCustomerCancelButtton(ActionEvent event) {

    Stage stage = (Stage) editCustomerCancelButton.getScene().getWindow();
    stage.close();
}

/**
 * This is a save method. This method saves the updated or modified customer information to
 * the database.  When it is saved, the customer table is refreshed.
 *
 * @param event when the save button is pressed.
 */

@FXML
void editCustomerSaveButton(ActionEvent event) {

    customer.setCustomer_Name(editCustomerName.getText());
    customer.setAddress(editCustomerStreet.getText());
    customer.setPostal_Code(editCustomerPostal.getText());
    customer.setPhone(editCustomerPhone.getText());
    customer.setDivision_ID(editCustomerStateProvince.getSelectionModel().getSelectedItem().getId());

    CustomerDAO.ModifyCustomer(customer);
    Stage stage = (Stage) editCustomerSaveButton.getScene().getWindow();
    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    stage.close();

}

/**
 * This is the main initialize method for the edit customer controller. It loads the combo box model
 * to the combo boxes.
 *
 * @param url
 * @param resourceBundle
 */

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        editCustomerCountry.setItems(CustomerDAO.getAllCountries());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    editCustomerCountry.setConverter(new StringConverter<>() {
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
 * This is a selection method. It loads the state/provinces combo box depending on the selected country.
 *
 * @param actionEvent when it is selected.
 */

public void changeSelection(ActionEvent actionEvent) {
    try {
        ItemComboBox selectedOption = editCustomerCountry.getSelectionModel().getSelectedItem();

        editCustomerStateProvince.setItems(CustomerDAO.getStates(selectedOption.getId()));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    editCustomerStateProvince.setConverter(new StringConverter<>() {
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
    editCustomerStateProvince.getSelectionModel().selectFirst();
}

public void onStateClicked(MouseEvent mouseEvent) {

}

private Customer customer;

/**
 * This is the setter method for the customer.  Sets the selected customer information in text fields and combo boxes in the window when it is loaded.
 * LAMDA EXPRESSION: When this method is run, it populates the state/provence depending on the country.
 * I used this LAMDA function because it helped cut down on the amount of code needed, and much more effective.  There are two LAMBDA expressions in this
 * method.
 *
 * @param _customer is the new customer model.
 * @throws SQLException when there is an error.
 */
public void setCustomer(Customer _customer) throws SQLException {
    customer = _customer;
    editCustomerCustomerID.setText(String.valueOf(customer.getCustomer_ID()));
    editCustomerName.setText(customer.getCustomer_Name());
    editCustomerStreet.setText(customer.getAddress());
    editCustomerPostal.setText(customer.getPostal_Code());
    editCustomerPhone.setText(customer.getPhone());

    var countryId = CustomerDAO.getCountryFromState(customer.getDivision());
    editCustomerCountry.getSelectionModel().select(editCustomerCountry.getItems().filtered(x -> x.getId() == countryId).stream().findFirst().get());
    editCustomerStateProvince.setItems(CustomerDAO.getStates(countryId));
    editCustomerStateProvince.setConverter(new StringConverter<>() {
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
    editCustomerStateProvince.getSelectionModel().select(editCustomerStateProvince.getItems().filtered(x -> x.getName().equals(customer.getDivision())).stream().findFirst().get());
}


}

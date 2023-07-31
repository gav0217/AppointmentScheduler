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

public class EditCustomer implements Initializable {


public Button editCustomerCancelButton;
public Button editCustomerSaveButton;
public ComboBox<ItemComboBox> editCustomerCountry;
public ComboBox<ItemComboBox> editCustomerStateProvince;
@FXML
private TextField editCustomerCustomerID;

@FXML
private TextField editCustomerName;

@FXML
private TextField editCustomerPhone;

@FXML
private TextField editCustomerPostal;

@FXML
private TextField editCustomerStreet;

@FXML
void editCustomerCancelButtton(ActionEvent event) {

    Stage stage = (Stage) editCustomerCancelButton.getScene().getWindow();
    stage.close();
}

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

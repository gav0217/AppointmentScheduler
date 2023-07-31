package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import model.Appointment;
import model.Customer;
import model.ItemComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController  implements Initializable {
public Button addCustomerCancelButton;
public Button addCustomerSaveButton;
public ComboBox<ItemComboBox> addCustomerCountry;
public ComboBox<ItemComboBox> addCustomerState;

@FXML
private TextField addCustomerCustomerID;

@FXML
private TextField addCustomerName;

@FXML
private TextField addCustomerPhone;

@FXML
private TextField addCustomerPostal;

@FXML
private TextField addCustomerStreet;

@FXML
void addCustomerCancelButtton(ActionEvent event) {

    Stage stage = (Stage) addCustomerCancelButton.getScene().getWindow();
    stage.close();
}

@FXML
void addCustomerCountry(MouseEvent event) {

}

@FXML
void addCustomerSaveButton(ActionEvent event) throws SQLException {
    Customer model = new Customer();
    //model.setCustomer_Name(addCustomerCustomerID.getText());
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
@FXML
void addCustomerState(MouseEvent event) {

}

private int id;
public void setNewCustomer(int _id)
{
    id = _id;
    addCustomerCustomerID.setText(String.valueOf(id));
}

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

public void onStateClicked(MouseEvent mouseEvent) {

}
}
package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import helper.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import model.Appointment;
import model.ItemComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class AddAppointmentController implements Initializable {
public ComboBox<ItemComboBox> addAppointmentCustomer;
public Button cancelAddAppointment;
public ComboBox<ItemComboBox> addAppointmentContactID;
public ComboBox<ItemComboBox> addAppointmentUserID;

@FXML
private TextField addAppointmentAppointmentID;

@FXML
private TextField addAppointmentDescription;

@FXML
private DatePicker addAppointmentEndDate;

@FXML
private ComboBox<String> addAppointmentEndTime;

@FXML
private TextField addAppointmentLocation;

@FXML
private DatePicker addAppointmentStartDate;

@FXML
private ComboBox<String> addAppointmentStartTime;

@FXML
private TextField addAppointmentTitle;

@FXML
private TextField addAppointmentType;

//Button to cancel adding the appointment. Exits back to the Main Screen.
@FXML
void addAppointmentCancelButton(ActionEvent event) {

    Stage stage = (Stage) cancelAddAppointment.getScene().getWindow();
    stage.close();
}

//Button to save a new appointment, adds the appointment details to the database.
@FXML
void addAppointmentSaveButton(ActionEvent event) throws SQLException, IOException {

    ItemComboBox selectedOption = addAppointmentCustomer.getSelectionModel().getSelectedItem();
    Appointment model = new Appointment();

    String startTime = addAppointmentStartTime.getSelectionModel().getSelectedItem();

    String endTime = addAppointmentEndTime.getSelectionModel().getSelectedItem();

    int startHour = Integer.parseInt(startTime.substring(0,2));
    int startMinute = Integer.parseInt(startTime.substring(3,5));
    model.setStart(addAppointmentStartDate.getValue().atTime(startHour, startMinute));

    int endHour = Integer.parseInt(endTime.substring(0,2));
    int endMinute = Integer.parseInt(endTime.substring(3,5));
    model.setEnd(addAppointmentEndDate.getValue().atTime(endHour, endMinute));

    if(AppointmentDAO.isOverlap(model.getStart(), model.getEnd(), 0)) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText("Appointment overlap");
        alert.show();
        return;
    }

    model.setTitle(addAppointmentTitle.getText());
    model.setDescription(addAppointmentDescription.getText());
    model.setLocation(addAppointmentLocation.getText());
    model.setType(addAppointmentType.getText());

    model.setCustomer_ID(selectedOption.getId());
    model.setUser_ID(addAppointmentUserID.getSelectionModel().getSelectedItem().getId());
    model.setContact_ID((addAppointmentContactID.getSelectionModel().getSelectedItem().getId()));

    AppointmentDAO.AddAppointment(model);

    Stage stage = (Stage) cancelAddAppointment.getScene().getWindow();
    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    stage.close();
}

private int id;

//Method to set the appointment ID, sets it in the New Appointment UI.
public void setNewAppointment(int _id)
{
    id = _id;
    addAppointmentAppointmentID.setText(String.valueOf(id));
}
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {

        addAppointmentCustomer.setItems(CustomerDAO.getAllIdName());
        addAppointmentCustomer.setConverter(new StringConverter<>() {
            @Override
            public String toString(ItemComboBox object) {
                if (object == null) return "";
                return object.getName();
            }
            @Override
            public ItemComboBox fromString(String string1) {
                return addAppointmentCustomer.getItems().stream().filter(ap -> ap.getName().equals(string1)).findFirst().orElse(null);
            }
        });

        addAppointmentStartTime.setItems(Utils.getTime(9,16));
        var endTime = Utils.getTime(9,16);
        endTime.remove(0);
        endTime.add("17:00");
        addAppointmentEndTime.setItems(endTime);


    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    try {
        addAppointmentUserID.setItems(CustomerDAO.getUsersIdName());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    addAppointmentUserID.setConverter(new StringConverter<>() {
        @Override
        public String toString(ItemComboBox object) {
            if (object == null) return "";
            return object.getName();
        }
        @Override
        public ItemComboBox fromString(String string1) {
            return null;
        }
    });

    try {
        addAppointmentContactID.setItems(CustomerDAO.getContactID());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    addAppointmentContactID.setConverter(new StringConverter<>() {
        @Override
        public String toString(ItemComboBox object) {
            if (object == null) return "";
            return object.getName();
        }
        @Override
        public ItemComboBox fromString(String string1) {
            return null;
        }
    });
}
public void addAppointmentCustomer(MouseEvent mouseEvent) {
}
public void changeSelection(ActionEvent actionEvent) throws SQLException {

}
}
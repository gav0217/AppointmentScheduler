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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class EditAppointment implements Initializable {

public Button cancelEditAppointment;
public Button saveEditAppointment;
public ComboBox<ItemComboBox> editAppointmentCustomerName;
public ComboBox<ItemComboBox> editAppointmentUserID;
public ComboBox<ItemComboBox> editAppointmentContact;
@FXML
private TextField editAppointmentAppointmentID;

@FXML
private TextField editAppointmentDescription;

@FXML
private DatePicker editAppointmentEndDate;

@FXML
private ComboBox<String> editAppointmentEndTime;

@FXML
private TextField editAppointmentLocation;

@FXML
private DatePicker editAppointmentStartDate;

@FXML
private ComboBox<String> editAppointmentStartTime;

@FXML
private TextField editAppointmentTitle;

@FXML
private TextField editAppointmentType;

@FXML
void editAppointmentCancelButton(ActionEvent event) {

    Stage stage = (Stage) cancelEditAppointment.getScene().getWindow();
    stage.close();

}

@FXML
void editAppointmentCustomer(MouseEvent event) {
}

@FXML
void editAppointmentSaveButton(ActionEvent event) throws SQLException {

    String startTime = editAppointmentStartTime.getSelectionModel().getSelectedItem();


    String endTime = editAppointmentEndTime.getSelectionModel().getSelectedItem();

    int startHour = Integer.parseInt(startTime.substring(0,2));
    int startMinute = Integer.parseInt(startTime.substring(3,5));
    appointment.setStart(editAppointmentStartDate.getValue().atTime(startHour, startMinute));

    int endHour = Integer.parseInt(endTime.substring(0,2));
    int endMinute = Integer.parseInt(endTime.substring(3,5));
    appointment.setEnd(editAppointmentEndDate.getValue().atTime(endHour, endMinute));

    if(AppointmentDAO.isOverlap(appointment.getStart(), appointment.getEnd(), appointment.getAppointment_ID())) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText("Appointment overlap");
        alert.show();
        return;
        //alert.setContentText(String.valueOf(appointment.getAppointment_ID() + "\r\n" + String.valueOf(appointment.getStart()) + "\r\n" + String.valueOf(appointment.getStart())));
    }


    appointment.setTitle(editAppointmentTitle.getText());
    appointment.setType(editAppointmentType.getText());
    appointment.setLocation(editAppointmentLocation.getText());
    appointment.setDescription(editAppointmentDescription.getText());



    appointment.setUser_ID(editAppointmentUserID.getSelectionModel().getSelectedItem().getId());
    appointment.setContact_ID((editAppointmentContact.getSelectionModel().getSelectedItem().getId()));
    appointment.setCustomer_ID((editAppointmentCustomerName.getSelectionModel().getSelectedItem().getId()));
    editAppointmentContact.getSelectionModel().select(appointment.getContact_ID());

    AppointmentDAO.ModifyAppoinment(appointment);
    Stage stage = (Stage) saveEditAppointment.getScene().getWindow();
    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    stage.close();
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        editAppointmentCustomerName.setItems(CustomerDAO.getAllIdName());
        editAppointmentCustomerName.setConverter(new StringConverter<>() {
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

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    try {
        editAppointmentUserID.setItems(CustomerDAO.getUsersIdName());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    editAppointmentUserID.setConverter(new StringConverter<>() {
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
        editAppointmentContact.setItems(CustomerDAO.getContactID());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    editAppointmentContact.setConverter(new StringConverter<>() {
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

private Appointment appointment;

public void setAppointment(Appointment _appointment) {
    appointment = _appointment;

    editAppointmentCustomerName.getSelectionModel().select(editAppointmentCustomerName.getItems().filtered(x -> x.getId().equals(appointment.getCustomer_ID())).stream().findFirst().get());
    editAppointmentUserID.getSelectionModel().select(editAppointmentUserID.getItems().filtered(x -> x.getId().equals(appointment.getUser_ID())).stream().findFirst().get());
    editAppointmentContact.getSelectionModel().select(editAppointmentContact.getItems().filtered(x -> x.getId().equals(appointment.getContact_ID())).stream().findFirst().get());
    editAppointmentAppointmentID.setText(String.valueOf(appointment.getAppointment_ID()));
    editAppointmentTitle.setText(appointment.getTitle());
    editAppointmentType.setText(appointment.getType());
    editAppointmentLocation.setText(appointment.getLocation());
    editAppointmentDescription.setText(appointment.getDescription());
    editAppointmentStartDate.setValue(appointment.getStart().toLocalDate());
    editAppointmentEndDate.setValue(appointment.getEnd().toLocalDate());
    String stime = String.format("%02d", appointment.getStart().getHour()) + ":" + String.format("%02d", appointment.getStart().getMinute());
    editAppointmentStartTime.setItems(Utils.getTime(9, 16));
    var endTime = Utils.getTime(9, 16);
    endTime.remove(0);
    endTime.add("17:00");
    editAppointmentEndTime.setItems(endTime);

    editAppointmentStartTime.getSelectionModel().select(stime);

    String etime = String.format("%02d", appointment.getEnd().getHour()) + ":" + String.format("%02d", appointment.getEnd().getMinute());
    editAppointmentEndTime.getSelectionModel().select(etime);

}

}


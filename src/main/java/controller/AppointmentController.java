package controller;

import com.example.fofiuappointments.AppointmentScheduler;
import dao.AppointmentDAO;
import helper.CalendarHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

@FXML
public TableView<Appointment> appointmentTable;
public RadioButton mainMenuAll;
public RadioButton mainMenuWeekly;
public RadioButton mainMenuMonthly;
public Button appointmentLogout;
private ToggleGroup toggleGroup;

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        appointmentTable.setItems(AppointmentDAO.getAllAppointments());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    toggleGroup = new ToggleGroup();
    mainMenuAll.setToggleGroup(toggleGroup);
    mainMenuWeekly.setToggleGroup(toggleGroup);
    mainMenuMonthly.setToggleGroup(toggleGroup);

    // Add a listener to the toggle group to react when a radio button is selected
    toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            try {
                if(selectedRadioButton == mainMenuAll)
                {
                    appointmentTable.setItems(AppointmentDAO.getAllAppointments());
                }
                else {
                    filterList(selectedRadioButton == mainMenuWeekly);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    });
}
private void filterList(boolean byWeek) throws SQLException {
    if (byWeek) {
        var pair = CalendarHelper.getDateRange(Calendar.DAY_OF_WEEK);
        appointmentTable.setItems(AppointmentDAO.getAllAppointments(pair.getKey(), pair.getValue()));
    } else {
        var pair = CalendarHelper.getDateRange(Calendar.DAY_OF_MONTH);
        appointmentTable.setItems(AppointmentDAO.getAllAppointments(pair.getKey(), pair.getValue()));
    }
}
private static Calendar getCalendarForNow() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    return calendar;
}
public void mainMenuAddAppointmentButton(ActionEvent actionEvent) {
}
public void addAppointment(MouseEvent mouseEvent) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("AddAppointment.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Add Appointment");

    Scene scene = new Scene(root);
    stage.setScene(scene);

    AddAppointmentController addApp =  loader.getController();

    addApp.setNewAppointment(AppointmentDAO.getNextAppointmentId());

    stage.show();
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
            try {
                appointmentTable.setItems(AppointmentDAO.getAllAppointments());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    });
}
public void appointmentModify(MouseEvent mouseEvent) throws IOException {
    Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("EditAppointment.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Edit Appointment");
    Scene scene = new Scene(root);
    stage.setScene(scene);

    EditAppointment edit =  loader.getController();
    edit.setAppointment(appointment);

    stage.show();
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
            try {
                appointmentTable.setItems(AppointmentDAO.getAllAppointments());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    });

}
public void appointmentDelete(MouseEvent mouseEvent) throws SQLException {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Delete Item");
    alert.setContentText("Are you sure you want to delete this item?");

    // Customize the buttons (Optional)
    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");
    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    // Show and wait for the user's response
    alert.showAndWait().ifPresent(response -> {
        if (response == buttonTypeYes) {
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            AppointmentDAO.DeleteAppointment(appointment);
            try {
                appointmentTable.setItems(AppointmentDAO.getAllAppointments());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Item deleted!");
        } else if (response == buttonTypeNo) {
            // User chose not to delete, do nothing or handle accordingly
            System.out.println("Delete canceled.");
        }
    });

}

public void appointmentLogoutButton(MouseEvent mouseEvent) throws IOException {

    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("LoginMenu.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Appointments");
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    Stage currentStage = (Stage) appointmentLogout.getScene().getWindow();
    currentStage.close();
}
}

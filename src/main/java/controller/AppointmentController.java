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

/**
 * This is the main appointment controller.  This class loads the appointment table, which
 * the user can sort by different views through radio buttons.   It can be sorted by all, current
 * week, and by current month.  From here you can add, modify, or delete appointments.  Once this is loaded,
 * you can go to other tabs such as the customer tab, and the report tab.
 *
 * @author Gavril Fofiu
 */
public class AppointmentController implements Initializable {
/**
 * This is a TableView for appointments.  Loads when the tab is loaded.
 */
@FXML
public TableView<Appointment> appointmentTable;
/**
 * This is the radio button for selecting all appointments in the appointment table.
 */
public RadioButton mainMenuAll;
/**
 * This is the radio button for selecting current week appointments in the appointment table.
 */
public RadioButton mainMenuWeekly;
/**
 * This is the radio button for selecting the current month appointments in the appointment table.
 */
public RadioButton mainMenuMonthly;
/**
 * This is the button to logout of the application back to the login menu.
 */
public Button appointmentLogout;
/**
 * This is the appointment view a radio button toggle group.
 */
private ToggleGroup toggleGroup;

/**
 * This is the main initialize method for this controller. It loads the appointment table when
 * this tab is selected.  It is selected by default.  It has the options to add, delete, and modify
 * appointments. You are also able to sort the appointment table by different views.
 *
 * @param url
 * @param resourceBundle
 */
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

    toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            try {
                if (selectedRadioButton == mainMenuAll) {
                    appointmentTable.setItems(AppointmentDAO.getAllAppointments());
                } else {
                    filterList(selectedRadioButton == mainMenuWeekly);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    });
}

/**
 * This is a method used to sort.  It gets the date and sends the information into
 * the method used to sort the table by current week, or current month if that is selected.
 *
 * @param byWeek is selected when the radio button is clicked.
 * @throws SQLException
 */
private void filterList(boolean byWeek) throws SQLException {
    if (byWeek) {
        var pair = CalendarHelper.getDateRange(Calendar.DAY_OF_WEEK);
        appointmentTable.setItems(AppointmentDAO.getAllAppointments(pair.getKey(), pair.getValue()));
    } else {
        var pair = CalendarHelper.getDateRange(Calendar.DAY_OF_MONTH);
        appointmentTable.setItems(AppointmentDAO.getAllAppointments(pair.getKey(), pair.getValue()));
    }
}

/**
 * This method loads the date.  This method gets the current date.
 *
 * @return the current date.
 */

private static Calendar getCalendarForNow() {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    return calendar;
}

public void mainMenuAddAppointmentButton(ActionEvent actionEvent) {
}

/**
 * This is a method that loads a new stage when selected.  This method loads the add appointment stage.
 *
 * @param mouseEvent loads when the mouse clicks the button.
 * @throws IOException
 * @throws SQLException
 */
public void addAppointment(MouseEvent mouseEvent) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("AddAppointment.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Add Appointment");

    Scene scene = new Scene(root);
    stage.setScene(scene);

    AddAppointmentController addApp = loader.getController();

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

/**
 * This method loads a new window when clicked.  This method loads the modify appointment
 * window for the selected appointment.  It loads the information for the selected appointment.
 *
 * @param mouseEvent loads when the mouse clicks the button.
 * @throws IOException
 */
public void appointmentModify(MouseEvent mouseEvent) throws IOException {
    Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("EditAppointment.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Edit Appointment");
    Scene scene = new Scene(root);
    stage.setScene(scene);

    EditAppointment edit = loader.getController();
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

/**
 * This is a method that loads an alert.  This method deletes an appointment
 * from the appointment database. Asks the user to confirm they want to delete
 * the appointment.
 *
 * @param mouseEvent loads when the mouse clicks the button.
 * @throws SQLException
 */
public void appointmentDelete(MouseEvent mouseEvent) throws SQLException {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Delete Appointment");
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
            System.out.println("Appointment deleted!");
        } else if (response == buttonTypeNo) {
            // User chose not to delete, do nothing or handle accordingly
            System.out.println("Delete canceled.");
        }
    });

}

/**
 * This is a logout method.  This method logs the User back out to the main
 * login screen.
 *
 * @param mouseEvent when the button is mouse clicked.
 * @throws IOException
 */

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

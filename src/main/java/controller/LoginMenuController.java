package controller;

import com.example.fofiuappointments.AppointmentScheduler;
import dao.AppointmentDAO;
import db.JDBC;
import helper.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {

public Button loginExitButton;
@FXML
private Label labelLocation;

@FXML
private Label labelLogin;

@FXML
private Label labelPassword;

@FXML
private Label labelUserName;

@FXML
private TextField locationDisplay;

@FXML
private PasswordField loginPassword;

@FXML
private TextField loginUserName;

@FXML
private Button loginButton;

@FXML
public void loginButton(ActionEvent event) throws IOException, SQLException {

    String user = loginUserName.getText();
    String password = loginPassword.getText();
    int User_ID = JDBC.checkLogin(user, password);

    FileWriter fileWriter = new FileWriter("login_activity.txt", true);
    PrintWriter recordAttempt = new PrintWriter(fileWriter);

    if (User_ID > 0) {
        Utils.loginUser = user;
        var appointment = AppointmentDAO.getNextAppointment(Utils.convertLocalDateTimeToDateUsingInstant(LocalDateTime.now(ZoneId.of("UTC"))), User_ID);

        if (appointment.getAppointment_ID() > 0) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("dictionary", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("Appointment"));
            alert.setHeaderText(resourceBundle.getString("Upcoming"));
            alert.setContentText(String.valueOf(appointment.getAppointment_ID() + "\r\n" + appointment.getTitle() + "\r\n" + String.valueOf(appointment.getStart()) + "\r\n" + String.valueOf(appointment.getEnd())));
            alert.showAndWait();
        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("dictionary", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("Title"));
            alert.setHeaderText(resourceBundle.getString("NoUpcoming"));
            alert.setContentText(resourceBundle.getString("NoUpcomingDialog"));
            alert.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("MainMenuScreen.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Appointment Scheduler");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        recordAttempt.print("user: " + loginUserName.getText() + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

        Stage currentStage = (Stage) loginUserName.getScene().getWindow();
        currentStage.close();

    } else {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("dictionary", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resourceBundle.getString("Title"));
        alert.setHeaderText(resourceBundle.getString("Error"));
        alert.setContentText(resourceBundle.getString("Incorrect"));
        alert.show();

        recordAttempt.print("user: " + loginUserName.getText() + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
    }

    recordAttempt.close();

}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {

        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        RefreshLanguage(locale, resourceBundle);

    } catch (MissingResourceException e) {
        System.out.println("Resource file missing: " + e);
    } catch (Exception e) {
        System.out.println(e);
    }
}

public void RefreshLanguage(Locale locale, ResourceBundle resourceBundle) {
    resourceBundle = ResourceBundle.getBundle("dictionary", locale);
    labelLogin.setText(resourceBundle.getString("Login"));
    labelUserName.setText(resourceBundle.getString("username"));
    labelPassword.setText(resourceBundle.getString("password"));
    loginButton.setText(resourceBundle.getString("Login"));
    loginExitButton.setText(resourceBundle.getString("Exit"));
    ZoneId zone = ZoneId.systemDefault();
    locationDisplay.setText(String.valueOf(zone));
    labelLocation.setText(resourceBundle.getString("Location"));

}

public void loginExitButton(ActionEvent actionEvent) {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("dictionary", Locale.getDefault());
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(resourceBundle.getString("Confirmation"));
    alert.setHeaderText(resourceBundle.getString("Terminate"));
    alert.setContentText(resourceBundle.getString("Question"));

    ButtonType buttonTypeYes = new ButtonType(resourceBundle.getString("Yes"));
    ButtonType buttonTypeNo = new ButtonType(resourceBundle.getString("No"));
    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    alert.showAndWait().ifPresent(response -> {
        if (response == buttonTypeYes) {
            Platform.exit();
            System.out.println("App Exited!");
        } else if (response == buttonTypeNo) {
            System.out.println("Exit Cancelled");
        }
    });
}
}

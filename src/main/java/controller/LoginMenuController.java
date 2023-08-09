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

/**
 * This is the login controller class. This class logs the user into the application. When the user
 * enters a valid username and password, it is verified against the database.  If it verifies it logs the user in, if not, it lets
 * the user know that the login is not valid.  It also checks to see if there are any upcoming appointments in the next 15 minutes and
 * lets the user know, also lets the user know if there are none in the next 15 minutes.  Also, the page loads in French if the computer shows in that location.
 *
 * @author Gavril Fofiu
 */

public class LoginMenuController implements Initializable {
/**
 * This is the exit application button.
 */
public Button loginExitButton;
/**
 * This is location label.
 */
@FXML
private Label labelLocation;
/**
 * This the Login text label.
 */

@FXML
private Label labelLogin;
/**
 * This is the label for the password.
 */

@FXML
private Label labelPassword;
/**
 * This is the label for the username.
 */

@FXML
private Label labelUserName;
/**
 * This is the text field for the location display.
 */

@FXML
private TextField locationDisplay;
/**
 * This is the password field for the password.
 */

@FXML
private PasswordField loginPassword;
/**
 * This is the text field for the login username.
 */

@FXML
private TextField loginUserName;
/**
 * This is the login button.
 */

@FXML
private Button loginButton;

/**
 * This is the login method. This method checks the database for a valid login. It also checks the upcoming appointments,
 * if there are any in the next 15 minutes, it notifies the user of which appointment is in the next 15 minutes.  If not, it
 * notifies the user there are no appointments in the next 15 minutes. It also records in a text file, all login attempts the
 * failed and successful ones, as well as the user attempting to log in.
 *
 * @param event on click of login button.
 * @throws IOException
 * @throws SQLException
 */

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

/**
 * This is the main initialize method for the login controller. This method checks the location to
 * see which language to load in.
 *
 * @param url
 * @param resourceBundle
 */

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

/**
 * This is a refresh method. This method checks the location of the computer running the application.
 * If the location is English, it loads English.  If the location is France, it loads French.
 *
 * @param locale loads location resources on load.
 * @param resourceBundle
 */

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

/**
 * This is an exit method. This method exits ask and confirms if the user wants to exit the application.
 * If the user clicks on the no, it does not terminate, but if they click yes, it terminates the application.
 *
 * @param actionEvent click on the exit button.
 */

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

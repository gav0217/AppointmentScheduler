package controller;

import com.example.fofiuappointments.AppointmentScheduler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This the Main Screen controller. This controller loads the appointment tab by default, and
 * the user can select which tab they want to view, the appointment tab, customer tab, or report tab.
 *
 * @author Gavril Fofiu
 */
public class MainMenuScreenController implements Initializable {
/**
 * This is the tab pane.
 */
public TabPane tabPane;
/**
 * This the customer tab.
 */
@FXML
private Tab customerTab;
/**
 * This is the appointment tab.
 */

@FXML
private Tab appointmentsTab;
/**
 * This is the report's tab.
 */

@FXML
private Tab reportsTab;

/**
 * This the main initialize method for the main screen controller. This method listens to the user to
 * see which tab the user selects and loads it.
 *
 * @param url
 * @param resourceBundle
 */

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

    tabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab tabNext) {
                    try {
                        switchTab(tabNext);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    );

    try {
        switchTab(appointmentsTab);

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

/**
 * This method switches the tab.  This method loads the selected tab for the user.
 *
 * @param tab loads the clicked tab.
 * @throws IOException
 */

void switchTab(Tab tab) throws IOException {
    FXMLLoader selectedTab = switch (tab.getText()) {
        case "Appointments" -> new FXMLLoader(AppointmentScheduler.class.getResource("Appointment.fxml"));
        case "Customers" -> new FXMLLoader(AppointmentScheduler.class.getResource("Customer.fxml"));
        case "Reports" -> new FXMLLoader(AppointmentScheduler.class.getResource("Report.fxml"));
        default -> null;
    };

    if(selectedTab != null) {
        AnchorPane view = selectedTab.load();
        tab.setContent(view);
    }
}
}
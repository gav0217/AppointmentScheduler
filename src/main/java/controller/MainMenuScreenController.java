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

public class MainMenuScreenController implements Initializable {
public TabPane tabPane;
@FXML
private Tab customerTab;

@FXML
private Tab appointmentsTab;

@FXML
private Tab reportsTab;


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
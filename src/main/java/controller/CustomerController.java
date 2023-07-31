package controller;

import com.example.fofiuappointments.AppointmentScheduler;
import dao.CustomerDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

@FXML
public TableView customerTable;
public Button customerDeleteButton;
public Button customerModifyButton;
public Button customerAddButton;


@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        customerTable.setItems(CustomerDAO.getAllCustomers());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

public void customersModify(MouseEvent mouseEvent) throws IOException, SQLException {
    Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("EditCustomer.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Edit Customer");
    Scene scene = new Scene(root);
    stage.setScene(scene);

    EditCustomer edit =  loader.getController();
    edit.setCustomer(customer);

    stage.show();
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
            try {
                customerTable.setItems(CustomerDAO.getAllCustomers());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    });
}

public void mainMenuAddCustomerButton() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("AddCustomer.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Add Customer");
    Scene scene = new Scene(root);
    stage.setScene(scene);

    AddCustomerController addCust =  loader.getController();

    addCust.setNewCustomer(CustomerDAO.getNextCustomerId());

    stage.show();
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
            try {
                customerTable.setItems(CustomerDAO.getAllCustomers());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    });
}

public void customersDelete(MouseEvent mouseEvent) throws SQLException {
    Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
    boolean haveAppointment = CustomerDAO.checkCustomerAppointment(customer.getCustomer_ID());

    if(haveAppointment) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Customer has Appointment!");
        alert.show();
    }
    else {
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
                CustomerDAO.DeleteCustomer(customer);
                try {
                    customerTable.setItems(CustomerDAO.getAllCustomers());
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
}

public void customersAdd(MouseEvent mouseEvent) {
}
}

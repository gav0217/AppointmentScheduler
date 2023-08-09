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

/**
 * This is the customer controller class.  This tab is loaded when the user selects the customer's tab.
 * This class loads a table view with all the current customers. It also has buttons to add, delete, and modify
 * customers.
 *
 * @author Gavril Fofiu
 */
public class CustomerController implements Initializable {
/**
 * This is a tableview for customers.
 */
@FXML
public TableView customerTable;
/**
 * This is a button to delete customers.
 */
public Button customerDeleteButton;
/**
 * This is a button to modify customers.
 */
public Button customerModifyButton;
/**
 * This is a button to add new customers.
 */
public Button customerAddButton;

/**
 * This is the main initialize method for the class. This method loads the customer table view
 * which lists all the customers.
 *
 * @param url
 * @param resourceBundle
 */
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        customerTable.setItems(CustomerDAO.getAllCustomers());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

/**
 * This is the modify customer method.  This method loads and sets the information for the selected customer
 * in a new window. When the window is closed, it refreshes the customer table view.
 *
 * @param mouseEvent when button is mouse clicked.
 * @throws IOException
 * @throws SQLException
 */
public void customersModify(MouseEvent mouseEvent) throws IOException, SQLException {
    Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("EditCustomer.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Edit Customer");
    Scene scene = new Scene(root);
    stage.setScene(scene);

    EditCustomer edit = loader.getController();
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

/**
 * This is the add new customer method.  It opens a new window into which the user inputs the new customer information.
 * Once it is closed, it refreshes the customer table view.
 *
 * @throws IOException
 * @throws SQLException
 */
public void mainMenuAddCustomerButton() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(AppointmentScheduler.class.getResource("AddCustomer.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setTitle("Add Customer");
    Scene scene = new Scene(root);
    stage.setScene(scene);

    AddCustomerController addCust = loader.getController();

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

/**
 * This is the customer delete method.  The method checks if the customer has any appointments and if they do,
 * it notifies the user that the customer has an appointment.  If the customer has no appointments, it asks the user
 * to confirm they want to delete the customer, and then it deletes the customer from the database.
 *
 * @param mouseEvent when button is mouse clicked.
 * @throws SQLException
 */

public void customersDelete(MouseEvent mouseEvent) throws SQLException {
    Customer customer = (Customer) customerTable.getSelectionModel().getSelectedItem();
    boolean haveAppointment = CustomerDAO.checkCustomerAppointment(customer.getCustomer_ID());

    if (haveAppointment) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Customer has Appointment!");
        alert.show();
    } else {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Are you sure you want to delete this Customer?");

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

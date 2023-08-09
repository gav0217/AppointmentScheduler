package dao;

import db.JDBC;
import helper.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.*;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * This is the Appointment DAO class. This is the interface for the implementation of the appointment class.
 *
 * @author Gavril Fofiu
 */

public class AppointmentDAO {
/**
 * This is get all appointments method. This method returns all the appointments from the database and
 * adds them to the Appointment Observable List.
 *
 * @return all appointments list.
 * @throws SQLException
 */

public static ObservableList<Appointment> getAllAppointments() throws SQLException {
    return getAllAppointments(null, null);
}
/**
 * This method formats the date.  Sets it in SimpleDateFormat.
 */
private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

/**
 * This is a get appointment method.  This method searchs the database for appointments between a specific date range.
 *
 * @param start the desired start date range.
 * @param end the desired end date range.
 * @return the selected rows from the database.
 * @throws SQLException
 */
public static ObservableList<Appointment> getAllAppointments(Date start, Date end) throws SQLException {
    ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
    String sql = "SELECT * from appointments";
    if (start != null && end != null) {
        sql += " where Start > '" + dateFormat.format(start) + "' and end < '" + dateFormat.format(end) + "'";
    }

    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Appointment appointment = new Appointment();
        appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
        appointment.setTitle(rs.getString("Title"));
        appointment.setDescription(rs.getString("Description"));
        appointment.setLocation(rs.getString("Location"));
        appointment.setType(rs.getString("Type"));
        appointment.setStart(rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId).toLocalDateTime());
        appointment.setEnd(rs.getTimestamp("End").toLocalDateTime().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId).toLocalDateTime());
        appointment.setCustomer_ID(rs.getInt("Customer_ID"));
        appointment.setUser_ID(rs.getInt("User_ID"));
        appointment.setContact_ID(rs.getInt("Contact_ID"));
        appointmentsObservableList.add(appointment);
    }

    return appointmentsObservableList;
}

/**
 * This method is an add appointment method. This method adds new appointments to the database.
 * It used the appointment model to add required columns to the database.
 *
 * @param model is the appointment model.
 * @throws SQLException
 */
public static void AddAppointment(Appointment model) throws SQLException {
    String insertSQL = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                               " VALUES (?, ?, ?, ?, ?, ?, NOW(), '" + Utils.loginUser + "', NOW(),  '" + Utils.loginUser + "', ?, ?, ?)";

    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        // Set the parameter values for the insert statement
        preparedStatement.setString(1, model.getTitle());
        preparedStatement.setString(2, model.getDescription());
        preparedStatement.setString(3, model.getLocation());
        preparedStatement.setString(4, model.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(model.getStart().atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(model.getEnd().atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
        preparedStatement.setInt(7, model.getCustomer_ID());
        preparedStatement.setInt(8, model.getUser_ID());
        preparedStatement.setInt(9, model.getContact_ID());

        // Execute the insert statement
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Row inserted successfully!");
        } else {
            System.out.println("Failed to insert row!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

}
/**
 * This is a delete appointment method. This method deletes an appointment from the database.
 * It deletes by the appointment ID.
 *
 * @param app is an appointment model.
 */
public static void DeleteAppointment(Appointment app) {
    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("delete from appointments where Appointment_ID = " + app.getAppointment_ID())) {
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static ZoneId zoneId = ZoneId.systemDefault();

/**
 * This the modify appointment method. It updates the appointments based on the appointment model.
 *
 * @param model is a model of an appointment.
 */
public static void ModifyAppoinment(Appointment model) {
    String updateSQL = "Update appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), " +
                               "Last_Updated_By = '" + Utils.loginUser + "', Customer_ID = ?, User_ID = ?, Contact_ID = ? Where Appointment_ID = ?";

    //ZoneId zoneId1 = ZoneId.of("America/New_York");
    //LocalDateTime utcLocalDateTime = .toLocalDateTime();
    var sada = model.getStart().atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toInstant();
    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

        // Set the parameter values for the insert statement
        preparedStatement.setString(1, model.getTitle());
        preparedStatement.setString(2, model.getDescription());
        preparedStatement.setString(3, model.getLocation());
        preparedStatement.setString(4, model.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(model.getStart().atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(model.getEnd().atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
        preparedStatement.setInt(7, model.getCustomer_ID());
        preparedStatement.setInt(8, model.getUser_ID());
        preparedStatement.setInt(9, model.getContact_ID());
        preparedStatement.setInt(10, model.getAppointment_ID());

        preparedStatement.executeUpdate();


    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * This method gets the next appointment. It gets the next appointment within the next 15 minutes.
 *
 * @param timeNow the current time.
 * @param User_ID the logged user.
 * @return the appointment information for the appointment within 15 minutes.
 * @throws SQLException
 */
public static Appointment getNextAppointment(Date timeNow, int User_ID) throws SQLException {
    Appointment appointment = new Appointment();
    Calendar cal = Calendar.getInstance();
    cal.setTime(timeNow);
    cal.add(Calendar.MINUTE, 15);
    String sql = "select * from appointments where User_ID = " + String.valueOf(User_ID) + " AND Start > '" + dateFormat.format(timeNow) + "' AND Start < '" + dateFormat.format(cal.getTime()) + "'";

    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
        appointment.setTitle(rs.getString("Title"));
        appointment.setStart(rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId).toLocalDateTime());
        appointment.setEnd(rs.getTimestamp("End").toLocalDateTime().atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId).toLocalDateTime());
    }

    return appointment;
}

/**
 * This method checks for overlaps.  This method checks if there is an overlap in new or updated appointment.
 *
 * @param start start times of appointments.
 * @param end end times of appointments.
 * @param id the ID of the appointments.
 * @return an error message for the overlap.
 * @throws SQLException
 */

public static boolean isOverlap(LocalDateTime start, LocalDateTime end, int id) throws SQLException {

    boolean isOverlap = true;
    String sql = "SELECT Count(*) as count " +
                         "FROM appointments " +
                         "WHERE ? <= end " +
                         "AND ? >= start";

    if (id > 0) {
        sql += " AND Appointment_ID != " + String.valueOf(id);
    }

    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
    ps.setTimestamp(1, Timestamp.valueOf(start.atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
    ps.setTimestamp(2, Timestamp.valueOf(end.atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));

    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        isOverlap = rs.getInt("count") > 0;
    }

    return isOverlap;
}

/**
 * This method gets the next ID for appointments. The method checks the database for the last appointment,
 * and returns the appointment ID in the add appointment window.
 *
 * @return the appointment ID for new appointment.
 * @throws SQLException
 */
public static int getNextAppointmentId() throws SQLException {
    int max = 1;
    try {
        String sql = "select MAX(Appointment_ID) as max from appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            max = rs.getInt("max") + 1;

        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return max;
}
}


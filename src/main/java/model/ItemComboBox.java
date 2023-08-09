package model;

/**
 * This the class for ItemComboBox.
 *
 * @author Gavril Fofiu
 */
public class ItemComboBox {
final int id;
final String text;

/**
 * This method creates a combo box.
 *
 * @param id   sets the customer ID.
 * @param text sets the customer name.
 */
public ItemComboBox(int id, String text) {
    this.id = id;
    this.text = text;
}

/**
 * @return the customer names for the combo box.
 */
public String getName() {
    return text;
}

/**
 * @return the customer ID for the combo box.
 */
public Integer getId() {
    return id;
}
}

package model;

public class ItemComboBox {
    final int id;
    final String text;

    public ItemComboBox(int id, String text) {
        this.id = id;
        this.text = text;
    }
    public String getName()
    {
        return text;
    }

public Integer getId()
{
    return id;
}
}

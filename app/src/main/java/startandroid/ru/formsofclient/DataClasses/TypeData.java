package startandroid.ru.formsofclient.DataClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Администратор on 01.10.2018.
 */

public class TypeData {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("textName")
    private String textName;

    @SerializedName("textNameUa")
    private String textNameUa;

    @SerializedName("color")
    private String color;

    @SerializedName("categoryId")
    private int categoryId;

    @SerializedName("identifier")
    private String identifier;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTextName() {
        return textName;
    }

    public String getTextNameUa() {
        return textNameUa;
    }

    public String getColor() {
        return color;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public void setTextNameUa(String textNameUa) {
        this.textNameUa = textNameUa;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}

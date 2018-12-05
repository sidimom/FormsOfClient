package startandroid.ru.formsofclient.DataClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Администратор on 01.10.2018.
 */

public class User {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

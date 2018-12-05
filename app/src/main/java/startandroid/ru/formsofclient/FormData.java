package startandroid.ru.formsofclient;

/**
 * Created by Администратор on 22.10.2018.
 */

public class FormData {
    private int id, id_base;
    private String name;

    public FormData(int id, int id_base, String name) {
        this.id = id;
        this.id_base = id_base;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getId_base() {
        return id_base;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_base(int id_base) {
        this.id_base = id_base;
    }

    public void setName(String name) {
        this.name = name;
    }
}

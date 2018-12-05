package startandroid.ru.formsofclient.DataClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Администратор on 01.10.2018.
 */

public class Circumstances {

    @SerializedName("id")
    private int id;

    @SerializedName("nameUa")
    private String nameUa;

    @SerializedName("nameEn")
    private String nameEn;

    @SerializedName("identifier")
    private String identifier;

    @SerializedName("tmspDtCreate")
    private int tmspDtCreate;

    @SerializedName("tmspDtChange")
    private int tmspDtChange;

    public int getId() {
        return id;
    }

    public String getNameUa() {
        return nameUa;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getTmspDtCreate() {
        return tmspDtCreate;
    }

    public int getTmspDtChange() {
        return tmspDtChange;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setTmspDtCreate(int tmspDtCreate) {
        this.tmspDtCreate = tmspDtCreate;
    }

    public void setTmspDtChange(int tmspDtChange) {
        this.tmspDtChange = tmspDtChange;
    }
}

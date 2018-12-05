package startandroid.ru.formsofclient;

/**
 * Created by Администратор on 23.10.2018.
 */

public class ResponceData {

    private int fillId, currentlyEngagedId, actualPresenceId, usingEquipmentId, grantAmount;

    public ResponceData(int fillId, int currentlyEngagedId, int actualPresenceId, int usingEquipmentId, int grantAmount) {
        this.fillId = fillId;
        this.currentlyEngagedId = currentlyEngagedId;
        this.actualPresenceId = actualPresenceId;
        this.usingEquipmentId = usingEquipmentId;
        this.grantAmount = grantAmount;
    }

    public int getFillId() {
        return fillId;
    }

    public int getCurrentlyEngagedId() {
        return currentlyEngagedId;
    }

    public int getActualPresenceId() {
        return actualPresenceId;
    }

    public int getUsingEquipmentId() {
        return usingEquipmentId;
    }

    public int getGrantAmount() {
        return grantAmount;
    }

    public void setFillId(int fillId) {
        this.fillId = fillId;
    }

    public void setCurrentlyEngagedId(int currentlyEngagedId) {
        this.currentlyEngagedId = currentlyEngagedId;
    }

    public void setActualPresenceId(int actualPresenceId) {
        this.actualPresenceId = actualPresenceId;
    }

    public void setUsingEquipmentId(int usingEquipmentId) {
        this.usingEquipmentId = usingEquipmentId;
    }

    public void setGrantAmount(int grantAmount) {
        this.grantAmount = grantAmount;
    }
}

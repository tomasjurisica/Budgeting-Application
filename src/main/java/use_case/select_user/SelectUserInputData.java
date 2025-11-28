package use_case.select_user;

public class SelectUserInputData {
    private final String roommateName;

    public SelectUserInputData(String roommateName) {
        this.roommateName = roommateName;
    }

    public String getRoommateName() {
        return roommateName;
    }
}

package use_case.select_user;

public class SelectUserOutputData {
    private final String roommateName;

    public SelectUserOutputData(String roommateName) {
        this.roommateName = roommateName;
    }

    public String getRoommateName() {
        return roommateName;
    }
}

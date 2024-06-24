package Entity;

public enum RoomType {
    S("Single"), D("Double"), QN("Queen"), QD("Quad"), T("Triple");
    private String type;
    private RoomType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}

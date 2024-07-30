package controller.tcp.getAllSquadRequest;

public class GetAllSquadHelper {

    private String name;
    private int memberCount;

    public GetAllSquadHelper(String name, int memberCount) {
        this.name = name;
        this.memberCount = memberCount;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}

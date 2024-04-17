package dto;

public class IndexedUserDTO {
    private int index;
    private String userId;
    private String name;
    private String email;

    public IndexedUserDTO(int index, String userId, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

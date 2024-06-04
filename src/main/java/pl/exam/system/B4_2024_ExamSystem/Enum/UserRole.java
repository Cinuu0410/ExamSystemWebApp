package pl.exam.system.B4_2024_ExamSystem.Enum;

public enum UserRole {
    ADMINISTRATOR("Administrator"),
    EGZAMINATOR("Egzaminator"),
    EGZAMINOWANY("Egzaminowany");

    private final String roleName;
    UserRole(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleName() {
        return roleName;
    }
}

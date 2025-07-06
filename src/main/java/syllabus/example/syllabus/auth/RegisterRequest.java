package syllabus.example.syllabus.auth;


import syllabus.example.syllabus.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String grade;
    private String function;
    private String email;
    private String password;

    private String confirmPassword;

    private Role role;

    public String getEmail() {
        return this.email;
    }



}

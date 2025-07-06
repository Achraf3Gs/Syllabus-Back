package syllabus.example.syllabus.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import syllabus.example.syllabus.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String message;

    @JsonProperty("access_Token")
    private String accesstoken;

    private String FirstName;
    private String LastName;
    private String grade;
    private String function;
    private Role role;
    private Integer id;
    private Date accessTokenExpiration;



}
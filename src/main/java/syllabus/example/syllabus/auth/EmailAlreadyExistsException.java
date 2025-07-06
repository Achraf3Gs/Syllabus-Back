package syllabus.example.syllabus.auth;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
package authenticationService.server;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    @NonNull
    private String username;
    @NonNull
    private String password;
    private LocalDate dateOfBirth;

    public User() {
        username = "";
        password = "";
        dateOfBirth = null;
    }

    public User(String username, String password, LocalDate dateOfBirth) {
        this.username = username.toLowerCase();
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(@NonNull LocalDate dateOfBirth) {
        if (dateOfBirth.isBefore(LocalDate.now())) {
            return;
        }
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Formats the user object into human-readable string
     *
     * @return Formatted user object
     */
    public String format() {
        return "%s DOB: %s".formatted(username, dateOfBirth.toString());
    }

    /**
     * Checks whether the user is over 18 years old
     *
     * @return True if the user is over 18, false otherwise.
     */
    public boolean isOver18() {
        return LocalDate.now().minusYears(18).isBefore(dateOfBirth);
    }
}

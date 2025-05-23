package tcpThreaded.server;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import tcpThreaded.business.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, User> users;
    @Getter
    @Setter(onParam_ = @NonNull)
    private User currentUser;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public User getUser(String email) {
        return users.get(email);
    }

    public boolean add(@NonNull String email, @NonNull String password) {
        User u = new User(email, password);
        return add(u);
    }

    public boolean add(User u) {
        if (!users.containsKey(u.getEmail())) {
            users.put(u.getEmail(), u);
            return true;
        }
        return false;
    }
}

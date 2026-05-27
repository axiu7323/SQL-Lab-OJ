package com.sqloj.domain.user;

import java.util.Objects;

public class User {

    private final UserId id;
    private final Username username;
    private PasswordHash passwordHash;
    private UserRole role;
    private UserStatus status;

    public User(UserId id, Username username, PasswordHash passwordHash, UserRole role, UserStatus status) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.username = Objects.requireNonNull(username, "username must not be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash must not be null");
        this.role = Objects.requireNonNull(role, "role must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    public static User create(UserId id, Username username, PasswordHash passwordHash, UserRole role) {
        return new User(id, username, passwordHash, role, UserStatus.ACTIVE);
    }

    public boolean canLogin() {
        return status == UserStatus.ACTIVE;
    }

    public boolean isStudent() {
        return role == UserRole.STUDENT;
    }

    public boolean isTeacher() {
        return role == UserRole.TEACHER;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public void changePassword(PasswordHash newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash, "newPasswordHash must not be null");
    }

    public void changeRole(UserRole newRole) {
        this.role = Objects.requireNonNull(newRole, "newRole must not be null");
    }

    public void enable() {
        this.status = UserStatus.ACTIVE;
    }

    public void disable() {
        this.status = UserStatus.DISABLED;
    }

    public UserId getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }
}

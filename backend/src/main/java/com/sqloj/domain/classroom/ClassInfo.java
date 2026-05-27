package com.sqloj.domain.classroom;

import com.sqloj.domain.shared.exception.DomainException;

import java.util.Objects;

public class ClassInfo {

    private final ClassId id;
    private String name;
    private boolean active;

    public ClassInfo(ClassId id, String name, boolean active) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        rename(name);
        this.active = active;
    }

    public static ClassInfo create(ClassId id, String name) {
        return new ClassInfo(id, name, true);
    }

    public void rename(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("class name must not be blank");
        }
        this.name = name.trim();
    }

    public void enable() {
        this.active = true;
    }

    public void disable() {
        this.active = false;
    }

    public ClassId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}


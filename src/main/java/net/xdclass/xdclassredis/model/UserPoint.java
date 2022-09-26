package net.xdclass.xdclassredis.model;

import java.util.Objects;

public class UserPoint {
    private String name;
    private String phone;

    public UserPoint() {
    }

    public UserPoint(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPoint userPoint = (UserPoint) o;
        return Objects.equals(name, userPoint.name) && Objects.equals(phone, userPoint.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }
}

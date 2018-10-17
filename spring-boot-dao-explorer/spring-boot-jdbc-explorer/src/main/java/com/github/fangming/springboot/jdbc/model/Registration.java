package com.github.fangming.springboot.jdbc.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Registration {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    public Registration(Long id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;
        Registration that = (Registration) o;
        return age == that.age &&
            Objects.equal(id, that.id) &&
            Objects.equal(firstName, that.firstName) &&
            Objects.equal(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, lastName, age);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("age", age)
            .toString();
    }
}

package ru.kata.spring.boot_security.demo.dto;

import java.util.Set;

public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private Set<Long> roleIds;

    public UserDto() {}

    public UserDto(Long id, String email, String password, String firstName,
                   String lastName, int age, Set<Long> roleIds) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roleIds = roleIds;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Set<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(Set<Long> roleIds) { this.roleIds = roleIds; }
}
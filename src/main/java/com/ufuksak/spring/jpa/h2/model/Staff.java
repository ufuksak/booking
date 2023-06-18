package com.ufuksak.spring.jpa.h2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Staff{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    @Column
    private String login;
    @Column
    private String password;

    public Staff(String name, AccessLevel accessLevel, String login, String password) {
        this.name = name;
        this.accessLevel = accessLevel;
        this.login = login;
        this.password = password;
    }

    public Staff() {
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accessLevel=" + accessLevel +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

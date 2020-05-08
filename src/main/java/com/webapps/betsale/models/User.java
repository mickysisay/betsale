package com.webapps.betsale.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {
    @NotNull
    private String username;
    @OneToMany
    @JoinColumn
    private List<Home> homes;
    @NotBlank
    @Email
    private String emailAddress;
    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {

    }
    public void Addhome(Home home){
        homes.add(home);
    }
    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}

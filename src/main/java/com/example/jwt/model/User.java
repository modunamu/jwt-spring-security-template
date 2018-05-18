package com.example.jwt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "USER", 
		indexes = {@Index(name = "USER_HISTORY_IDX1",  columnList="username", unique = true)})
public class User extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Pattern(regexp = "^[^<>\\\\'\"&;%]*$")
    @Column(length = 50, unique = true)
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @Column(length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String firstname;

    @Column(length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String lastname;

    @Column(length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    private Boolean enabled = true;
    
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private List<Authority> authorities;
    
    @Builder
    public User(        Long id,
            String username,
            String firstname,
            String lastname,
            String password,
            String email,
            List<Authority> authorities,
            boolean enabled,
            Date lastPasswordResetDate) {
    	
    		this.id = id;
    		this.username = username;
    		this.firstname = firstname;
    		this.lastname = lastname;
    		this.password = password;
    		this.email = email;
    		this.authorities = authorities;
    		this.enabled = enabled;
    		this.lastPasswordResetDate = lastPasswordResetDate;

    }
    
}
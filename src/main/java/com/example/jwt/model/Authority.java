package com.example.jwt.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "AUTHORITY")
public class Authority extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    private Long id;

    @Column(length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName authorityName;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

    public Authority(AuthorityName authorityName) {
    		this.authorityName = authorityName;
    }
}
package com.health.yogiodigym.calendar.entity;

import com.health.yogiodigym.calendar.auth.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String pwd;

    @Column(length = 10)
    private String gender;

    @Column
    private Float weight;

    @Column
    private Float height;

    @Column
    private String addr;

    @Column
    private Float latitude;

    @Column
    private Float longitude;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "drop_date")
    private LocalDate dropDate;

    @Column
    private String profile;

    @Column
    private String status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "authority", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    @Column(name = "role")
    private Set<Role> roles;
}

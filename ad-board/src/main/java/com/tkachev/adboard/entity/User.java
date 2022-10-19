package com.tkachev.adboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "board_user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "rating")
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Ad> ads;

    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Review> reviews;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_chat",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "chat_id")})
    private Set<Chat> chats;

    @ToString.Exclude
    @OneToMany(mappedBy = "sender", cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Message> messages;

    public void addChatToUser(Chat chat) {
        if (chats == null) {
            chats = new HashSet<>();
        }

        chats.add(chat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                status == user.status &&
                role == user.role &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, status, role, email, password);
    }
}

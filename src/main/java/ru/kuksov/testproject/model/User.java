package ru.kuksov.testproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Данные таблицы "users" в БД
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID, generator = "user_uuid_seq")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID uuid;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @JoinColumn(name = "role")
    @ManyToOne
    private Role role;

}
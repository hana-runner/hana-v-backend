package com.v.hana.entity.user;

import com.v.hana.common.constant.Gender;
import com.v.hana.entity.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Entity e = new Entity()
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = false, columnDefinition = "INT")
    private Gender gender;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "is_receive_email")
    private boolean isReceiveEmail = true;

    @Column(name = "is_receive_alarm")
    private boolean isReceiveAlarm = true;

    @Builder
    public User(
            String username,
            String pw,
            String email,
            String name,
            Gender gender,
            LocalDate birthday) {
        this.username = username;
        this.pw = pw;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }
}

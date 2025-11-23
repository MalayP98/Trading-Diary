package com.trading.diary.pojo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreationTimestamp
    protected LocalDateTime creationDate;

    @UpdateTimestamp
    protected LocalDateTime updateDate;

    protected boolean deleted;
}

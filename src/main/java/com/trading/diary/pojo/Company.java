package com.trading.diary.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Company {

    @Id
    private String companySymbol;

    public Company(String companySymbol) {
        this.companySymbol = companySymbol;
    }
}

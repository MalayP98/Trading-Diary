package com.trading.diary.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Company {

    @Id
    private String companySymbol;

    public Company(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    @Override
    public String toString(){
        return companySymbol;
    }
}

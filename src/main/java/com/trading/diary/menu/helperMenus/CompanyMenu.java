package com.trading.diary.menu.helperMenus;

import com.trading.diary.menu.AbstractMenu;
import com.trading.diary.menu.MenuName;
import com.trading.diary.pojo.Company;
import com.trading.diary.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMenu extends AbstractMenu<Company> {
    
    private final CompanyService companyService;

    @Override
    public Company showMenu() {
        System.out.println("Enter company symbol: ");
        String companySymbol = InputType.STRING.nextInput();
        return companyService.addCompany(companySymbol);
    }

    @Override
    public MenuName menuName() {
        return MenuName.COMPANY_MENU;
    }
}

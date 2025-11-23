package com.trading.diary.menu.formation_menu.support_reversal.paginationMenus;

import com.trading.diary.menu.AbstractMenu;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimplePaginationMenu<T, R> extends AbstractMenu<R> {

    protected static final int PAGE_SIZE = 5;

    protected final Supplier<Long> countSupplier;

    protected final BiFunction<T, ? super Pageable, List<R>> pageFunction;

    protected final Supplier<T> attributSupplier;

    protected final Function<R, String> display;

    public SimplePaginationMenu(Supplier<Long> countSupplier, BiFunction<T, ? super Pageable, List<R>> pageFunction,
                                Supplier<T> attributSupplier, Function<R, String> display) {
        this.countSupplier = countSupplier;
        this.pageFunction = pageFunction;
        this.attributSupplier = attributSupplier;
        this.display = display;
    }

    @Override
    public R showMenu() {
        long count = countSupplier.get();
        long totalPages = (count/PAGE_SIZE) + (count%PAGE_SIZE > 0 ? 1 : 0);
        if(totalPages == 0){
            print("No data found.");
            return null;
        }
        T attr = attributSupplier.get();
        List<R> page;
        int option;
        for(int i=1; i<=totalPages; i++){
            print("=== Page " + i + " ===");
            page = pageFunction.apply(attr, PageRequest.of(i-1, PAGE_SIZE));
            option = 1;
            for(R content : page) {
                print(option + ": " + display.apply(content));
                option++;
            }
            print("Select from the list or press any other key to move to the next page.");
            int choice = InputType.INT.nextInput();
            if(choice >= 1 && choice <= page.size()){
                return page.get(choice-1);
            }
        }
        print("Nothing selected. Try again!");
        return showMenu();
    }
}

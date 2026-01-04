package com.trading.diary.menu;


import com.trading.diary.menu.factories.MenuFactory;
import com.trading.diary.utils.Helper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Function;

public abstract class AbstractMenu<T> implements Menu<T> {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected enum InputType {
        INT {
            @Override
            public Function<String, Integer> parser() {
                return Integer::parseInt;
            }
        },
        LONG {
            @Override
            public Function<String, Long> parser() {
                return Long::parseLong;
            }
        },
        FLOAT {
            @Override
            protected Function<String, Float> parser() {
                return Float::parseFloat;
            }
        },
        STRING {
            @Override
            protected Function<String, String> parser() {
                return Function.identity();
            }
        },
        BOOLEAN {
            @Override
            protected Function<String, Boolean> parser() {
                return Helper::booleanInputConverter;
            }
        },
        DATE {
            @Override
            protected Function<String, ?> parser() {
                return (input -> LocalDate.parse(input, FORMATTER).atStartOfDay());
            }
        };

        protected abstract Function<String, ?> parser();

        private <R> R inputHelper(boolean isSkipable) {
            try {
                String input = next();
                if (StringUtils.isEmpty(input)) {
                    if (isSkipable) return null;
                    else throw new Exception("Input is not skippable.");
                }
                return (R) this.parser().apply(input);
            } catch (Exception e) {
                print("Error occurred. Message : " + e.getMessage() + " Try again!");
                return inputHelper(isSkipable);
            }
        }

        public <R> R nextInput() {
            return inputHelper(false);
        }

        public <R> R nextSkipableInput() {
            return inputHelper(true);
        }
    }

    private static final Scanner SCANNER = new Scanner(System.in);

    protected static String next() {
        return SCANNER.nextLine();
    }

    @PostConstruct
    protected void init() {
        MenuFactory.addMenu(this);
    }

    protected static void print(String output) {
        System.out.println(output);
    }
}

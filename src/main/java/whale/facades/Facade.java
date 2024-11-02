package whale.facades;

import java.util.Scanner;

public interface Facade {
    static final String SEPARATOR = System.lineSeparator();
    static final String TEXT_MENU = "Для начала работы программы необходимо ввести 1 или START" +
            SEPARATOR +
            "Для выхода из программы \"EXIT\" или 0";
    static final String NEXT_STEP = "Введи адрес ресурса, в котором нужно найти ссылки";
    static final Scanner scanner = new Scanner(System.in);
    static final String INT_PATTERN = "[0-9]";
    static final String WORD_PATTERN = "[a-zA-Z]+";

    void showMenu();
    void menu();
    void postChoosing();
    void exit();
    void printSuccess();
    void showError(String errorMsg);
    boolean isParamsSet();
}

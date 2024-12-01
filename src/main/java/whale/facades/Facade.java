package whale.facades;

public interface Facade {
    static final String SEPARATOR = System.lineSeparator();
    static final String TEXT_MENU = "Для начала работы программы необходимо ввести 1 или START" +
            SEPARATOR +
            "Для выхода из программы \"EXIT\" или 0";
    static final String NEXT_STEP = "Введи адрес ресурса, в котором нужно найти ссылки";
    static final String INT_PATTERN = "[0-9]";
    static final String WORD_PATTERN = "[a-zA-Z]+";

    String availableActions(String param);
    String actions(String param);
    String links(String param);
    String exit(String param);
    String submit(String param);
    String infoMessage(String param);
}

package whale;

public enum Command {
    START(1, "start"),
    EXIT(0, "exit"),
    HTTP(2, "http"),
    FILE(3, "file")
    ;
    private int code;
    private String name;

    Command(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Command getCommandByCode(int code) {
        for (Command command: values()) {
            if(command.code == code) {
                return command;
            }
        }
        throw new IllegalArgumentException("Неверная команда");
    }
}

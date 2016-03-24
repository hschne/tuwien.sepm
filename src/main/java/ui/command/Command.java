package ui.command;

public interface Command<T> {

    void execute();

    void setParameter(T type);
}

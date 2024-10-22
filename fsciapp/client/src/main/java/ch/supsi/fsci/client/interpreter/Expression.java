package ch.supsi.fsci.client.interpreter;

import ch.supsi.fsci.client.command.Command;

abstract public class Expression {
    abstract public Command interpret(Context context);
}

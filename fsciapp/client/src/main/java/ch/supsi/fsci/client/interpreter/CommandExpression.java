package ch.supsi.fsci.client.interpreter;

import ch.supsi.fsci.client.command.Command;

import java.util.StringTokenizer;

public class CommandExpression extends Expression {
    private final String userInput;

    public CommandExpression(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public Command interpret(Context context) {
        StringTokenizer tokenizer = new StringTokenizer(userInput);

        context.setFullInputCommand(userInput);

        // Check if userInput is empty (Is also checked in inputEventHandler)
        if (!tokenizer.hasMoreTokens()) {
            return null;
        }

        String commandKey = tokenizer.nextToken();

        // If command key is valid
        if (context.isValidKey(commandKey)) {
            context.setCommandKey(commandKey);

            // Add arguments to the argument list
            while (tokenizer.hasMoreTokens()) {
                context.addArguments(tokenizer.nextToken());
            }
        }

        return context.retrieveCommand();
    }
}

package ast.type;

import ast.Locatable;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

    private static ErrorHandler instance = new ErrorHandler();
    private List<String> errors = new ArrayList<>();

    private ErrorHandler() {}

    public static ErrorHandler getInstance() {
        return instance;
    }

    public void addError(ErrorType error){
        errors.add(error.toString());
    }

    public boolean anyError() {
        return errors.size()>0;
    }

    public void showErrors(PrintStream out) {
        for (String error : errors) {
            out.println(error);
        }
    }


}
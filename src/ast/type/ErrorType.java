package ast.type;

import ast.Locatable;

public class ErrorType implements Type {

    private String message;
    private int linea;
    private int columna;

    public ErrorType(String message, Locatable locatable) {
        this.message = message;
        this.linea = locatable.getLine();
        this.columna = locatable.getColumn();
        ErrorHandler.getInstance().addError(this);
    }

    @Override
    public String toString() {
        return "ErrorType{" +
                "message='" + message + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }

}

package ast.expression;

import ast.Locatable;

public interface Expression extends Locatable {
    boolean getLValue();
    void setLValue(boolean lvalue);
}

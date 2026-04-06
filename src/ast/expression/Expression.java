package ast.expression;

import ast.Locatable;
import ast.type.Type;

public interface Expression extends Locatable {
    boolean getLValue();
    void setLValue(boolean lvalue);
    void setType(Type type);
    Type getType();
}

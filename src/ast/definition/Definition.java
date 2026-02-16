package ast.definition;

import ast.Locatable;
import ast.type.Type;

public interface Definition extends Locatable {
    String getName();
    Type getType();
}
package ast.type;

import ast.Visitor;

public class CharType implements Type {

    private static CharType instance = new CharType();

    private CharType() {}

    public static CharType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "char";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}

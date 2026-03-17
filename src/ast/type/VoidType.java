package ast.type;

import ast.Visitor;

public class VoidType implements Type {

    private static VoidType instance = new VoidType();

    private VoidType() {}

    public static VoidType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
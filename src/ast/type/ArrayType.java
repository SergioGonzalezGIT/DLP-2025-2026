package ast.type;

import ast.Visitor;

public class ArrayType implements Type {

    private int dimension;
    private Type of;

    public ArrayType(int dimension, Type of) {
        this.dimension = dimension;
        this.of = of;
    }

    public int getDimension() {
        return dimension;
    }

    public Type getOf() {
        return of;
    }

    @Override
    public String toString() {
        return "[" + dimension + "]" + of.toString();
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

}
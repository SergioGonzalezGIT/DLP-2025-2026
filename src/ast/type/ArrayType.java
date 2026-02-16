package ast.type;

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
}
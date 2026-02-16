package ast.type;

public class NumberType implements Type {

    private static NumberType instance = new NumberType();

    private NumberType() {}

    public static NumberType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "number";
    }
}

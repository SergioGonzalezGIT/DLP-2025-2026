package ast.type;

public class IntType implements  Type{

    private static IntType instance = new IntType();

    private IntType() {}

    public static IntType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "int";
    }
}

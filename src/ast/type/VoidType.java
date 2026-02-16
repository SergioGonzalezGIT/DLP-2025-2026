package ast.type;

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
}
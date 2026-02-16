package ast.type;

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
}

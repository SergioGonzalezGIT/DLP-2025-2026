package ast.type;

public class RecordField {

    private String name;

    public RecordField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
package ast.type;

public class RecordField {

    private String name;

    public Type type;

    public RecordField(String name, Type type) {
        this.name = name;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type; // ¡Nuevo!
    }

    @Override
    public String toString() {
        return name + ":" + type; // Ya se imprime él solito con su tipo
    }
}
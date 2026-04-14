package ast.type;

import ast.Locatable;
import ast.Visitor;

public class RecordField implements Locatable {

    private String name;
    private Type type;
    private int line;
    private int column;

    private int offset;


    public RecordField(int line, int column, String name, Type type) {
        this.line = line;
        this.column = column;
        this.name = name;
        this.type = type;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + ":" + type;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }


    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }
}
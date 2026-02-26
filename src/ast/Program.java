package ast;

import ast.definition.Definition;
import java.util.List;

public class Program implements ASTNode {

    private int line;
    private int column;
    private List<Definition> definitions;

    public Program(List<Definition> definitions) {
        //this.line = line;
        //this.column = column;
        this.definitions = definitions;
    }

    public void addDefinitions(List<Definition> defs) {
        this.definitions.addAll(defs);
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Program with " + definitions.size() + " definitions.";
    }
}
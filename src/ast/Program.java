package ast;

import ast.definition.Definition;
import java.util.List;

public class Program implements ASTNode {

    private List<Definition> definitions;

    public Program(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public void addDefinitions(List<Definition> defs) {
        this.definitions.addAll(defs);
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    @Override
    public String toString() {
        return "Program with " + definitions.size() + " definitions.";
    }
}
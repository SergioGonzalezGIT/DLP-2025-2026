package ast.type;

import ast.definition.VarDefinition;
import java.util.List;

public class FunctionType implements Type {

    private List<VarDefinition> parameters;
    private Type returnType;

    public FunctionType(List<VarDefinition> parameters, Type returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<VarDefinition> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "function type";
    }
}
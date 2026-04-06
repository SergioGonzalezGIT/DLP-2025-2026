package ast.type;

import ast.Locatable;
import ast.Visitor;
import ast.definition.VarDefinition;
import java.util.List;

public class FunctionType extends  AbstractType {

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

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {
        if (other instanceof ErrorType) {
            return;
        }
        new ErrorType("El tipo " + this.toString() + " no es compatible con el tipo " + other.toString(), locatable);
    }

    @Override
    public Type parenthesis(List<Type> argumentTypes, Locatable locatable) {
        if (argumentTypes.size() != parameters.size()) {
            return new ErrorType("Invocación a función con número de argumentos incorrecto. Se esperaban "
                    + parameters.size() + " pero se encontraron " + argumentTypes.size(), locatable);
        }

        for (int i = 0; i < argumentTypes.size(); i++) {
            Type argType = argumentTypes.get(i);
            Type paramType = parameters.get(i).getType();

            argType.mustPromoteTo(paramType, locatable);
        }

        return returnType;
    }
}
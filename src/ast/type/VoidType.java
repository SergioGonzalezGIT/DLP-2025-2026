package ast.type;

import ast.Locatable;
import ast.Visitor;

public class VoidType extends  AbstractType {

    private static VoidType instance = new VoidType();

    private VoidType() {}

    public static VoidType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {
        if (other instanceof VoidType || other instanceof ErrorType) {
            return;
        }

        new ErrorType("El tipo " + this.toString() + " no es compatible con el tipo " + other.toString(), locatable);
    }
}
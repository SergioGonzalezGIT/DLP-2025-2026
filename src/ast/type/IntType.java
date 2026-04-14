package ast.type;

import ast.Locatable;
import ast.Visitor;

public class IntType extends  AbstractType{

    private static IntType instance = new IntType();

    private IntType() {}

    public static IntType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public void mustBeLogical(Locatable locatable) {

    }

    @Override
    public void mustBeBuiltIn(Locatable locatable) {
    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {
        if (other instanceof IntType || other instanceof ErrorType) {
            return;
        }
        new ErrorType("El tipo " + this.toString() + " no es compatible con " + other.toString(), locatable);
    }

    @Override
    public Type arithmetic(Type other, Locatable locatable) {
        if (other instanceof IntType) {
            return this;
        }
        if (other instanceof ErrorType) {
            return other;
        }
        return super.arithmetic(other, locatable);
    }

    @Override
    public Type comparison(Type other, Locatable locatable) {
        if (other instanceof IntType) {
            return this;
        }
        if (other instanceof ErrorType) return other;

        return super.comparison(other, locatable);
    }

    @Override
    public Type logical(Type other, Locatable locatable) {
        if (other instanceof IntType) {
            return this;
        }
        if (other instanceof ErrorType) return other;

        return super.logical(other, locatable);
    }

    @Override
    public Type unaryMinus(Locatable locatable) {
        return this;
    }

    @Override
    public Type unaryNot(Locatable locatable) {
        return this;
    }

    @Override
    public Type canBeCastTo(Type type, Locatable locatable) {
        if (type instanceof IntType || type instanceof CharType || type instanceof NumberType) {
            return type; // Casting válido, devolvemos el tipo destino
        }
        // Si intentan castear a un Array o un Record, que salte el error por defecto
        return super.canBeCastTo(type, locatable);
    }

    @Override
    public int numberOfBytes() {
        return 2;
    }


}

package ast.type;

import ast.Locatable;
import ast.Visitor;

public class NumberType extends  AbstractType {

    private static NumberType instance = new NumberType();

    private NumberType() {}

    public static NumberType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "RealType";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public void mustBeBuiltIn(Locatable locatable) {
    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {
        if (other instanceof NumberType || other instanceof ErrorType) {
            return;
        }
        new ErrorType("El tipo " + this.toString() + " no es compatible con el tipo " + other.toString(), locatable);
    }

    @Override
    public Type arithmetic(Type other, Locatable locatable) {


        if (other instanceof NumberType || other instanceof IntType || other instanceof CharType) {
            return NumberType.getInstance();
        }

        if (other instanceof ErrorType) return other;

        return super.arithmetic(other, locatable);
    }

    @Override
    public Type comparison(Type other, Locatable locatable) {
        if (other instanceof NumberType) {
            return IntType.getInstance();
        }

        if (other instanceof ErrorType) return other;

        return super.comparison(other, locatable);
    }

    @Override
    public Type canBeCastTo(Type type, Locatable locatable) {
        if (type instanceof IntType || type instanceof CharType || type instanceof NumberType) {
            return type;
        }
        return super.canBeCastTo(type, locatable);
    }

    @Override
    public Type unaryMinus(Locatable locatable) {
        return this;
    }

    @Override
    public int numberOfBytes() {
        return 4;
    }


    @Override
    public char suffix(){
        return 'f';
    }


}

package ast.type;

import ast.Locatable;
import ast.Visitor;

public class ArrayType extends  AbstractType {

    private int dimension;
    private Type of;

    public ArrayType(int dimension, Type of) {
        this.dimension = dimension;
        this.of = of;
    }

    public int getDimension() {
        return dimension;
    }

    public Type getOf() {
        return of;
    }

    @Override
    public String toString() {
        return "ArrayType[of:" + of.toString()  + ",size:" + dimension + "]";
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
    public Type squareBrackets(Type indexType, Locatable locatable) {
        if (indexType instanceof IntType) {
            return this.of;
        }

        if (indexType instanceof ErrorType) {
            return indexType;
        }

        return new ErrorType("El índice de acceso a un array debe ser entero, pero se encontró " + indexType.toString(), locatable);
    }

    @Override
    public int numberOfBytes() {
        return this.dimension * this.of.numberOfBytes();
    }


}
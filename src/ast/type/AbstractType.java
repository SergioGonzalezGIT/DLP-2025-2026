package ast.type;

import ast.Locatable;

import java.util.List;

public abstract class AbstractType implements Type {

    @Override
    public void mustBeLogical(Locatable locatable) {
        new ErrorType("El tipo " + this.getClass().getSimpleName() + " no es un tipo lógico válido.", locatable);
    }

    @Override
    public void mustBeBuiltIn(Locatable locatable) {
        new ErrorType("El tipo " + this.getClass().getSimpleName() + " no es un tipo primitivo (Built-in).", locatable);
    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {
        new ErrorType("El tipo " + this.getClass().getSimpleName() + " no es compatible con el tipo " + other.getClass().getSimpleName(), locatable);
    }

    @Override
    public Type arithmetic(Type other, Locatable locatable) {
        return new ErrorType("No se pueden hacer operaciones aritmeticas entre " + this.getClass().getSimpleName() + " y " + other.getClass().getSimpleName(), locatable);
    }

    @Override
    public Type comparison(Type other, Locatable locatable) {
        return new ErrorType("No se pueden hacer comparaciones entre " + this.getClass().getSimpleName() + " y " + other.getClass().getSimpleName(), locatable);
    }

    @Override
    public Type logical(Type other, Locatable locatable) {
        return new ErrorType("Operación lógica (&&, ||) no permitida entre " + this.getClass().getSimpleName() + " y " + other.getClass().getSimpleName(), locatable);
    }

    @Override
    public Type unaryMinus(Locatable locatable) {
        return new ErrorType("No se puede aplicar el menos unario (-) al tipo " + this.getClass().getSimpleName(), locatable);
    }

    @Override
    public Type unaryNot(Locatable locatable) {
        return new ErrorType("No se puede aplicar la negación lógica (!) al tipo " + this.getClass().getSimpleName(), locatable);
    }

    @Override
    public Type squareBrackets(Type indexType, Locatable locatable) {
        return new ErrorType("El tipo " + this.getClass().getSimpleName() + " no es un array y no se puede indexar con [].", locatable);
    }

    @Override
    public Type dot(String field, Locatable locatable) {
        return new ErrorType("El tipo " + this.getClass().getSimpleName() + " no tiene campos a los que acceder con el operador punto.", locatable);
    }

    @Override
    public Type parenthesis(List<Type> args, Locatable locatable) {
        return new ErrorType("El tipo " + this.getClass().getSimpleName() + " no es una función y no puede ser invocado con ().", locatable);
    }

    @Override
    public Type canBeCastTo(Type type, Locatable locatable) {
        if (type instanceof ErrorType) {
            return type; // Si ya hay un error, lo propagamos
        }
        return new ErrorType("No se puede convertir explícitamente el tipo " +
                this.toString() + " al tipo " + type.toString(), locatable);
    }


}
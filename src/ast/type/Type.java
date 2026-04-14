package ast.type;

import ast.ASTNode;
import ast.Locatable;

import java.util.List;

public interface Type extends ASTNode {

    void mustBeLogical(Locatable locatable);
    void mustBeBuiltIn(Locatable locatable);
    Type arithmetic(Type other, Locatable locatable);
    Type unaryNot(Locatable locatable);


    Type logical(Type other, Locatable locatable);
    Type unaryMinus(Locatable locatable);

    void mustPromoteTo(Type other, Locatable locatable);
    Type canBeCastTo(Type type, Locatable locatable);

    Type comparison(Type other, Locatable locatable);
    Type squareBrackets(Type indexType, Locatable locatable);
    Type dot(String field, Locatable locatable);
    Type parenthesis(List<Type> args, Locatable locatable);




    int numberOfBytes();

}


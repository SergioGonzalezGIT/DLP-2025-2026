package ast;

import ast.definition.VarDefinition;
import ast.expression.Variable;
import ast.type.IntType;

public class AddressVisitor extends AbstractCGVisitor<Void, Void> {

    public AddressVisitor(CodeGenerator cg) {
        super(cg);
    }

    @Override
    public Void visit(Variable variable, Void param) {

        VarDefinition varDef = (VarDefinition) variable.getDefinition();

        if (varDef.getScope() == 0) {
            cg.pusha(varDef.getOffset());
        } else {
            cg.pushBp();
            cg.push(varDef.getOffset());
            cg.add(IntType.getInstance());
        }

        return null;
    }
}

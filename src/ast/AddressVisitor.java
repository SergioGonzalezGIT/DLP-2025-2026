package ast;

import ast.definition.VarDefinition;
import ast.expression.ArrayAccess;
import ast.expression.Variable;
import ast.expression.FieldAccess;

import ast.type.IntType;
import ast.type.RecordField;
import ast.type.RecordType;

public class AddressVisitor extends AbstractCGVisitor<Void, Void> {


    private ValueVisitor valueVisitor;

    public AddressVisitor(CodeGenerator cg) {
        super(cg);
    }

    public void setValueVisitor(ValueVisitor valueVisitor) {
        this.valueVisitor = valueVisitor;
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

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {
        fieldAccess.getExpression().accept(this, null);
        RecordType type = (RecordType) fieldAccess.getExpression().getType();
        RecordField field = type.getField(fieldAccess.getFieldName());
        cg.push(field.getOffset());
        cg.add(IntType.getInstance());

        return null;
    }

    @Override
    public Void visit(ArrayAccess array, Void param) {
        array.getLeft().accept(this, null);
        //Seria mi .getRight de lo explicado en clase
        array.getIndex().accept(valueVisitor, null);
        cg.push(array.getType().numberOfBytes());
        cg.mul(IntType.getInstance());
        cg.add(IntType.getInstance());

        return null;
    }

}

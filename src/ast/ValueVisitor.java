package ast;

import ast.expression.*;
import ast.type.CharType;
import ast.type.IntType;
import ast.type.NumberType;
import ast.type.Type;

public class ValueVisitor extends AbstractCGVisitor<Void, Void>{

    private AddressVisitor addressVisitor;

    public ValueVisitor(CodeGenerator cg) {
        super(cg);
        this.addressVisitor = new AddressVisitor(cg);
        this.addressVisitor.setValueVisitor(this);
    }

    public void setAddressVisitor(AddressVisitor addressVisitor) {
        this.addressVisitor=addressVisitor;
    }

    @Override
    public Void visit(IntLiteral node, Void param) {
        cg.push(node.getValue());
        return null;
    }

    @Override
    public Void visit(NumberLiteral node, Void param) {
        cg.push(node.getValue());
        return null;
    }

    @Override
    public Void visit(CharLiteral node, Void param) {
        cg.push(node.getValue());
        return null;
    }


    @Override
    public Void visit(Variable node, Void param) {
        node.accept(addressVisitor, null);

        cg.load(node.getType());

        return null;
    }

    @Override
    public Void visit(FieldAccess node, Void param) {
        node.accept(addressVisitor, null);

        cg.load(node.getType());

        return null;
    }

    @Override
    public Void visit(ArrayAccess node, Void param) {
        node.accept(addressVisitor, null);
        cg.load(node.getType());
        return null;
    }

    @Override
    public Void visit(Arithmetic a, Void param) {
        a.getLeft().accept(this, null);
        cg.convertTo(a.getLeft().getType(), a.getType());

        a.getRight().accept(this, null);
        cg.convertTo(a.getRight().getType(), a.getType());

        if (a.getOperator().equals("+")) cg.add(a.getType());
        else if (a.getOperator().equals("-")) cg.sub(a.getType());
        else if (a.getOperator().equals("*")) cg.mul(a.getType());
        else if (a.getOperator().equals("/")) cg.div(a.getType());
        else if (a.getOperator().equals("%")) cg.mod(a.getType());

        return null;
    }

    @Override
    public Void visit(Comparison c, Void param) {
        Type leftT = c.getLeft().getType();
        Type rightT = c.getRight().getType();

        Type commonType = leftT;

        if (rightT instanceof NumberType) commonType = rightT;
        else if (rightT instanceof IntType && leftT instanceof CharType) commonType = rightT;

        c.getLeft().accept(this, null);
        cg.convertTo(leftT, commonType);

        c.getRight().accept(this, null);
        cg.convertTo(rightT, commonType);

        if (c.getOperator().equals(">")) cg.gt(commonType);
        else if (c.getOperator().equals("<")) cg.lt(commonType);
        else if (c.getOperator().equals(">=")) cg.ge(commonType);
        else if (c.getOperator().equals("<=")) cg.le(commonType);
        else if (c.getOperator().equals("==")) cg.eq(commonType);
        else if (c.getOperator().equals("!=")) cg.ne(commonType);

        return null;
    }

    @Override
    public Void visit(Logical l, Void param) {
        l.getLeft().accept(this, null);
        l.getRight().accept(this, null);
        if (l.getOperator().equals("&&")) cg.and();
        else if (l.getOperator().equals("||")) cg.or();
        return null;
    }

    @Override
    public Void visit(Cast c, Void param) {
        c.getExpression().accept(this, null);
        cg.convertTo(c.getExpression().getType(), c.getCastType());
        return null;
    }

    @Override
    public Void visit(FunctionInvocation inv, Void param) {
        for (Expression arg : inv.getArguments()) {
            arg.accept(this, null);
        }

        cg.call(inv.getVariable().getName());

        return null;
    }

    @Override
    public Void visit(UnaryNot un, Void param) {
        un.getExpression().accept(this, null);

        cg.not();

        return null;
    }

    @Override
    public Void visit(UnaryMinus um, Void param) {
        cg.push(0);
        cg.convertTo(IntType.getInstance(), um.getType());

        um.getExpression().accept(this, null);

        cg.sub(um.getType());

        return null;
    }


}

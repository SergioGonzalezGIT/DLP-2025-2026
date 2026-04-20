package ast;

import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.Expression;
import ast.statement.*;
import ast.type.FunctionType;
import ast.type.VoidType;

public class ExecutionVisitor extends AbstractCGVisitor<Void, Void> {

    private AddressVisitor addressVisitor;
    private ValueVisitor valueVisitor;

    private FunctionDefinition currentFunction;

    public ExecutionVisitor(CodeGenerator cg) {
        super(cg);
        this.addressVisitor = new AddressVisitor(cg);
        this.valueVisitor = new ValueVisitor(cg);
    }

    @Override
    public Void visit(Assignment a, Void param) {
        cg.line(a.getLine());
        cg.comment("Assignment");

        a.getLeft().accept(addressVisitor, null);
        a.getRight().accept(valueVisitor, null);
        cg.convertTo(a.getRight().getType(), a.getLeft().getType());
        cg.store(a.getLeft().getType());
        return null;
    }

    @Override
    public Void visit(Input input, Void param) {
        for (Expression expr : input.getExpressions()) {
            cg.line(input.getLine());
            cg.comment("Read");

            expr.accept(addressVisitor, null);
            cg.in(expr.getType());
            cg.store(expr.getType());
        }
        return null;
    }

    @Override
    public Void visit(Log print, Void param) {
        for (Expression expr : print.getExpressions()) {
            cg.line(print.getLine());
            cg.comment("Write");

            expr.accept(valueVisitor, null);
            cg.out(expr.getType());
        }
        return null;
    }


    @Override
    public Void visit(While w, Void param) {
        String labelCondition = cg.getLabel();
        String labelEnd = cg.getLabel();

        cg.writeLabel(labelCondition);

        w.getCondition().accept(valueVisitor, null);

        cg.jz(labelEnd);

        for (Statement s : w.getBody()) {
            s.accept(this, null);
        }

        cg.jmp(labelCondition);

        cg.writeLabel(labelEnd);

        return null;
    }

    @Override
    public Void visit(Program p, Void param) {

        for (Definition def : p.getDefinitions()) {
            if (def instanceof VarDefinition) {
                VarDefinition v = (VarDefinition) def;
                cg.comment(v.getType().toString() + " " + v.getName() + " (offset " + v.getOffset() + ")");
            }
        }

        cg.mainInvocation();
        cg.call("main");
        cg.halt();

        for (Definition def : p.getDefinitions()) {
            if (def instanceof FunctionDefinition) {
                def.accept(this, null);
            }
        }

        return null;
    }
    @Override
    public Void visit(VarDefinition v, Void param) {
        return null;
    }

    @Override
    public Void visit(IfElse ifElse, Void param) {
        String labelElse = cg.getLabel();
        String labelEnd = cg.getLabel();

        ifElse.getCondition().accept(valueVisitor, null);

        cg.jz(labelElse);

        for (Statement s : ifElse.getIfBody()) {
            s.accept(this, null);
        }
        cg.jmp(labelEnd);

        cg.writeLabel(labelElse);
        for (Statement s : ifElse.getElseBody()) {
            s.accept(this, null);
        }

        cg.writeLabel(labelEnd);

        return null;
    }

    @Override
    public Void visit(FunctionDefinition def, Void param) {
        this.currentFunction = def;

        cg.line(def.getLine());
        cg.writeLabel(def.getName());

        cg.comment("Parameters");

        cg.comment("Local variables");
        int localBytes = 0;
        for (Statement s : def.getStatements()) {
            if (s instanceof VarDefinition) {
                VarDefinition v = (VarDefinition) s;
                cg.comment(v.getType().toString() + " " + v.getName() + " (offset " + v.getOffset() + ")");
                localBytes += v.getType().numberOfBytes();
            }
        }

        cg.enter(localBytes);

        for (Statement s : def.getStatements()) {
            s.accept(this, null);
        }

        FunctionType type = (FunctionType) def.getType();
        int returnBytes = 0;
        if (!(type.getReturnType() instanceof VoidType)) {
            returnBytes = type.getReturnType().numberOfBytes();
        }
        int paramBytes = 0;
        for (VarDefinition p : type.getParameters()) {
            paramBytes += p.getType().numberOfBytes();
        }
        if (returnBytes == 0) {
            cg.ret(returnBytes, localBytes, paramBytes);
        }
        return null;
    }

    @Override
    public Void visit(Return ret, Void param) {
        if (ret.getExpression() != null) {
            ret.getExpression().accept(valueVisitor, null);
        }

        FunctionType type = (FunctionType) this.currentFunction.getType();

        int returnBytes = 0;
        if (!(type.getReturnType() instanceof VoidType)) {
            returnBytes = type.getReturnType().numberOfBytes();
        }

        int localBytes = 0;
        for (Statement s : this.currentFunction.getStatements()) {
            if (s instanceof VarDefinition) {
                localBytes += ((VarDefinition)s).getType().numberOfBytes();
            }
        }

        // Sumar bytes de parámetros
        int paramBytes = 0;
        for (VarDefinition p : type.getParameters()) {
            paramBytes += p.getType().numberOfBytes();
        }

        // 3. Limpiamos la pila y salimos
        cg.ret(returnBytes, localBytes, paramBytes);

        return null;
    }
}

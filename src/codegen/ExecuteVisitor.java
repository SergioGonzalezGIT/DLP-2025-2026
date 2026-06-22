package codegen;

import ast.Program;
import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.Expression;
import ast.expression.FunctionInvocation;
import ast.statement.*;
import ast.type.FunctionType;
import ast.type.IntType;
import ast.type.Type;
import ast.type.VoidType;


//podria cambiarase a fundDef, Void, y hacerlo segun la plantilla que tengo hecha de clase
//En el if y en el while hay que propagar el parametro
//ExecuteCGVisitor no se puede definir para expresiones
//(sentencias, definiciones y programa
public class ExecuteVisitor extends AbstractCGVisitor<FunctionDefinition, Void> {

    private AddressVisitor addressVisitor;
    private ValueVisitor valueVisitor;


    public ExecuteVisitor(CodeGenerator cg) {
        super(cg);
        this.addressVisitor = new AddressVisitor(cg);
        this.valueVisitor = new ValueVisitor(cg);

        this.addressVisitor.setValueVisitor(this.valueVisitor);
        this.valueVisitor.setAddressVisitor(this.addressVisitor);
    }

    @Override
    public Void visit(Assignment a, FunctionDefinition param) {
        cg.line(a.getLine());
        cg.comment("Assignment");

        a.getLeft().accept(addressVisitor, null);
        a.getRight().accept(valueVisitor, null);
        cg.convertTo(a.getRight().getType(), a.getLeft().getType());
        cg.store(a.getLeft().getType());
        return null;
    }

    @Override
    public Void visit(Input input, FunctionDefinition param) {
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
    public Void visit(Log print, FunctionDefinition param) {
        for (Expression expr : print.getExpressions()) {
            cg.line(print.getLine());
            cg.comment("Write");

            expr.accept(valueVisitor, null);
            cg.out(expr.getType());
        }
        return null;
    }




    //Hay que hacer convertTo para asegurar, cg.convertTo ( expr.type, IntType), antes del jz(end)
    @Override
    public Void visit(While w, FunctionDefinition param) {
        cg.line(w.getLine());
        cg.comment("While");

        cg.line(w.getLine());

        String condition = cg.getLabel();
        String end = cg.getLabel();

        cg.writeLabelPegado(condition);

        w.getCondition().accept(valueVisitor, null);
        cg.convertTo(w.getCondition().getType(), IntType.getInstance());
        cg.jz(end);

        cg.comment("While body");

        for (Statement s : w.getBody()) {
            s.accept(this, param);
        }

        cg.jmp(condition);

        cg.writeLabelPegado(end);

        return null;
    }



    @Override
    public Void visit(Return ret, FunctionDefinition param) {
        cg.line(ret.getLine());
        cg.comment("Return");

        FunctionType type = (FunctionType) param.getType();
        Type expectedReturn = type.getReturnType();

        if (ret.getExpression() != null) {
            ret.getExpression().accept(valueVisitor, null);
            cg.convertTo(ret.getExpression().getType(), expectedReturn);
        }

        int returnBytes = 0;
        if (!(expectedReturn instanceof VoidType)) {
            returnBytes = expectedReturn.numberOfBytes();
        }

        int localBytes = 0;
        for (Statement s : param.getStatements()) {
            if (s instanceof VarDefinition) {
                localBytes += ((VarDefinition)s).getType().numberOfBytes();
            }
        }

        int paramBytes = 0;
        for (VarDefinition p : type.getParameters()) {
            paramBytes += p.getType().numberOfBytes();
        }

        cg.ret(returnBytes, localBytes, paramBytes);

        return null;
    }

    @Override
    public Void visit(Program p, FunctionDefinition param) {

        for (Definition def : p.getDefinitions()) {
            if (def instanceof VarDefinition) {
                VarDefinition v = (VarDefinition) def;
                cg.comment(v.getType().toString() + " " + v.getName() + " (offset " + v.getOffset() + ")");
            }
        }

        cg.mainInvocation();
        cg.callMain();
        cg.halt();

        for (Definition def : p.getDefinitions()) {
            if (def instanceof FunctionDefinition) {
                def.accept(this, null);
            }
        }

        return null;
    }
    @Override
    public Void visit(VarDefinition v, FunctionDefinition param) {
        return null;
    }

    @Override
    public Void visit(IfElse ifElse, FunctionDefinition param) {
        String labelElse = cg.getLabel();
        String labelEnd = cg.getLabel();

        cg.line(ifElse.getLine());
        cg.comment("If");
        cg.line(ifElse.getLine());

        ifElse.getCondition().accept(valueVisitor, null);
        cg.convertTo(ifElse.getCondition().getType(), IntType.getInstance());
        cg.jz(labelElse);

        cg.comment("if body");
        for (Statement s : ifElse.getIfBody()) {
            s.accept(this, param);
        }

        cg.jmp(labelEnd);

        cg.writeLabelPegado(labelElse);

        if (ifElse.getElseBody() != null && !ifElse.getElseBody().isEmpty()) {


            cg.comment("else body");
            for (Statement s : ifElse.getElseBody()) {
                s.accept(this, param);
            }
        }

        cg.writeLabelPegado(labelEnd);

        return null;
    }



    @Override
    public Void visit(FunctionInvocation inv, FunctionDefinition param) {
        cg.line(inv.getLine());
        inv.accept(valueVisitor, null);

        if (!(inv.getType() instanceof VoidType)) {
            cg.pop(inv.getType());
        }

        return null;
    }

    @Override
    public Void visit(Invocation inv, FunctionDefinition param) {
        cg.line(inv.getLine());

        // 1. Traducir argumentos (igual que en ValueVisitor)
        FunctionType funcType = (FunctionType) inv.getVariable().getType();
        for (int i = 0; i < inv.getArguments().size(); i++) {
            Expression arg = inv.getArguments().get(i);
            arg.accept(valueVisitor, null);
            Type paramType = funcType.getParameters().get(i).getType();
            cg.convertTo(arg.getType(), paramType);
        }

        // 2. Llamar a la función
        cg.call(inv.getVariable().getName());

        if (!(funcType.getReturnType() instanceof VoidType)) {
            cg.pop(funcType.getReturnType());
        }

        return null;
    }

    @Override
    public Void visit(FunctionDefinition def, FunctionDefinition param) {

        cg.line(def.getLine());
        cg.writeLabel(def.getName());

        cg.comment("Parameters");

        // Extraemos los parámetros desde el objeto FunctionType almacenado en la definición
        if (def.getType() instanceof FunctionType) {
            FunctionType fType = (FunctionType) def.getType();
            for (VarDefinition p : fType.getParameters()) {
                cg.comment( p.getType().toString() + " " + p.getName() + " (offset " + p.getOffset() + ")");
            }
        }

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
            s.accept(this, def);
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
    public Void visit(Incremento inc, FunctionDefinition param) {
        cg.line(inc.getLine());
        cg.comment("Incremento");

        // 1. Apilamos la DIRECCIÓN donde vamos a guardar el resultado final
        inc.getExpression().accept(addressVisitor, null);

        // 2. Apilamos el VALOR ACTUAL de la variable
        inc.getExpression().accept(valueVisitor, null);

        // 3. Apilamos un 1
        cg.push(1);

        // 4. Sumamos (saca el 1 y el valor actual, los suma, y mete el resultado)
        cg.add(ast.type.IntType.getInstance());

        // 5. Guardamos (saca el resultado de la suma y la dirección inicial, y machaca la memoria)
        cg.store(inc.getExpression().getType());

        return null;
    }

}

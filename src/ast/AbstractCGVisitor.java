package ast;

import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

public abstract class AbstractCGVisitor<TP, TR> implements Visitor<TP, TR> {

    protected CodeGenerator cg;

    public AbstractCGVisitor(CodeGenerator cg) {
        this.cg = cg;
    }

    protected TR error(ASTNode node) {
        throw new UnsupportedOperationException(
                "Operación no soportada. Has llamado a un visitor incorrecto para el nodo: "
                        + node.getClass().getSimpleName()
                        + " en la clase " + this.getClass().getSimpleName()
        );
    }


    @Override
    public TR visit(IntLiteral node, TP param) { return error(node); }
    @Override
    public TR visit(CharLiteral node, TP param) { return error(node); }
    @Override
    public TR visit(NumberLiteral node, TP param) { return error(node); }
    @Override
    public TR visit(Variable node, TP param) { return error(node); }
    @Override
    public TR visit(Arithmetic node, TP param) { return error(node); }
    @Override
    public TR visit(Comparison node, TP param) { return error(node); }
    @Override
    public TR visit(Logical node, TP param) { return error(node); }
    @Override
    public TR visit(UnaryMinus node, TP param) { return error(node); }
    @Override
    public TR visit(UnaryNot node, TP param) { return error(node); }
    @Override
    public TR visit(ArrayAccess node, TP param) { return error(node); }
    @Override
    public TR visit(FieldAccess node, TP param) { return error(node); }
    @Override
    public TR visit(FunctionInvocation node, TP param) { return error(node); }
    @Override
    public TR visit(Cast node, TP param) { return error(node); }

    @Override
    public TR visit(Assignment node, TP param) { return error(node); }
    @Override
    public TR visit(IfElse node, TP param) { return error(node); }
    @Override
    public TR visit(While node, TP param) { return error(node); }
    @Override
    public TR visit(Input node, TP param) { return error(node); }
    @Override
    public TR visit(Log node, TP param) { return error(node); } // O Print
    @Override
    public TR visit(Return node, TP param) { return error(node); }

    @Override
    public TR visit(VarDefinition node, TP param) { return error(node); }
    @Override
    public TR visit(FunctionDefinition node, TP param) { return error(node); }

    @Override
    public TR visit(Program node, TP param) { return error(node); }

    @Override
    public TR visit(Invocation node, TP param) { return error(node); }

    @Override
    public TR visit(ArrayType node, TP param) { return error(node); }
    @Override
    public TR visit(CharType node, TP param) { return error(node); }
    @Override
    public TR visit(IntType node, TP param) { return error(node); }
    @Override
    public TR visit(NumberType node, TP param) { return error(node); }
    @Override
    public TR visit(VoidType node, TP param) { return error(node); }
    @Override
    public TR visit(ErrorType node, TP param) { return error(node); }
    @Override
    public TR visit(FunctionType node, TP param) { return error(node); }
    @Override
    public TR visit(RecordType node, TP param) { return error(node); }
    @Override
    public TR visit(RecordField node, TP param) { return error(node); }
}

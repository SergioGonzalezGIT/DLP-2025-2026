package codegen;

import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.type.FunctionType;
import ast.type.RecordField;
import ast.type.RecordType;
import visitor.AbstractVisitor;


//poner en un paquete que se llame ¿codeGen?
//Hijos de FunctionType serán parametros
//HIjos de FunctionDefinitiron seran locales
//A través de un parametro del visitor, paso esta informacino con un boolean
//porque hay 2 opciones
// IP, Instruction Pointer -> dirección de la instrucción en ejecución (segmento de código)
public class OffsetVisitor extends AbstractVisitor<Boolean, Void> {
    private int globalNumberBytes = 0;
    private int localOffset=0;
    private int paramOffset = 4;

    @Override
    public Void visit(FunctionType funcType, Boolean isParam) {
        for (int i = funcType.getParameters().size() - 1; i >= 0; i--) {
            VarDefinition p = funcType.getParameters().get(i);
            p.accept(this, true);
        }
        funcType.getReturnType().accept(this, false);

        return null;
    }

    @Override
    public Void visit(VarDefinition varDef, Boolean isParam) {
        varDef.getType().accept(this, isParam);

        if (varDef.getScope() == 0) {
            // Globales
            varDef.setOffset(globalNumberBytes);
            globalNumberBytes += varDef.getType().numberOfBytes();

        } else if (Boolean.FALSE.equals(isParam)) {
            // Locales (Crecen en negativo)
            localOffset -= varDef.getType().numberOfBytes();
            varDef.setOffset(localOffset);

        } else if (Boolean.TRUE.equals(isParam)) {
            // Parámetros (Crecen en positivo)
            varDef.setOffset(paramOffset);
            paramOffset += varDef.getType().numberOfBytes();
        }

        return null;
    }

    @Override
    public Void visit(RecordType record, Boolean isParam) {
        int fieldOffset = 0;
        for (RecordField field : record.getFields()) {
            field.setOffset(fieldOffset);
            fieldOffset += field.getType().numberOfBytes();
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition funcDef, Boolean isParam) {
        localOffset = 0;
        paramOffset = 4;

        funcDef.getType().accept(this, true);

        for (ast.statement.Statement s : funcDef.getStatements()) {
            s.accept(this, false);
        }

        return null;
    }
}


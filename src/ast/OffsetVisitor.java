package ast;

import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.FunctionInvocation;
import ast.type.FunctionType;
import ast.type.RecordField;
import ast.type.RecordType;


//poner en un paquete que se llame ¿codeGen?
//Hijos de FunctionType serán parametros
//HIjos de FunctionDefinitiron seran locales
//A través de un parametro del visitor, paso esta informacino con un boolean
//porque hay 2 opciones
public class OffsetVisitor extends AbstractVisitor<Boolean, Void> {
    private int globalNumberBytes = 0;
    private int localOffset=0;
    private int paramOffset = 4;

    @Override
    public Void visit(FunctionType funcType, Boolean isParam) {
        // 3. DELEGAMOS EN VarDefinition. Ya no calculamos nada aquí.
        for (VarDefinition p : funcType.getParameters()) {
            p.accept(this, isParam); // Pasará el 'true' hacia abajo
        }
        funcType.getReturnType().accept(this, false);

        return null;
    }

    @Override
    public Void visit(VarDefinition varDef, Boolean isParam) {
        // Visitamos el tipo por si es un RecordType
        varDef.getType().accept(this, isParam);

        // 4. EL IF DE TRES VÍAS (Aquí se centraliza todo)
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
        // 2. SE RESETEAN EN FUNCDEFINITION (Palabras textuales de Oscar)
        localOffset = 0;
        paramOffset = 4; // Dependiendo de tu máquina, suele ser 4 por el BP y dir. de retorno

        // Visitamos el tipo de la función (donde están los parámetros). Le pasamos true.
        funcDef.getType().accept(this, true);

        // Visitamos las sentencias (donde están las locales). Les pasamos false.
        for (ast.statement.Statement s : funcDef.getStatements()) {
            s.accept(this, false);
        }

        return null;
    }
}


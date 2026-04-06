package ast.type;

import ast.Locatable;
import ast.Visitor;
import ast.definition.VarDefinition;

import java.util.ArrayList;
import java.util.List;

public class RecordType extends  AbstractType {

    private List<RecordField> fields;

    public RecordType(List<RecordField> fields) {
        this.fields = fields;
    }

    public void addFields(List<VarDefinition> varDefs) {
        for (VarDefinition vd : varDefs) {
            boolean isDuplicate = false;

            for (RecordField existing : this.fields) {
                if (existing.getName().equals(vd.getName())) {
                    new ErrorType("Campo duplicado en struct/record: " + vd.getName(), vd);
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                RecordField field = new RecordField(vd.getLine(), vd.getColumn(), vd.getName(), vd.getType());
                this.fields.add(field);
            }
        }
    }

    public List<RecordField> getFields() {
        return fields;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("record(");
        for (int i = 0; i < fields.size(); i++) {
            sb.append(fields.get(i).toString());
            if (i < fields.size() - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {
        if (other instanceof ErrorType) return;

        if (other instanceof RecordType) {
            RecordType otherRecord = (RecordType) other;

            if (this.fields.size() != otherRecord.getFields().size()) {
                new ErrorType("Los records tienen distinto número de campos y no son compatibles.", locatable);
                return;
            }

            for (int i = 0; i < this.fields.size(); i++) {
                Type myFieldType = this.fields.get(i).getType();
                Type otherFieldType = otherRecord.getFields().get(i).getType();

                myFieldType.mustPromoteTo(otherFieldType, locatable);
            }
            return;
        }

        new ErrorType("El tipo " + this.toString() + " no es compatible con el tipo " + other.toString(), locatable);
    }

    @Override
    public Type dot(String fieldName, Locatable locatable) {
        for (RecordField field : this.fields) {
            if (field.getName().equals(fieldName)) {
                return field.getType();
            }
        }

        return new ErrorType("El campo '" + fieldName + "' no está definido en el record.", locatable);
    }
}
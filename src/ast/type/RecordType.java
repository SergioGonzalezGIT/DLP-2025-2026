package ast.type;

import ast.definition.VarDefinition;

import java.util.ArrayList;
import java.util.List;

public class RecordType implements Type {

    private List<RecordField> fields;
    private List<Type> types;

    public RecordType(List<RecordField> fields, List<Type> types) {
        this.fields = fields;
        this.types = types;
    }

    public void addFields(List<VarDefinition> varDefs) {
        for (VarDefinition vd : varDefs) {
            RecordField field = new RecordField(vd.getName());

            this.fields.add(field);
            this.types.add(vd.getType());
        }
    }

    public List<RecordField> getFields() {
        return fields;
    }

    public List<Type> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("record(");
        for (int i = 0; i < fields.size(); i++) {
            sb.append(fields.get(i).getName()).append(":").append(types.get(i));
            if (i < fields.size() - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}
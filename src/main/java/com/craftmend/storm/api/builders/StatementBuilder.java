package com.craftmend.storm.api.builders;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.parser.objects.ModelField;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatementBuilder {

    private StormModel model;

    public String buildSqlTableCreateStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + model.parsed().getTableName() + " (");

        for (int i = 0; i < model.parsed().getParsedFields().length; i++) {
            ModelField mf = model.parsed().getParsedFields()[i];
            boolean isLast = model.parsed().getParsedFields().length == i + 1;
            sb.append(" " + mf.buildSqlType() + (isLast ? "" : ","));
        }

        // parse constraints
        for (ModelField parsedField : model.parsed().getParsedFields()) {
            if (parsedField.getKeyType() == KeyType.PRIMARY) {
                sb.append(",");
                sb.append("PRIMARY KEY (" + parsedField.getColumnName() +")");
            }
        }

        sb.append(")");
        return sb.toString();
    }

}

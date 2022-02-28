package com.craftmend.storm.dialect.mariadb;

import com.craftmend.storm.api.enums.KeyType;
import com.craftmend.storm.dialect.Dialect;
import com.craftmend.storm.parser.objects.ModelField;

public class MariaDialect implements Dialect {

    @Override
    public String compileColumn(ModelField<?> modelField) {
        String sqlTypeDeclaration = modelField.getAdapter().getSqlBaseType();
        sqlTypeDeclaration = sqlTypeDeclaration.replace("%max", modelField.getMax() + "");
        return modelField.getColumnName() + " " + sqlTypeDeclaration +

                (modelField.getKeyType() == KeyType.PRIMARY ? " PRIMARY KEY" : "") +

                (modelField.isAutoIncrement() ? " AUTO INCREMENT" : "") +

                (modelField.getDefaultValue() != null ? " DEFAULT(" +
                        (modelField.getAdapter().escapeAsString() ? "'" + modelField.getDefaultValue() + "'" : modelField.getDefaultValue())
                        + ")" : "") +

                (modelField.isNotNull() ? " NOT NULL" : "") +

                (modelField.isUnique() ? " UNIQUE" : "");
    }

}
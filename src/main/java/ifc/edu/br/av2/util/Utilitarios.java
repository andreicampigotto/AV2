package ifc.edu.br.av2.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilitarios {
    
    public static List<Map<String, Object>> resultSetToList(ResultSet resultSet) throws SQLException {
        ArrayList<Map<String, Object>> resultado = new ArrayList<>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        while (resultSet.next()) {
            HashMap<String, Object> linha = new HashMap<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String nmRetorno = rsmd.getColumnName(i);
                Object valor = resultSet.getObject(i);
                linha.put(nmRetorno, valor);
            }
            resultado.add(linha);
        }
        resultSet.close();
        return resultado;
    }
    
}
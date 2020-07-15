package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import service.RequisicaoAPI;

import static io.restassured.RestAssured.get;


public class JSON2XLS {
    private static String url ="http://5e212b4f6867a0001416f152.mockapi.io/api/v1/";;

    public static void transformar(String array, String caminho) throws IOException {
        JSON2XLS.transformar(new JSONArray(array), caminho);
    }

    public static void transformar(JSONArray array, String caminho) throws IOException {
        Set<String> campos = new HashSet<>();

        for (Object objeto : array) {
            JSONObject linha = (JSONObject) objeto;

            campos.addAll(linha.keySet());
        }

        JSON2XLS.transformar(new LinkedList<>(campos), array, caminho);
    }

    public static void transformar(List<String> campos, String array, String caminho) throws IOException {
        JSON2XLS.transformar(campos, new JSONArray(array), caminho);
    }

    public static void transformar(List<String> campos, JSONArray array, String caminho) throws IOException {
        SXSSFWorkbook xlsx = new SXSSFWorkbook(50);
        HSSFWorkbook xls = new HSSFWorkbook();
        Sheet aba = (Sheet) xls.createSheet("dataprovider");
        FileOutputStream saida;
        File arquivo;

        JSON2XLS.criarCabecalho(campos, aba);

        for (int indice = 1; indice <= array.length(); indice++) {
            List<Object> celulas = new ArrayList();
            JSONObject objeto = array.getJSONObject(indice - 1);

            campos.forEach((campo) -> {
                if (objeto.has(campo)) {
                    celulas.add(objeto.get(campo));
                } else {
                    celulas.add("");
                }
            });

            JSON2XLS.criarLinha(celulas, aba, indice);
        }

        try {
            arquivo = new File(caminho);

            if (!arquivo.exists()) {
                arquivo.getParentFile().mkdirs();
                arquivo.createNewFile();
            }

            saida = new FileOutputStream(caminho);
            xls.write(saida);
            saida.close();
        } catch (IOException excecao) {
            throw new RuntimeException(excecao);
        }

        xls.close();
    }

    private static void criarCabecalho(List<String> rotulos, Sheet aba) {
        CellStyle estilo;
        Font fonte;
        Row row;
        int indice;

        row = aba.createRow(0);

        for (indice = 0; indice < rotulos.size(); indice++) {
            row.createCell(indice).setCellValue(rotulos.get(indice));
        }

        estilo = aba.getWorkbook().createCellStyle();
        fonte = aba.getWorkbook().createFont();
        fonte.setBold(true);
        estilo.setFont(fonte);

        for (indice = 0; indice < row.getLastCellNum(); indice++) {
            row.getCell(indice).setCellStyle(estilo);
        }
    }

    private static void criarLinha(List<Object> celulas, Sheet aba, int indiceLinha) {
        Row linha = aba.createRow(indiceLinha);

        for (int indice = 0; indice < celulas.size(); indice++) {
            Object celula = celulas.get(indice);

            linha.createCell(indice).setCellValue(String.valueOf(celula));
        }
    }
    public static String getBody  (String endpoint) {

        String responseString =get(url + endpoint ).then().
                assertThat()
                .statusCode(200)
                .extract()
                .asString();
        Assert.assertNotNull(responseString);
        return responseString;
    }
    public static void createXlsFile() throws IOException {
        String json =getBody("users");
        List<String> campos = new LinkedList<>();
        campos.add("id");
        campos.add("name");
        JSON2XLS.transformar(campos, new JSONArray(json), "Arquivos/dataprovider.xls");
    }
}
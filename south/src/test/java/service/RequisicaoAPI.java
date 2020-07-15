package service;


import automation.utils.ArquivoUtils;
import com.cucumber.listener.Reporter;
import dto.Usuarios;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.json.simple.JSONObject;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import Utils.JSON2XLS;


public class RequisicaoAPI {


	private String url = "http://5e212b4f6867a0001416f152.mockapi.io/api/v1/";
	public static Map<String, String> map = new HashMap<String, String>();
	public void valida_endpoint(String endpoint){
				when().
				get(url + endpoint).
				then().
				statusCode(200);
		System.out.println(url + endpoint);

	}
	public String getNameUserTest(String endpoint, String name,String id){

				String responseString =get(url + endpoint +"/"+id).
				then().
				body("name", equalTo(name))
				.extract()
				.path("name");
				System.out.println(url + endpoint);
				if(responseString.equals(name)){
					return "Nome encontrado na API com sucesso:"+responseString;
				}else {
					return "Nome não encontrado na API:" + responseString;
				}
	}


	public String getUserTest(String id ,String endpoint) {

		String responseString =get(url + endpoint +"/"+id).
				then().
				body("id", equalTo(id))
				.extract()
				.path("name");
		System.out.println(url + endpoint);
		if(responseString.equals(id)){
			return "Nome encontrado na API com sucesso:"+responseString;
		}else {
			return "Nome não encontrado na API:" + responseString;
		}


	}


	public String getField  (String iduser ,String endpoint,String field) {

		String responseString =get(url + endpoint + "/" + iduser).then().
				extract().
				path("name");
		Assert.assertNotNull(responseString);
		return responseString;
	}


	public void  postUserTest(String endpoint , String nome){


		RestAssured.baseURI =url ;
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		request.body(requestParams.toJSONString());
		Response response = request.post(endpoint);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		System.out.println("Response body: " + response.body().asString());


	}



	public void putUserTest(String endpoint,String id){
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", id);
		requestParams.put("name", "QA/DEV");

		given().
				body(requestParams.toJSONString()).
				when().
				put(url + endpoint +"/5" ).
				then().
				statusCode(200);
				//body(containsString("updatedAt"));

	}


	public void deleteUserTest(String endpoint ,String id){
		String idUser= getField(id,endpoint,"id");
		if(idUser.equals(id)){

			when().
					delete(url + endpoint + "/" + idUser).
					then().
					statusCode(200);
			System.out.println(url + endpoint + "/" + idUser);
		}else{
			Assert.fail("Id não encontrado");
		}

	}

 public ArrayList<Usuarios> readDataProvider(String planilha) throws IOException, BiffException {
	 String path="";
	 if(planilha.equals("updates")){
	  		 path ="src/test/resources/Arquivos/dataprovider_update.xls";
	  }else if(planilha.equals("dataprovider")){
		   path ="Arquivos/dataprovider.xls";
		  JSON2XLS.createXlsFile();
	 }
	  Workbook workbook = Workbook.getWorkbook(new File(path));
	  Sheet sheet = workbook.getSheet(planilha);
	  int rows = sheet.getRows();
	  ArrayList<Usuarios> listaUsuarios = new ArrayList<Usuarios>();

	 for (int i = 0; i < rows; i++) {
		 Usuarios usuarios = new Usuarios();
	 	  Cell a1 = sheet.getCell(0, i);
		 Cell a2 = sheet.getCell(1, i);

		 if(planilha.equals("updates")){
			 usuarios.nome = a2.getContents();
			 usuarios.job=a1.getContents();

		 }else {
			 usuarios.id = a1.getContents();
			 usuarios.nome = a2.getContents();
		 }
		 listaUsuarios.add(usuarios);

		}

	 workbook.close();
	 return listaUsuarios;
 }
 public String validateUsersFromDataProvider(String endpoint, String planilha,String nome) throws IOException, BiffException {
	 String result=null;
	 List<Usuarios> contents = readDataProvider(planilha);
	 for (Usuarios content : contents) {
		 if (!content.id.equals("id")) {
			 String responseString = get(url + endpoint + "/" + content.id).
					 then().
					 body("name", equalTo(content.nome))
					 .extract()
					 .path("name");

			 Reporter.addStepLog("Nome: " + content.nome + " Id: " + content.id);

			 if (responseString.equals(content.nome)) {
				 result = "Usuários encontrados na API com sucesso:";
			 } else {
				 result = "Usuários não encontrados na API:";
			 }
		 }
	 }
return result;
 }
	public void putUserFromDataProvider(String endpoint,String plan) throws IOException, BiffException {
		//Leitura do arquivo com os dados do teste\\
		List<Usuarios> contents = readDataProvider(plan);
		int count =0; //número de registros a serem atualizados\\
		for (Usuarios content : contents) {
			if(count<=5) {
				count++;
				//campo a ser atualizado\\
				map.put("job", content.job);
				map.put("name", content.nome);
				given().
						contentType("application/json").
						body(map).
						when().
						put(url + endpoint + "/" + count).
						then().
						statusCode(200);

			}else{
				break;
			}

		}
	}
}




	
		



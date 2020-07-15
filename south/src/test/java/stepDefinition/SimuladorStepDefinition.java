package stepDefinition;


import automation.utils.ArquivoUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.SimuladorDTO;
import paginas.PaginaSimulador;
import service.RequisicaoAPI;
import automation.Pages;
import session.ThreadManager;
import com.cucumber.listener.Reporter;

public class SimuladorStepDefinition {
	RequisicaoAPI req = new RequisicaoAPI();
	private Pages getPages() {
		return ThreadManager.getSession().getPages();
	}
	
	

	//-----------------------Star definition here-------------------------//
	@And("^valido_nome_response \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void valido_nome_response(String endpoint, String nome,String id) throws Throwable {
		String result =req.getNameUserTest(endpoint,nome,id);
		Reporter.addStepLog(result);

	}
	@And("^valido_endpoint \"([^\"]*)\"$")
	public void valido_endpoint(String endpoint) throws Throwable {
		req.valida_endpoint(endpoint);

	}
	@And("^valido_userId_response \"([^\"]*)\" \"([^\"]*)\"$")
	public void valido_user_id_response(String endpoint, String id) throws Throwable {
		String body =req.getField(id,endpoint,"id");
		Reporter.addStepLog("Reponse:" +body);


	}
	@And("^deletar_user_ID \"([^\"]*)\" \"([^\"]*)\"$")
	public void deletar_user_by_id(String endpoint, String iduser) throws Throwable {
		req.deleteUserTest(endpoint,iduser);
		//Reporter.addStepLog(result);
	}
	@And("^criar_registro_api \"([^\"]*)\" \"([^\"]*)\"$")
	public void request_post_api(String endpoint, String nome) throws Throwable {
		req.postUserTest(endpoint, nome);
	}
		@And("^update_registro_api \"([^\"]*)\" \"([^\"]*)\"$")
		public void update_registro_api(String endpoint, String id) throws Throwable {
			req.putUserTest(endpoint,id);

	}
	@And("^update_registro_api_dataprovider \"([^\"]*)\" \"([^\"]*)\"$")
	public void update_registro_api_dataprovider(String endpoint, String plan) throws Throwable {
		req.putUserFromDataProvider(endpoint,plan);

	}

	@Then("^valido_nome_response_dataprovider \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void valido_nome_response_dataprovider(String endpoint ,String planilha,String nome) throws Throwable {
		req.validateUsersFromDataProvider(endpoint,planilha,nome);

	}

	//-----------------------End definition here------------------------//
	


}

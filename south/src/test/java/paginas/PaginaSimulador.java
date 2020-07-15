package paginas;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.AbstractPage;
import session.ThreadManager;

public class PaginaSimulador extends AbstractPage {
	
	
    public  PaginaSimulador(WebDriver driver) {
    	this.getDriver();
    	   	
    }
    public WebDriver getDriver() {
		return ThreadManager.getSession().getDriver();
	}
    public void preencherCampos(String aplicacao,String poupanca,String tempo) throws Exception {
    	inseriValorAplicacao(aplicacao);	
    	inseriValorPoupanca(poupanca);
    	tempoInvestimento(tempo);
    	
    }
    public void validaErroTela(){
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebElement erro = getDriver().findElement(By.xpath("//label[text()='Valor mínimo de 20.00']"));
    	if(erro.isDisplayed()){
    		System.out.println("Valor mínimo é R$20,00");
    	}
        }
    public boolean validaSimulacaoRealizada(){
    	 WebElement result = getDriver().findElement(By.xpath("/html/body/div[3]/div/div/div[1]/div/div[2]/a"));
         return result.getText().toLowerCase().contains("refazer a simula");
    }
    public void inseriValorAplicacao(String valor){
    		WebElement aplicacao = getDriver().findElement(By.id("valorAplicar"));
			aplicacao.clear();
			aplicacao.sendKeys(valor);
	}
    public void inseriValorPoupanca(String valor){
    	WebElement valormensal = getDriver().findElement(By.id("valorInvestir"));
    	valormensal.clear();
        valormensal.sendKeys(valor);
    }
    
    public void clicoBotaoLimpar(){
    	 WebElement botaosimular = getDriver().findElement(By.className("icone"));
         botaosimular.click();
         try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void clicoSelecionoPeriodo(String tipo){
   	 	WebElement combo = getDriver().findElement(By.xpath("//div[contains(@class,'blocoMeses')]"));
        combo.click();
        WebElement periodo = getDriver().findElement(By.xpath("//a[@rel='"+tipo+"']"));
        periodo.click();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
	 public void validoSimulacaoRealizada(){
		 WebElement sucesso = getDriver().findElement(By.xpath("//span[@class='valor']"));
		 
		 try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(sucesso.isDisplayed()){
			 System.out.println("Simulação realizada com sucesso");
		 }else{
			 Assert.fail("Simulação não realizada");
		 }
	 }
    public void tempoInvestimento(String tempo){
    	 WebElement tempoinvestimento = getDriver().findElement(By.id("tempo"));
    	 tempoinvestimento.clear();
         tempoinvestimento.sendKeys(tempo);
    }
    public void clicoBotaoRefazer(){
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebElement refazer = getDriver().findElement(By.xpath("//a[@class='btn btnAmarelo btnRefazer']"));
    	refazer.click();
    }
    public void clicarBotaoSimular(){
    	 
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebElement botaosimular = getDriver().findElement(By.className("simular"));
         botaosimular.click();
         
    }
    public void clicarBotaoPerfil(){
   	 
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebElement botaoperfil = getDriver().findElement(By.xpath("//input[@value ='paraVoce']"));
    	botaoperfil.click();
         
    }
    public String validaErroPreencherFormulario(){
    	return getDriver().findElement(By.id("valorAplicar-error")).getText();
    }
    public String validarMensagemAlerta() {

    	inseriValorAplicacao("10,00");	
    	inseriValorPoupanca("30,00");
    	tempoInvestimento("8");
    	clicarBotaoSimular();

        return getDriver().findElement(By.id("valorAplicar-error")).getText();
    }
    
}

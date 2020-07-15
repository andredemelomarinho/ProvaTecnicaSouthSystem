package cucumberTest;

import java.io.IOException;
import java.net.MalformedURLException;

import com.cucumber.listener.Reporter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.testng.ITestContext;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import automation.utils.*;



@RunWith(Cucumber.class)
@CucumberOptions(
	strict = true,
	features = { "src/test/java/features", },
	monochrome = false,
	glue = { "stepDefinition" },
	plugin = { "pretty", "com.cucumber.listener.ExtentCucumberFormatter:output/report.html"},
	tags = { "@testeAPI9" }
)

public class TestRunner {
	static ITestContext testContext=null;
	@AfterClass

		public static void writeExtentReport() {
			Reporter.assignAuthor("André");

		}



	
	@BeforeClass
	public static void testStarts() throws   InstantiationException, IllegalAccessException {
		ArquivoUtils.deleteFilesArquivos();
	}

}

package cl.leshowski.banner.app;
import java.io.FileInputStream;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.leshowski.banner.model.Persona;
import cl.leshowski.banner.services.Spaiden;
import cl.leshowski.banner.utilities.AccesoApiREST;
import cl.leshowski.banner.utilities.DataHandler;

/**
 * Crea una persona en Banner 
 * @author Luis
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		
		DataHandler dataHandler = new DataHandler();
		
		try {
		
			//Obtengo properties con detalle de conexión, urls, etc
			FileInputStream ip= new FileInputStream("config.properties");
		    Properties p=new Properties();  
		    p.load(ip);  
			
			//Se abre la conexión a la BD
		    dataHandler.jdbcUrl = p.getProperty("jdbcUrl");
			dataHandler.userid = p.getProperty("userid");
			dataHandler.password = p.getProperty("password");
			dataHandler.getDBConnection();
			
			//Se obtiene la salida de la API
			String resultado = AccesoApiREST.obtSalidaServicio(p.getProperty("apiPersona"), null);
			
			//La salida de la api se mapea al objeto persona
			Persona persona = new Persona();
			persona = AccesoApiREST.mapeaJsonAObjeto(resultado, "", Persona.class);
			
			//Se actualizan algunos datos del objeto para hacer la persistencia en Banner
			persona.setSpriden_first_name(persona.getName());
			persona.setSpriden_last_name(persona.getName());
			persona.setSpriden_id("GENERATED");
			persona.setSpriden_entity_ind("P");
			persona.setSpriden_origin("JAVA");
			persona.setSpriden_data_origin("JAVA");
			
			//Se crea la persona en Banner
			Spaiden.p_create(dataHandler.conn, persona);
			
			//Se imprime el resultado de la persona creada
			System.out.println((new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(persona));
			
			
		}catch(Exception e) {
			
			throw e;
			
		}finally {
			if(dataHandler.conn != null && !dataHandler.conn.isClosed())
				dataHandler.conn.close();
		}
		
	}
}

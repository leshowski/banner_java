package cl.leshowski.banner.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class AccesoApiREST {
	
	/**
	 * Entrega la respuesta del servicio REST pasado por parametro
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static String obtSalidaServicio(String uri,String apikey) throws Exception {
		
		String salida = "";
		String stringbuffer;
		
		try {
		
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if(apikey !=null && !apikey.isEmpty())
				conn.setRequestProperty("x-api-key", apikey);
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Falla acceso Servicio : HTTP error code : "+conn.getResponseCode()+". URI:"+uri);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

			while ((stringbuffer = br.readLine()) != null) {
				salida = salida+stringbuffer;
			}
			
			conn.disconnect();
			
		}catch(Exception e) {
			throw new RuntimeException("Problemas en el Servicio: "+e.getMessage());
		}
		
		return salida;
		
	}
	
	public static <T> T mapeaJsonAObjeto(String json, String nodoBase,Class<T> clazz) throws Exception{
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			JsonNode nodeData = node.at(nodoBase);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			return objectMapper.readValue(nodeData.toString(), clazz);
			
		}catch(Exception e) {
			throw new Exception("Error al querer mapear el Json a una lista de valores. Detalle:"+e.getMessage());
		}

	}
	
	/**
	 *
	 * Devuelve el String Json mapeado como una lista de objetos del tipo de la clase
	 * pasado por parametro
	 * 
	 * @param json String con el Json
	 * @param nodoBase Punto de partida para mapear el Json de entrada
	 * @param c Tipo de objeto a mapear en la lista resultante
	 * @return
	 * @throws Exception
	 */
	public static List<?> mapeaJsonLista(String json, String nodoBase,Class c) throws Exception{
		List<?> navigation = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			JsonNode programasNode = node.at(nodoBase);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			navigation = objectMapper.readValue(programasNode.toString(),objectMapper.getTypeFactory().constructCollectionType(List.class, c));
			
		}catch(Exception e) {
			throw new Exception("Error al querer mapear el Json a una lista de valores. Detalle:"+e.getMessage());
		}
		return navigation;
	}

	/**
	 * Dado el Json de entrada, se obtiene el String segun la ruta pasada por parametro
	 * @param json
	 * @param nodoBase
	 * @return
	 * @throws Exception
	 */
	public static String mapeaJsonString(String json, String nodoBase) throws Exception{
		
		String resultado = null;
		
		try {
		
			ObjectMapper mapper0 = new ObjectMapper();
			JsonNode node0 = mapper0.readTree(json.toLowerCase());
			JsonNode node01 = node0.at(nodoBase);
			resultado = node01.asText();
			
			return resultado;
			
		}catch(Exception e) {
			throw new Exception("Error al querer mapear el Json a un String. Detalle:"+e.getMessage());
		}
	}
}

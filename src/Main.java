public class Main {

	public static void main(String[] args) throws Exception {
		
		DataHandler dataHandler = new DataHandler();
		dataHandler.jdbcUrl = "jdbc:oracle:thin:@//ip:puerto/sid_or_servicename";
		dataHandler.userid = "usuario";
		dataHandler.password = "clave";
		
		dataHandler.getDBConnection();
		
		
		
		Integer vl_pidm = Spaiden.p_create(dataHandler.conn, 
										 "GENERATED", 
										 "TEST PONCE", 
										 "TEST LUIS", 
										 null, 
										 null, 
										 "P", 
										 null, 
										 "BANINST1", 
										 null, 
										 "BANINST1", 
										 null);
		
		System.out.println(vl_pidm);
		
		dataHandler.conn.close();


	}

}

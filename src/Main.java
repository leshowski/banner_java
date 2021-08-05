public class Main {

	public static void main(String[] args) throws Exception {
		
		DataHandler dataHandler = new DataHandler();
		dataHandler.jdbcUrl = "jdbc:oracle:thin:@//10.0.2.161:1521/DEVL.dbipaiepnp.ipaieprednp.oraclevcn.com";
		dataHandler.userid = "BANINST1";
		dataHandler.password = "devl2021";
		
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

import java.sql.CallableStatement;
import java.sql.Connection;

import oracle.jdbc.internal.OracleTypes;

public class Spaiden {
	
	/**
	 * Crea un registro en la tabla SPAIDEN
	 * @param conn
	 * @param ve_spriden_id
	 * @param ve_spriden_last_name
	 * @param ve_spriden_first_name
	 * @param ve_spriden_mi
	 * @param ve_spriden_change_ind
	 * @param ve_spriden_entity_ind
	 * @param ve_spriden_user
	 * @param ve_spriden_origin
	 * @param ve_spriden_ntyp_code
	 * @param ve_spriden_data_origin
	 * @param ve_spriden_surname_prefix
	 * @return
	 * @throws Exception
	 */
	public static int p_create(Connection conn,
							   String ve_spriden_id,
            				   String ve_spriden_last_name,
            					String ve_spriden_first_name,
            					String ve_spriden_mi,
            					String ve_spriden_change_ind,
            					String ve_spriden_entity_ind,
            					String ve_spriden_user,
            					String ve_spriden_origin,
            					String ve_spriden_ntyp_code,
            					String ve_spriden_data_origin,
            					String ve_spriden_surname_prefix) throws Exception {
		
		CallableStatement callst = null;
		
		Integer vl_pidm = 0;
		
		try {
			
			callst = conn.prepareCall("{CALL gb_identification.p_create(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			callst.setString(1, ve_spriden_id);
			callst.setString(2, ve_spriden_last_name);
			callst.setString(3, ve_spriden_first_name);
			callst.setString(4, ve_spriden_mi);
			callst.setString(5, ve_spriden_change_ind);
			callst.setString(6, ve_spriden_entity_ind);
			callst.setString(7, ve_spriden_user);
			callst.setString(8, ve_spriden_origin);
			callst.setString(9, ve_spriden_ntyp_code);
			callst.setString(10, ve_spriden_data_origin);
			callst.setString(11, ve_spriden_surname_prefix);
			callst.registerOutParameter(1, OracleTypes.VARCHAR);
			callst.registerOutParameter(12, OracleTypes.INTEGER);
			callst.registerOutParameter(13, OracleTypes.VARCHAR);
			callst.execute();
			
			vl_pidm = callst.getInt(12);
			
			return vl_pidm;

		} catch(Exception e) {
			throw e;
		} finally {
			if(callst !=null && !callst.isClosed())
				callst.close();
		}
	}
}

package helperUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import connectionFactory.Connections;
import queryLibrary.Queries;

public class TestScenarioBulkInsertion {
	
	public static Map<String, String> existingTestcaseApp = new HashMap<String, String>();

	public static void insertTSandUpdateTsHistory(String Appname, String TSname, String userId) {

		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Boolean flag = true;
		Boolean access = false;
		int apid=0;
		int tsID=0;
		int existingTsID =0;
		Calendar cal = Calendar.getInstance(); 
		java.sql.Timestamp timestamps = new java.sql.Timestamp(cal.getTimeInMillis());
		int intUID = Integer.parseInt(userId);

		boolean TSExists = false;

		String queryAppId = Queries.getAppIds1(Appname);

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(queryAppId);

			while(rs.next()) {
				if(Appname.equalsIgnoreCase(rs.getString(2))) {
					apid = rs.getInt(1);
				}
			}

			// Check If User has Access to that application
			String checkIfHasAccess = Queries.ifUserHasAccessWhileBulkUpload+userId;
			rs3 = st.executeQuery(checkIfHasAccess);
			while(rs3.next()) {
				if (rs3.getInt(2) == apid) {
					access = true;
				}
			}

			System.out.println("ACCESS : " + access);
			if(access) {
				// Insert into Test Scenario Master
				if (apid != 0) {
					// check if Test Scenario name exists
					String checkIfExistsQuery = Queries.getTestCaseNamesByApplicationName+apid;
					rs1 = st.executeQuery(checkIfExistsQuery);
					while(rs1.next()) {
						if(TSname.equalsIgnoreCase(rs1.getString(2))) {
							TSExists = true;
							existingTsID = rs1.getInt(1);
						}
					}
					System.out.println("isMal : " + TSExists);
					// exists
					if (TSExists == true) {
						/*String status = "Update";
						// Add to history table
						String updatehistory = Queries.insertIntoTestScenarioHistory;
						PreparedStatement preparedStatement1 = cn.prepareStatement(updatehistory);
						preparedStatement1.setInt(1, existingTsID);
						preparedStatement1.setString(2, TSname);
						preparedStatement1.setInt(3, apid);
						preparedStatement1.setInt(4, intUID);
						preparedStatement1.setString(5, status);
						preparedStatement1.setTimestamp(6, timestamps);
						preparedStatement1.executeUpdate();

						// Change TS OwnerShip
						String updateTSOwnership = Queries.updateTsMaster(existingTsID, intUID);
						Statement sts = cn.createStatement();
						sts.executeUpdate(updateTSOwnership);*/
						existingTestcaseApp.put(TSname, Appname);
					}else {
						// Add To Test Scenario Master
						String status = "Create";
						String insertTCquery = Queries.addTestCases;
						PreparedStatement preparedStatement = cn.prepareStatement(insertTCquery);
						preparedStatement.setString(1, TSname);
						preparedStatement.setInt(2, apid);
						preparedStatement.setInt(3, intUID);
						preparedStatement.setTimestamp(4, timestamps);
						preparedStatement.executeUpdate();

						// Update Test Scenario Master history
						String getTsid = Queries.getTestScenarioId(apid, TSname);
						rs2 = st.executeQuery(getTsid);
						while(rs2.next()) {
							tsID = rs2.getInt(1);
						}
						String updatehistory = Queries.insertIntoTestScenarioHistory;
						PreparedStatement preparedStatement1 = cn.prepareStatement(updatehistory);
						preparedStatement1.setInt(1, tsID);
						preparedStatement1.setString(2, TSname);
						preparedStatement1.setInt(3, apid);
						preparedStatement1.setInt(4, intUID);
						preparedStatement1.setString(5, status);
						preparedStatement1.setTimestamp(6, timestamps);
						preparedStatement1.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

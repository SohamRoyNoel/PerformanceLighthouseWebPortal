package controllers.updateControllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.Gson;

import helperUtils.TestScenarioBulkInsertion;

public class UploadFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Fillo fillo = new Fillo();

	public UploadFileController() {
		super();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Boolean flag = true;
		String jsonx = null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}

		ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
		PrintWriter writer = response.getWriter();

		try {
			System.err.println("Uploaded");
			List<FileItem> items = uploadHandler.parseRequest(request);
			Map<String, String> mps = new HashMap<String, String>();

			for (FileItem item : items) {
				System.err.println("Uploaded For");
				if (!item.isFormField()) {
					HttpSession session=request.getSession(false);  
					String userID=(String)session.getAttribute("LoginID");
					String fn = userID+"_"+item.getName();
					File file = new File(request.getServletContext().getRealPath("/")+"TestCaseData/", fn);
					item.write(file);
					List<String> jsonList = new ArrayList<String>();

					// Now Read the Excel
					String bookPath = request.getServletContext().getRealPath("/")+"TestCaseData/"+fn;
					readEcelData(bookPath, userID);
					/*
					 * If the Excel has Test case name that already exists
					 * */
					System.err.println("ERROR : " + TestScenarioBulkInsertion.existingTestcaseApp.size());
					if (TestScenarioBulkInsertion.existingTestcaseApp.size() > 0) {
						deleteExcel(bookPath);
						mps = TestScenarioBulkInsertion.existingTestcaseApp;
						String appender = "";
						String entity = "";
						int counter = 0;
						
						for (Map.Entry<String, String> entry : mps.entrySet()) {
							counter++;
							entity = "Ts"+counter;
							appender = "\""+entity+"\":{\"slNo\":\""+counter+"\",\"tc\":\""+entry.getKey()+"\",\"app\":\""+entry.getValue()+"\",\"tb\":\""+"<input type='text' class='form-control' id='inputEmail4' placeholder='Email'>"+"\",\"Button\":\""+"<button id='puller' onClick='openerEditTC(this);' type='submit' class='btn btn-primary'>Update</button>"+"\", \"Status\": \"Test Data InProgress\"}";
							jsonList.add(appender);
						}
						//jsonList.add();
						System.out.println("Listed Val : " + jsonList);
						String cnvjson = jsonList.toString();
						String newJsonBuilder = "{"+cnvjson.substring(1, cnvjson.length()-1)+ "}";
						System.err.println("NEW STRING : " + newJsonBuilder);
						jsonx = new Gson().toJson(newJsonBuilder);
						TestScenarioBulkInsertion.existingTestcaseApp.clear();
						response.setContentType("application/json");
			            response.getWriter().write(jsonx);
					}else {
						deleteExcel(bookPath);
						//mps.put("Status", "Test Data Inserted Successfully");
						String x = "{\"Ts1\":{\"Status\": \"Test Data Inserted Successfully\"}}";
						List<String> jsonList1 = new ArrayList<String>();
						jsonList1.add(x);
						jsonx = new Gson().toJson(jsonList1);
						System.err.println("JSON ERR : " + jsonx);
						response.setContentType("application/json");
						response.getWriter().write(jsonx);
						
					}
					
					
				}
			}
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			writer.close();
		}

	}

	public void readEcelData(String bookPath, String userId) {
		try {
			//readExcelData("E:\\TEST WEBSITE\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\PerformanceWebFramework\\TestCaseData\\2_Book1.xlsx", "Sheet1");
			Fillo fillo=new Fillo();
			Connection connection=fillo.getConnection(bookPath);
			String strQuery="Select * from Sheet1";
			Recordset recordset=connection.executeQuery(strQuery);

			while(recordset.next()){
				String Tcname = recordset.getField("TcName");
				String AppName = recordset.getField("Application");
				TestScenarioBulkInsertion.insertTSandUpdateTsHistory(AppName, Tcname, userId);
				
			}

			
			
			recordset.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteExcel(String bookPath) {
		try {
			FileUtils.forceDelete(FileUtils.getFile(bookPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}


public class abc {

	public static void main(String[] args) {
		int a = 0;
		int b = 0;
		int c = 0;
		String embed = "\"categories\": [{"+
				"\"category\": [{"+
				"\"label\": \"Nav_MAX_UnloadEven\""+
				"},"+
				"{ \"label\": \"Nav_AVG_UnloadEvent\" },"+
				"{ \"label\": \"Nav_MIN_UnloadEvent\" }]}],"+
				"\"dataset\": [{"+
				"\"data\": [{"+
				"\"value\": \""+b+"\"},"+
				"{ \"value\": \""+a+"\" },"+ 
				"{ \"value\": \""+c+"\" }]}]";
		
		String chart = "\"chart\": {"+
				"\"theme\": \"fusion\","+
				"\"yaxisname\": \"Loading Times (Seconds)\","+
				"\"showvalues\": \"1\","+
				"\"placeValuesInside\": \"1\","+
				"\"rotateValues\": \"1\","+
				"\"valueFontColor\": \"#ffffff\","+
				"\"numberprefix\": \"\","+
				"\"numVisiblePlot\": \"15\","+
				"\"showLabels\": \"0\","+
				"\"labeldisplay\": \"WRAP\","+
				"\"linethickness\": \"3\","+
				"\"scrollheight\": \"10\","+
				"\"flatScrollBars\": \"1\","+
				"\"scrollShowButtons\": \"0\","+
				"\"scrollColor\": \"#cccccc\","+
				"\"exportEnabled\": \"1\","+
				"\"exportFormats\": \"csv | xlsx\","+
				"\"showHoverEffect\": \"1\""+

          "},";
		

		System.out.println("{"+ chart + embed + "}");
	}

}

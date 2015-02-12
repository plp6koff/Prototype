package com.consultancygrid.trz.util;

//Java
import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TrzStatic;

/**
 * This class demonstrates the conversion of an XML file to PDF using JAXP
 * (XSLT) and FOP (XSL-FO).
 */
public class PDFCreatorUtil {

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {

		System.out.println("FOP ExampleXML2PDF\n");
		System.out.println("Preparing...");

		// Setup directories

		TrzStatic dod = null;
		TrzStatic oR = null;
		TrzStatic oS = null;
		
		
		
		BigDecimal b = null;
		BigDecimal h = null;
		BigDecimal u = null;
		BigDecimal r = null;
		EmployeeSalary emplSal = null;

		dod = new TrzStatic();
		dod.setValueType("percent");
		dod.setValue("10");

		oR = new TrzStatic();
		oR.setValueType("percent");
		oR.setValue("17.8");

		oS = new TrzStatic();
		oS.setValueType("percent");
		oS.setValue("12.9");

		emplSal = new EmployeeSalary();

		b = BigDecimal.valueOf(Double.valueOf(1000.0));
		h = BigDecimal.valueOf(Double.valueOf(800.0));
		u = BigDecimal.valueOf(Double.valueOf(100.0));
		r = BigDecimal.valueOf(Double.valueOf(500.0));

		emplSal = new EmployeeSalary();
		Employee empl = new Employee();
		empl.setFirstName("Anton");
		empl.setLastName("Georgiev");
		Period period = new Period();
		period.setCode("2015-12");
		emplSal.setEmployee(empl);
		emplSal.setPeriod(period);
		emplSal.setV01(BigDecimal.valueOf(Double.valueOf("1000")));
		emplSal.setV06(BigDecimal.valueOf(Double.valueOf("800")));
//		EmployeeSallaryCalculateUtil.calcSettings(b.doubleValue(),
//				h.doubleValue(), r.doubleValue(), u.doubleValue(), 0.0, 0.0,
//				emplSal, dod, oR, oS);
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><v>");
		sb.append("<v1>" + emplSal.getV01().toString() + "</v1>");
		sb.append("<v2>" + emplSal.getV02().toString() + "</v2>");
		sb.append("<v3>" + emplSal.getV03().toString() + "</v3>");
		sb.append("<v4>" + emplSal.getV04().toString() + "</v4>");
		sb.append("<v5>" + emplSal.getV05().toString() + "</v5>");
		sb.append("<v6>" + emplSal.getV06().toString() + "</v6>");
		sb.append("<v22>" + emplSal.getV22().toString() + "</v22></v>");

	}

	private static String fileFromObj(EmployeeSalary emplSal) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><v>");
		sb.append("<v1>" + emplSal.getV01().toString() + "</v1>");
		sb.append("<v2>" + emplSal.getV02().toString() + "</v2>");
		sb.append("<v3>" + emplSal.getV03().toString() + "</v3>");
		sb.append("<v4>" + emplSal.getV04().toString() + "</v4>");
		sb.append("<v5>" + emplSal.getV05().toString() + "</v5>");
		sb.append("<v6>" + emplSal.getV06().toString() + "</v6>");
		sb.append("<v7>" + emplSal.getV07().toString() + "</v7>");
		sb.append("<v8>" + emplSal.getV08().toString() + "</v8>");
		sb.append("<v9>" + emplSal.getV09().toString() + "</v9>");
		sb.append("<v10>" + emplSal.getV10().toString() + "</v10>");
		sb.append("<v11>" + emplSal.getV11().toString() + "</v11>");
		sb.append("<v12>" + emplSal.getV12().toString() + "</v12>");
		sb.append("<v13>" + emplSal.getV13().toString() + "</v13>");
		sb.append("<v14>" + emplSal.getV14().toString() + "</v14>");
		sb.append("<v16>" + emplSal.getV15().toString() + "</v16>");
		sb.append("<v17>" + ((emplSal.getV17() !=  null) ? emplSal.getV17().toString(): "0.0") + "</v17>");
		sb.append("<v18>" + ((emplSal.getV18() !=  null) ? emplSal.getV18().toString(): "0.0") + "</v18>");
		sb.append("</v>");
		return sb.toString();
	}

	public static File createPDF(EmployeeSalary emplSal, String outDirPath) {
		
		File outDir = null;
		File pdffile = null;
		try {

			String tempFile = fileFromObj(emplSal);

			File baseDir = new File("src/resources");
			outDir = new File(baseDir, "out");
			outDir.mkdirs();
			// Setup input and output files
			File xsltfile = new File(baseDir, "xsltTest.xsl");
			 pdffile = new File(outDirPath, "PDF" + emplSal.getEmployee().getMatchCode()+emplSal.getPeriod().getCode()+".pdf");

			System.out.println("Stylesheet: " + xsltfile);
			System.out.println("Output: PDF (" + pdffile + ")");
			System.out.println();
			System.out.println("Transforming...");

			DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
			Configuration cfg = null;
			try {
				File conf = new File(baseDir, "fop.xconf");
				cfg = cfgBuilder.buildFromFile(conf);
			} catch (ConfigurationException e1) {
			} catch (SAXException e1) {
			}
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setStrictValidation(false);
			if (cfg != null) {
				try {
					fopFactory.setUserConfig(cfg);
				} catch (FOPException e1) {
				}
			}
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

			// configure foUserAgent as desired

			// Setup output
			OutputStream out = new java.io.FileOutputStream(pdffile);
			out = new java.io.BufferedOutputStream(out);

			try {
				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
						foUserAgent, out);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory
						.newTransformer(new StreamSource(xsltfile));
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				// Set the value of a <param> in the stylesheet
				transformer.setParameter("paramPeriod", emplSal.getPeriod()
						.getCode());
				transformer.setParameter("paramEmployee", emplSal.getEmployee()
						.getFirstName()
						+ " "
						+ emplSal.getEmployee().getLastName());
				transformer.setParameter("h1", ResourceLoaderUtil
						.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL1));
				transformer.setParameter("h2", ResourceLoaderUtil
						.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL1));

				File tmpFile = new File(outDir, "xmlTmp.xml");
				FileUtils.writeStringToFile(tmpFile, tempFile, "UTF-8");
				// Setup input for XSLT transformation
				Source src = new StreamSource(tmpFile);

				// Resulting SAX events (the generated FO) must be piped through
				// to FOP
				Result res = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, res);
			} finally {
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		return pdffile;
	}
}
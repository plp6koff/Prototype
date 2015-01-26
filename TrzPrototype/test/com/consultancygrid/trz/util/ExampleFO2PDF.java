//package com.consultancygrid.trz.util;
//
//// Java
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//
//import javax.naming.ConfigurationException;
//import javax.xml.transform.Result;
//import javax.xml.transform.Source;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.sax.SAXResult;
//import javax.xml.transform.stream.StreamSource;
//
//import org.apache.fop.apps.FOPException;
//import org.apache.fop.apps.FOUserAgent;
//import org.apache.fop.apps.Fop;
//import org.apache.fop.apps.FopFactory;
//import org.apache.fop.apps.FormattingResults;
//import org.apache.fop.apps.MimeConstants;
//import org.apache.fop.apps.PageSequenceResults;
//import org.junit.Before;
//import org.xml.sax.SAXException;
//
//import com.consultancygrid.trz.model.EmployeeSalary;
//import com.consultancygrid.trz.model.EmployeeSettings;
//import com.consultancygrid.trz.model.TrzStatic;
//
///**
// * This class demonstrates the conversion of an FO file to PDF using FOP.
// */
//public class ExampleFO2PDF {
//
//	// configure fopFactory as desired
//	private final FopFactory fopFactory = null;
//
//	/**
//	 * Converts an FO file to a PDF file using FOP
//	 * 
//	 * @param fo
//	 *            the FO file
//	 * @param pdf
//	 *            the target PDF file
//	 * @throws IOException
//	 *             In case of an I/O problem
//	 * @throws FOPException
//	 *             In case of a FOP problem
//	 */
//	public void convertFO2PDF(File fo, File pdf, String xml)
//			throws IOException, FOPException {
//
//		FopFactory fopFactory = FopFactory.newInstance();
//
//		OutputStream out = null;
//
//		try {
//			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
//			// configure foUserAgent as desired
//
//			// Setup output stream. Note: Using BufferedOutputStream
//			// for performance reasons (helpful with FileOutputStreams).
//			out = new FileOutputStream(pdf);
//			out = new BufferedOutputStream(out);
//
//			// Construct fop with desired output format
//			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
//					out);
//
//			// Setup JAXP using identity transformer
//			TransformerFactory factory = TransformerFactory.newInstance();
//			Transformer transformer = factory.newTransformer(); // identity
//																// transformer
//
//			// Setup input stream
//			Source src = new StreamSource(fo);
//
//			// Resulting SAX events (the generated FO) must be piped through to
//			// FOP
//			Result res = new SAXResult(fop.getDefaultHandler());
//
//			// Start XSLT transformation and FOP processing
//			transformer.transform(src, res);
//
//			// Result processing
//			FormattingResults foResults = fop.getResults();
//			java.util.List pageSequences = foResults.getPageSequences();
//			for (java.util.Iterator it = pageSequences.iterator(); it.hasNext();) {
//				PageSequenceResults pageSequenceResults = (PageSequenceResults) it
//						.next();
//				System.out
//						.println("PageSequence "
//								+ (String.valueOf(pageSequenceResults.getID())
//										.length() > 0 ? pageSequenceResults
//										.getID() : "<no id>") + " generated "
//								+ pageSequenceResults.getPageCount()
//								+ " pages.");
//			}
//			System.out.println("Generated " + foResults.getPageCount()
//					+ " pages in total.");
//
//		} catch (Exception e) {
//			e.printStackTrace(System.err);
//			System.exit(-1);
//		} finally {
//			out.close();
//		}
//	}
//
//	/**
//	 * Main method.
//	 * 
//	 * @param args
//	 *            command-line arguments
//	 */
//	public static void main(String[] args) {
//		try {
//
//			TrzStatic dod = null;
//			TrzStatic oR = null;
//			TrzStatic oS = null;
//			BigDecimal b = null;
//			BigDecimal h = null;
//			BigDecimal u = null;
//			BigDecimal r = null;
//			EmployeeSalary emplSalary = null;
//
//			dod = new TrzStatic();
//			dod.setValueType("percent");
//			dod.setValue("10");
//
//			oR = new TrzStatic();
//			oR.setValueType("percent");
//			oR.setValue("17.8");
//
//			oS = new TrzStatic();
//			oS.setValueType("percent");
//			oS.setValue("12.9");
//
//			emplSalary = new EmployeeSalary();
//
//			b = BigDecimal.valueOf(Double.valueOf(1000.0));
//			h = BigDecimal.valueOf(Double.valueOf(800.0));
//			u = BigDecimal.valueOf(Double.valueOf(100.0));
//			r = BigDecimal.valueOf(Double.valueOf(500.0));
//
//			emplSalary = new EmployeeSalary();
//			EmployeeSallaryCalculateUtil.calcSettings(b.doubleValue(),
//					h.doubleValue(), r.doubleValue(), u.doubleValue(), 0.0,
//					0.0, emplSalary, dod, oR, oS);
//
//			StringBuffer sb = new StringBuffer(
//					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><v>");
//			sb.append("<v1>" + emplSalary.getV01().toString() + "</v1>");
//			sb.append("<v2>" + emplSalary.getV02().toString() + "</v2>");
//			sb.append("<v3>" + emplSalary.getV03().toString() + "</v3>");
//			sb.append("<v4>" + emplSalary.getV04().toString() + "</v4>");
//			sb.append("<v5>" + emplSalary.getV05().toString() + "</v5>");
//			sb.append("<v6>" + emplSalary.getV06().toString() + "</v6>");
//			sb.append("<v7>" + emplSalary.getV07().toString() + "</v7>");
//			sb.append("<v8>" + emplSalary.getV08().toString() + "</v8>");
//			sb.append("<v9>" + emplSalary.getV09().toString() + "</v9>");
//			sb.append("<v10>" + emplSalary.getV10().toString() + "</v10>");
//			sb.append("<v11>" + emplSalary.getV11().toString() + "</v11>");
//			sb.append("<v12>" + emplSalary.getV12().toString() + "</v12>");
//			sb.append("<v13>" + emplSalary.getV13().toString() + "</v13>");
//			sb.append("<v14>" + emplSalary.getV14().toString() + "</v14>");
//			sb.append("<v16>" + emplSalary.getV15().toString() + "</v15>");
//			sb.append("<v17>" + emplSalary.getV16().toString() + "</v16>");
//			sb.append("<v18>" + emplSalary.getV17().toString() + "</v17>");
//			sb.append("<v19>" + emplSalary.getV18().toString() + "</v18>");
//			sb.append("<v20>" + emplSalary.getV19().toString() + "</v19>");
//			sb.append("<v21>" + emplSalary.getV20().toString() + "</v20>");
//			sb.append("<v22>" + emplSalary.getV21().toString() + "</v21>");
//			sb.append("<v23>" + emplSalary.getV22().toString() + "</v22></v>");
//
//			System.out.println("FOP ExampleFO2PDF\n");
//			System.out.println("Preparing...");
//
//			// Setup directories
//			File baseDir = new File("src/resources");
//			File outDir = new File(baseDir, "out");
//			outDir.mkdirs();
//
//			// Setup input and output files
//			File fofile = new File(baseDir, "helloworld.fo");
//			// File fofile = new File(baseDir,
//			// "../fo/pagination/franklin_2pageseqs.fo");
//			File pdffile = new File(outDir, "ResultFO2PDF.pdf");
//
//			System.out.println("Input: XSL-FO (" + fofile + ")");
//			System.out.println("Output: PDF (" + pdffile + ")");
//			System.out.println();
//			System.out.println("Transforming...");
//
//			ExampleFO2PDF app = new ExampleFO2PDF();
//			app.convertFO2PDF(fofile, pdffile);
//
//			System.out.println("Success!");
//		} catch (Exception e) {
//			e.printStackTrace(System.err);
//			System.exit(-1);
//		}
//	}
//}
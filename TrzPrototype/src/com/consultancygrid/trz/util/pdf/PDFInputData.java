package com.consultancygrid.trz.util.pdf;

public class PDFInputData {

	
	private String fileName;
	
	private String departmentName;
	
	private String employeeName;
	
	private String periodCode;
	
	private Double sum;
	
	private String xmlFile;
	
	private String outDirPath; 
	
	private Double vaucher1;
	
	private Double vaucher2;
	
	
	
	public PDFInputData(String fileName, 
			String departmentName,
			String employeeName,
			String periodCode,
			Double sum,
			String xmlFile,  
			String outDirPath, 
			Double vaucher1,
			Double vaucher2) {
		
		this.fileName = fileName; 
		this.departmentName = departmentName;
		this.employeeName = employeeName;
		this.periodCode = periodCode;
		this.sum = sum;
		this.xmlFile = xmlFile;  
		this.outDirPath = outDirPath; 
		this.vaucher1 = vaucher1;
		this.vaucher2 =  vaucher2;
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	/**
	 * @return the periodCode
	 */
	public String getPeriodCode() {
		return periodCode;
	}
	/**
	 * @param periodCode the periodCode to set
	 */
	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	/**
	 * @return the sum
	 */
	public Double getSum() {
		return sum;
	}
	/**
	 * @param sum the sum to set
	 */
	public void setSum(Double sum) {
		this.sum = sum;
	}
	/**
	 * @return the xmlFile
	 */
	public String getXmlFile() {
		return xmlFile;
	}
	/**
	 * @param xmlFile the xmlFile to set
	 */
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
	/**
	 * @return the outDirPath
	 */
	public String getOutDirPath() {
		return outDirPath;
	}
	/**
	 * @param outDirPath the outDirPath to set
	 */
	public void setOutDirPath(String outDirPath) {
		this.outDirPath = outDirPath;
	}
	/**
	 * @return the vaucher1
	 */
	public Double getVaucher1() {
		return vaucher1;
	}
	/**
	 * @param vaucher1 the vaucher1 to set
	 */
	public void setVaucher1(Double vaucher1) {
		this.vaucher1 = vaucher1;
	}
	/**
	 * @return the vaucher2
	 */
	public Double getVaucher2() {
		return vaucher2;
	}
	/**
	 * @param vaucher2 the vaucher2 to set
	 */
	public void setVaucher2(Double vaucher2) {
		this.vaucher2 = vaucher2;
	}
	
	
}

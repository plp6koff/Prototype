package com.consultancygrid.trz.base;


public interface Constants {

	/*
	 * Constants related to the employees columns size
	 */
	int col0MW = 4;
	
	int col1MW = 120;
	
	int col2MW = 200;
	
	int col3MW = 260;
	
	int col4MW = 260;
	
	int col5MW = 80;
	
	int col6MW = 50;
	
	int col7MW = 60;
	
	int col8MW = 60;
	
	int col9MW = 200;
	
	int col10MW = 150;

	String PERSISTENCE_UNIT_NAME = "trzUnit";
	
    String EMPTY_STRING = "";
	
    String DEFAULT_DATA_DIR= "data_dir";
    
    String CUSTOM_CFG_PATH = "resources/custom.cfg.properties";
    
	String CONFIG_PATH = "conf/config.properties";
	String CONFIG_COLUMN_VISIBILITY_PATH = "resources/column.visibility.properties";
	String CONFIG_CLIENT = "resources/client.configuration.properties";
	
	String CSV_PATH = "csv.path";
	String DATA_FOLDER = "data.folder";
	
	String NUMBER_COLUMN_KEY = "number";
	String INVOICE_TYPE_COLUMN_KEY = "invoiceType";
	String CURRENCY_COLUMN_KEY = "currency";
	String DATE_COLUMN_KEY = "date";
	String EXCL_VAT_COLUMN_KEY = "exclVat";
	String INCL_VAT_COLUMN_KEY = "inclVat";
	String TOTAL_VAT_COLUMN_KEY = "totalVat";
	String CREATION_DATE_COLUMN_KEY = "creationDate";
	String SUPPLIER_NAME_COLUMN_KEY = "supplierName";
	String SUPPLIER_IDENT_COLUMN_KEY = "supplierIdent";
	String BUYER_NAME_COLUMN_KEY = "buyerName";
	String BUYER_IDENT_COLUMN_KEY = "buyerIdent";
	String CODE_COLUMN_KEY = "code";
	String ERROR_COLUMN_KEY = "error";
	String ZIP_COLUMN_KEY = "zip";
	String WF_STEPS_COLUMN_KEY = "wfSteps";
	String INVOICE_NOTES_COLUMN_KEY = "invoiceNotes";
	String ATTACHMENTS_COLUMN_KEY = "attachments";
	String CDC_COLUMN_KEY = "cdc";
	String SOFTWARE_VERSION_COLUMN_KEY = "softwareVersion";
	
	String CLIENT_KEY = "client";
	
	String MAIN_FXML_PATH = "resources/fxml/main.fxml";
	String SEARCH_FXML_PATH = "resources/fxml/search.fxml";
	String WF_FXML_PATH = "resources/fxml/wf.fxml";
	String PDF_FXML_PATH = "resources/fxml/pdf.fxml";
	String PARTY_FXML_PATH = "resources/fxml/party.fxml";
	String POPUP_FXML_PATH = "resources/fxml/popup.fxml";
	String NOTES_FXML_PATH = "resources/fxml/notes.fxml";
	String PROMPT_FXML_PATH = "resources/fxml/prompt.fxml";
	String LOGO_IMAGE_PATH = "resources/image/logo.jpg";
	
	String WF_ICON = "resources/image/plus-ico.png";
	String PDF_ICON = "resources/image/pdf-ico.png";
	String DOWNLOAD_ICON = "resources/image/dwn-ico.png";
	String ATTACHMENT_ICON = "resources/image/attach-ico.png";
	String INVOICE_NOTES_ICON = "resources/image/notes-ico.png";
	
	String MAIN_TITLE = "BillViewer";
	String SEARCH_TITLE = "Search";
	String MAIN_CSS = "resources/css/main.css";
	String PARTY_CSS = "resources/css/party.css";
	String ALERT_SUCCESS_CSS = "resources/css/success.css";
	String ALERT_FAILURE_CSS = "resources/css/failure.css";
	String PROMPT_CSS = "resources/css/prompt.css";
	String PROPERTIES_FILE = "resources.lang.Lang";
	
	String SUPPLIER = "Supplier";
	String BUYER = "Buyer";
	
	String SEARCH_INV_NUMBER = "searchInvNumber";
	String SEARCH_CURRENCY = "searchCurrency";
	String SEARCH_SUPP_NAME = "searchSuppName";
	String SEARCH_SUPP_IDENT = "searchSuppIdent";
	String SEARCH_BUYER_NAME = "searchBuyerName";
	String SEARCH_BUYER_IDENT = "searchBuyerIdent";
	String SEARCH_INV_TYPE = "searchInvType";
	String SEARCH_START_DATE = "searchStartDate";
	String SEARCH_END_DATE = "searchEndDate";
	String SEARCH_START_INV_DATE = "searchStartInvDate";
	String SEARCH_END_INV_DATE = "searchEndInvDate";
	
	String PLESE_SELECT = "Please select";
	String DLMTR_STEPS = "#";
	String DLIMTR_ACTOR_ACTION = "~";
	
	String DLMTR_INVOICE_NOTES = "||";
	String DLMTR_INVOICE_NOTES_COLUMNS = "~~";
	
	String CSV_EXPORT_NAME = "data/_lr.csv";
	String PARTNERS_EXPORT_NAME = "data/partners.zip";
	String MASS_PROOF_EXPORT_NAME = "proof_export.zip";
	String REGEX_INVOICE_NUMBER = "[^\\.^a-z^A-Z^0-9^_^-]";
    
    

}

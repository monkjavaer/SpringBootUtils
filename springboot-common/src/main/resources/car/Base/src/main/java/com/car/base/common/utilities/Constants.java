package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

public class Constants {

	public static final String BEHAVIOR_ANALYSIS_CONFIG_PATH = "/mnt/hdfs/data/analysis/conf/";
	public static final String BEHAVIOR_ANALYSIS_IMAGE_PATH = "/mnt/hdfs/data/analysis/image/";

    //Used by pdf
	private static final String RELATIVE_CHECK_ACTA_PATH = "/templates/acta-check.pdf";
	private static final String RELATIVE_INSTALL_ACTA_TAXI_PATH = "/templates/acta-install-taxi.pdf";
	private static final String RELATIVE_INSTALL_ACTA_AMBULANCE_PATH = "/templates/acta-install-ambulance.pdf";
	private static final String RELATIVE_INSTALL_ACTA_BUS_PATH = "/templates/acta-install-bus.pdf";
	private static final String RELATIVE_REMOVE_ACTA_PATH = "/templates/acta-deinstall.pdf";
	private static final String RELATIVE_REPLACE_ACTA_PATH = "/templates/acta-replace.pdf";
	private static final String RELATIVE_COMMITMENT_ACTA_PATH = "/templates/acta-commitment.pdf";
	private static final String RELATIVE_REPAIR_ACTA_PATH = "/templates/acta-repair.pdf";
	private static final String RELATIVE_COMMITMENT_LETTER_PATH = "/templates/commitment-letter.pdf";

    public static String CHECK_ACTA_PATH;
    public static String INSTALL_ACTA_TAXI_PATH;
    public static String INSTALL_ACTA_AMBULANCE_PATH;
    public static String INSTALL_ACTA_BUS_PATH;
    public static String REMOVE_ACTA_PATH;
    public static String REPLACE_ACTA_PATH;
    public static String COMMITMENT_ACTA_PATH;
    public static String REPAIR_ACTA_PATH;
    public static String COMMITMENT_LETTER_PATH;

    //Used by mail
    private static final String RELATIVE_REGIST_NOTICE_MAIL_PATH = "/templates/registration-notice-mail.html";
    private static final String RELATIVE_REGIST_CANCEL_MAIL_PATH = "/templates/registration-cancel-mail.html";
    private static final String RELATIVE_LOW_INVENTORY_MAIL_PATH = "/templates/low-inventory-notice-mail.html";
    private static final String RELATIVE_MAIL_LOGO_PATH = "/templates/logo.jpg";

    public static String REGIST_NOTICE_MAIL_PATH;
    public static String REGIST_CANCEL_MAIL_PATH;
    public static String LOW_INVENTORY_MAIL_PATH;
    public static String MAIL_LOGO_PATH;

    //Used by sms
    private static final String RELATIVE_REGIST_NOTICE_SMS_PATH = "/templates/registration-notice-sms.txt";
    private static final String RELATIVE_REGIST_CANCEL_SMS_PATH = "/templates/registration-cancel-sms.txt";


    public static String REGIST_NOTICE_SMS_PATH;
    public static String REGIST_CANCEL_SMS_PATH;

    //Used by excel
    private static final String RELATIVE_CHECKLIST_AMBULANCE_PATH = "/templates/checklist-ambulance.xlsx";
    private static final String RELATIVE_CHECKLIST_BUS_INTERCANTONAL_PATH = "/templates/checklist-bus-intercantonal.xlsx";
    private static final String RELATIVE_CHECKLIST_BUS_INTERPROVINCIAL_PATH = "/templates/checklist-bus-interprovincial.xlsx";
    private static final String RELATIVE_CHECKLIST_BUS_URBANO_PATH = "/templates/checklist-bus-urbano.xlsx";
    private static final String RELATIVE_CHECKLIST_TAXI_PATH = "/templates/checklist-taxi.xlsx";

    public static String CHECKLIST_AMBULANCE_PATH;
    public static String CHECKLIST_BUS_INTERCANTONAL_PATH;
    public static String CHECKLIST_BUS_INTERPROVINCIAL_PATH;
    public static String CHECKLIST_BUS_URBANO_PATH;
    public static String CHECKLIST_TAXI_PATH;

    //Used by doc
    private static final String	RELATIVE_MANUAL_INSTALLATION_SCHEMES_AMBULANCE_PATH = "/templates/manual-installation-schemes-ambulance.doc";
    private static final String	RELATIVE_MANUAL_INSTALLATION_SCHEMES_BUS_PATH = "/templates/manual-installation-schemes-bus.doc";
    private static final String	RELATIVE_MANUAL_INSTALLATION_SCHEMES_BUS_II_PATH = "/templates/manual-installation-schemes-bus-ii.doc";
    private static final String	RELATIVE_MANUAL_INSTALLATION_SCHEMES_FLOTA_PATH = "/templates/manual-installation-schemes-flota.doc";
    private static final String	RELATIVE_MANUAL_INSTALLATION_SCHEMES_TAXI_PATH = "/templates/manual-installation-schemes-taxi.doc";
    private static final String	RELATIVE_MANUAL_INSTRUCTION_AMBULANCE_PATH = "/templates/manual-instruction-ambulance.doc";
    private static final String	RELATIVE_MANUAL_INSTRUCTION_BUS_PATH = "/templates/manual-instruction-bus.doc";
    private static final String	RELATIVE_MANUAL_INSTRUCTION_BUS_II_PATH = "/templates/manual-instruction-bus-ii.doc";
    private static final String	RELATIVE_MANUAL_INSTRUCTION_FLOTA_PATH = "/templates/manual-instruction-flota.doc";
    private static final String	RELATIVE_MANUAL_INSTRUCTION_TAXI_PATH = "/templates/manual-instruction-taxi.doc";

    public static String MANUAL_INSTALLATION_SCHEMES_AMBULANCE_PATH;
    public static String MANUAL_INSTALLATION_SCHEMES_BUS_PATH;
    public static String MANUAL_INSTALLATION_SCHEMES_BUS_II_PATH;
    public static String MANUAL_INSTALLATION_SCHEMES_FLOTA_PATH;
    public static String MANUAL_INSTALLATION_SCHEMES_TAXI_PATH;
    public static String MANUAL_INSTRUCTION_AMBULANCE_PATH;
    public static String MANUAL_INSTRUCTION_BUS_PATH;
    public static String MANUAL_INSTRUCTION_BUS_II_PATH;
    public static String MANUAL_INSTRUCTION_FLOTA_PATH;
    public static String MANUAL_INSTRUCTION_TAXI_PATH;

    static {
    	//Constants obj = new Constants();
    	String osName = System.getProperty("os.name").toLowerCase();
    	if(osName.indexOf("win") > -1) {
        	CHECK_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_CHECK_ACTA_PATH).toString().replace("file:/", "");
        	INSTALL_ACTA_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_INSTALL_ACTA_TAXI_PATH).toString().replace("file:/", "");
        	INSTALL_ACTA_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_INSTALL_ACTA_AMBULANCE_PATH).toString().replace("file:/", "");
        	INSTALL_ACTA_BUS_PATH = Constants.class.getResource(Constants.RELATIVE_INSTALL_ACTA_BUS_PATH).toString().replace("file:/", "");
        	REMOVE_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_REMOVE_ACTA_PATH).toString().replace("file:/", "");
        	REPLACE_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_REPLACE_ACTA_PATH).toString().replace("file:/", "");
        	COMMITMENT_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_COMMITMENT_ACTA_PATH).toString().replace("file:/", "");
        	REPAIR_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_REPAIR_ACTA_PATH).toString().replace("file:/", "");
        	COMMITMENT_LETTER_PATH = Constants.class.getResource(Constants.RELATIVE_COMMITMENT_LETTER_PATH).toString().replace("file:/", "");

        	REGIST_NOTICE_MAIL_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_NOTICE_MAIL_PATH).toString().replace("file:/", "");
        	REGIST_CANCEL_MAIL_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_CANCEL_MAIL_PATH).toString().replace("file:/", "");
        	LOW_INVENTORY_MAIL_PATH = Constants.class.getResource(Constants.RELATIVE_LOW_INVENTORY_MAIL_PATH).toString().replace("file:/", "");
        	MAIL_LOGO_PATH = Constants.class.getResource(Constants.RELATIVE_MAIL_LOGO_PATH).toString().replace("file:/", "");

        	REGIST_NOTICE_SMS_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_NOTICE_SMS_PATH).toString().replace("file:/", "");
        	REGIST_CANCEL_SMS_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_CANCEL_SMS_PATH).toString().replace("file:/", "");

        	CHECKLIST_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_AMBULANCE_PATH).toString().replace("file:/", "");
        	CHECKLIST_BUS_INTERCANTONAL_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_BUS_INTERCANTONAL_PATH).toString().replace("file:/", "");
        	CHECKLIST_BUS_INTERPROVINCIAL_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_BUS_INTERPROVINCIAL_PATH).toString().replace("file:/", "");
        	CHECKLIST_BUS_URBANO_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_BUS_URBANO_PATH).toString().replace("file:/", "");
        	CHECKLIST_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_TAXI_PATH).toString().replace("file:/", "");

        	MANUAL_INSTALLATION_SCHEMES_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_AMBULANCE_PATH).toString().replace("file:/", "");
        	MANUAL_INSTALLATION_SCHEMES_BUS_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_BUS_PATH).toString().replace("file:/", "");
        	MANUAL_INSTALLATION_SCHEMES_BUS_II_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_BUS_II_PATH).toString().replace("file:/", "");
        	MANUAL_INSTALLATION_SCHEMES_FLOTA_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_FLOTA_PATH).toString().replace("file:/", "");
        	MANUAL_INSTALLATION_SCHEMES_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_TAXI_PATH).toString().replace("file:/", "");
        	MANUAL_INSTRUCTION_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_AMBULANCE_PATH).toString().replace("file:/", "");
        	MANUAL_INSTRUCTION_BUS_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_BUS_PATH).toString().replace("file:/", "");
        	MANUAL_INSTRUCTION_BUS_II_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_BUS_II_PATH).toString().replace("file:/", "");
        	MANUAL_INSTRUCTION_FLOTA_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_FLOTA_PATH).toString().replace("file:/", "");
        	MANUAL_INSTRUCTION_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_TAXI_PATH).toString().replace("file:/", "");


    	} else  {//if(osName.indexOf("linux") > -1 || osName.indexOf("unix") > -1)
        	CHECK_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_CHECK_ACTA_PATH).toString().replace("file:", "");
        	INSTALL_ACTA_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_INSTALL_ACTA_TAXI_PATH).toString().replace("file:", "");
        	INSTALL_ACTA_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_INSTALL_ACTA_AMBULANCE_PATH).toString().replace("file:", "");
        	INSTALL_ACTA_BUS_PATH = Constants.class.getResource(Constants.RELATIVE_INSTALL_ACTA_BUS_PATH).toString().replace("file:", "");
        	REMOVE_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_REMOVE_ACTA_PATH).toString().replace("file:", "");
        	REPLACE_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_REPLACE_ACTA_PATH).toString().replace("file:", "");
        	COMMITMENT_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_COMMITMENT_ACTA_PATH).toString().replace("file:", "");
        	REPAIR_ACTA_PATH = Constants.class.getResource(Constants.RELATIVE_REPAIR_ACTA_PATH).toString().replace("file:", "");
        	COMMITMENT_LETTER_PATH = Constants.class.getResource(Constants.RELATIVE_COMMITMENT_LETTER_PATH).toString().replace("file:", "");

        	REGIST_NOTICE_MAIL_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_NOTICE_MAIL_PATH).toString().replace("file:", "");
        	REGIST_CANCEL_MAIL_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_CANCEL_MAIL_PATH).toString().replace("file:", "");
        	LOW_INVENTORY_MAIL_PATH = Constants.class.getResource(Constants.RELATIVE_LOW_INVENTORY_MAIL_PATH).toString().replace("file:", "");
        	MAIL_LOGO_PATH = Constants.class.getResource(Constants.RELATIVE_MAIL_LOGO_PATH).toString().replace("file:", "");

        	REGIST_NOTICE_SMS_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_NOTICE_SMS_PATH).toString().replace("file:", "");
        	REGIST_CANCEL_SMS_PATH = Constants.class.getResource(Constants.RELATIVE_REGIST_CANCEL_SMS_PATH).toString().replace("file:", "");

        	CHECKLIST_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_AMBULANCE_PATH).toString().replace("file:", "");
        	CHECKLIST_BUS_INTERCANTONAL_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_BUS_INTERCANTONAL_PATH).toString().replace("file:", "");
        	CHECKLIST_BUS_INTERPROVINCIAL_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_BUS_INTERPROVINCIAL_PATH).toString().replace("file:", "");
        	CHECKLIST_BUS_URBANO_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_BUS_URBANO_PATH).toString().replace("file:", "");
        	CHECKLIST_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_CHECKLIST_TAXI_PATH).toString().replace("file:", "");

        	MANUAL_INSTALLATION_SCHEMES_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_AMBULANCE_PATH).toString().replace("file:", "");
        	MANUAL_INSTALLATION_SCHEMES_BUS_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_BUS_PATH).toString().replace("file:", "");
        	MANUAL_INSTALLATION_SCHEMES_BUS_II_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_BUS_II_PATH).toString().replace("file:", "");
        	MANUAL_INSTALLATION_SCHEMES_FLOTA_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_FLOTA_PATH).toString().replace("file:", "");
        	MANUAL_INSTALLATION_SCHEMES_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTALLATION_SCHEMES_TAXI_PATH).toString().replace("file:", "");
        	MANUAL_INSTRUCTION_AMBULANCE_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_AMBULANCE_PATH).toString().replace("file:", "");
        	MANUAL_INSTRUCTION_BUS_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_BUS_PATH).toString().replace("file:", "");
        	MANUAL_INSTRUCTION_BUS_II_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_BUS_II_PATH).toString().replace("file:", "");
        	MANUAL_INSTRUCTION_FLOTA_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_FLOTA_PATH).toString().replace("file:", "");
        	MANUAL_INSTRUCTION_TAXI_PATH = Constants.class.getResource(Constants.RELATIVE_MANUAL_INSTRUCTION_TAXI_PATH).toString().replace("file:", "");

    	}
    	//System.out.println("***********" + REGIST_NOTICE_MAIL_PATH);

    }

    public static final String getTmpPath() {
		String tmpPath = System.getProperty("java.io.tmpdir");
		if((tmpPath.charAt(tmpPath.length() - 1) != '/') && (tmpPath.charAt(tmpPath.length() - 1) != '\\') ) {
			tmpPath += '/';
		}
		return tmpPath;
	}

    //used by websocket
    public static final String WEBSOCKET_USERNAME = "websocket.username";  
    
    public static final String WEBSOCKET_CONTEXT = "websocket.request.context";
    //
	public static final String OMS_EXCHANGE_NAME = "OMS_EXCHANGE";
	
	public static final String OM_EXCHANGE_NAME = "OM_EXCHANGE";
	
	public static final String WS_EXCHANGE_NAME = "WS_EXCHANGE";
	
	public static final String BR_EXCHANGE_NAME = "BR_EXCHANGE";
	
	public static final String U2S_EXCHANGE_NAME = "U2S_EXCHANGE";
	
	public static final String QUEUE_NAME_PREFIX = "QUEUE_";
	
	public static final String OMS_MESSAGE_QUEUE_NAME = "messageQueue";

	public static final String WEBSOCKET_INITIAL_TYPE = "initial";
	
	public static final String WEBSOCKET_INSTANT_TYPE = "instant";
	
	public static final String WEBSOCKET_COUNT_TYPE = "count";
	
	public static final String WEBSOCKET_HISTORY_TYPE = "history";
	
	public static final String WEBSOCKET_OPERATION_SUBTYPE = "operation";
	
	public static final String WEBSOCKET_INVENTORY_SUBTYPE = "inventory";
	
	public static final String WEBSOCKET_OTHER_SUBTYPE = "other";
	
	public static final String WEBSOCKET_MARK_READ_COMMAND = "markRead";
	
	public static final String AUTHORITY_PROPERTY_PREFIX = "authority";
	
	
    
    public static void main(String[]args) {
    	System.out.println(Constants.CHECK_ACTA_PATH);
    	System.out.println(Constants.INSTALL_ACTA_TAXI_PATH);
    	System.out.println(Constants.INSTALL_ACTA_AMBULANCE_PATH);
    	System.out.println(Constants.INSTALL_ACTA_BUS_PATH);
    	System.out.println(Constants.REMOVE_ACTA_PATH);
    	System.out.println(Constants.REPAIR_ACTA_PATH);
    	System.out.println(Constants.COMMITMENT_LETTER_PATH);
    }
    
    
}

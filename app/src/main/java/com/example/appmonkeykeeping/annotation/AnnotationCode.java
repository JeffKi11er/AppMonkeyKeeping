package com.example.appmonkeykeeping.annotation;

public class AnnotationCode {
    public static final String TOTAL_AMOUNT_IN_USING = "total_using";
    public static final String UNIT = "_unit";
    public static final String requestChoose = "please choose one of categories";
    public static final String ARRAY_MONEY_SAVING = "money_array_saving";
    public static final String SHARED_TABLE = "sharedTable";
    public static final String MONKEY_TASK = "dateSchedule";
    public static final String DATE_FORMAT = "yy/MM/dd HH:mm:ss";
    public static final String INSERT_SHOWING_FORMAT = "EEEE MMMM dd, yyyy";
    public static final int REQUEST_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 103;
    public static final int WRITE_STORAGE_REQUEST_CODE = 102;
    public static final String[]typesOfRecording = new String[]{"outcome","income","lend","borrow","transfer"};
    public static enum Categories{
        Lunch,
        Internet,
        Gas,
        Groceries,
        Breakfast,
        Electricity,
        Transport,
        Education,
        Save,
        Give,
        Finance,
        Play,
        Income
    }
    public static final String INSERT_OUTCOME_DATE = "INSERT_OUTCOME_DATE";
    public static final String INSERT_OUTCOME_LOCATION = "INSERT_OUTCOME_LOCATION";
    public static final String INSERT_OUTCOME_MAIN_MONEY = "INSERT_OUTCOME_MAIN_MONEY";
    public static final String INSERT_OUTCOME_COMMENT = "INSERT_OUTCOME_COMMENT";
    public static final String INSERT_OUTCOME_PERIOD = "INSERT_OUTCOME_PERIOD";
}

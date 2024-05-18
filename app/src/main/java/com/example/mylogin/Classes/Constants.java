package com.example.mylogin.Classes;

public class Constants {

    private static final String ROOT_URL = "http://192.168.1.109/database/";

    public static final String  URL_REGISTER = ROOT_URL + "RegisterUser.php";

    public static final String  URL_LOGIN = ROOT_URL + "userLogin.php";

    public static final String URL_NURSES = ROOT_URL + "SelectNurses.php";

    public static final String URL_POST_APPOINTMENT = ROOT_URL + "Post_Appointment.php";

    public static final String URL_GET_KIDS = ROOT_URL + "SelectKids.php";

    public static final String URL_GET_OF_ClIENT = ROOT_URL + "SelectKids_client.php?client_id= ";

    public static final String URL_GET_APPOINTMENTS_FOR_NURSE = ROOT_URL + "Get_Appointment_Nurse.php?nurse_id=";

    public static final String URL_GET_APPOINTMENTS = ROOT_URL + "Get_Appointment_clientusername.php";

    public static final String URL_UPDATE_APPOINTMENT_STATUS = ROOT_URL + "Approve_Appointment.php?ID= " ;

    public static final String URL_DELETE_APPOINTMENT = ROOT_URL + "Delete_Appointment.php?ID=";


    public static final String URL_VALID_APPOINTMETNS_ONLY = ROOT_URL + "Get_Valid_Appointments_Only.php?Nurse_ID=";

    public static final String URL_SELECT_KID_FOR_VACCINE = ROOT_URL + "Select_Certain_Kid.php?ID=";

    public static final String URL_GET_Vaccine = ROOT_URL + "Get_Vaccine.php";

    public static final String URL_Post_Kid = ROOT_URL + "Add_kid_first.php";

    public static final String  URL_GET_Clients  =  ROOT_URL+ "SelectClients.php";

    public static final String  URL_GET_VACCINES_FOR_KID  =  ROOT_URL+ "Get_Vaccine_For_Kid.php?ID=";

    public static final String  URL_UPDATE_KID  =  ROOT_URL+ "Update_Kid_infos.php";


    public static final String URL_GET_VALID_APP_USER = ROOT_URL+ "Get_Valid_Appointment_User.php?Client_ID=";

}

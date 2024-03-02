package test;

import apiUtils.MailerAPI;
import entities.User;
import entities.UserRole;
import services.User.LevelService;
import services.User.UserService;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class UserMain {
    public static void main(String[] args) throws MessagingException {
        MailerAPI.sendMail("VisionSignAcademy@outlook.com","Azerty123!","iyedguezmir11@gmail.com"); ;

}}

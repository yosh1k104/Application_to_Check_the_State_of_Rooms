/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wip;

// Java縺ｧGmail縺九ｉ繝｡繝ｼ繝ｫ繧抵ｿｽ?菫｡縺吶ｋ繧ｵ繝ｳ繝励Ν??avaMail菴ｿ逕ｨ?�ｽimport java.util.Date;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

//import org.sunspotworld.Sample;

public class JavaMailSend {
	// Sample sample = new Sample();
	private String text = "";

	public void send(String address, String changeText) {
		// public static void main(String[] argv) {
		try {
			// 繝励Ο繝代ユ繧｣縺ｮ險ｭ�ｽ?
			Properties props = System.getProperties();
			// 繝帙せ�ｽ?
			props.put("mail.smtp.host", "smtp.gmail.com");
			// 隱崎ｨｼ?�ｽ縺吶ｋ�ｽ?
			props.put("mail.smpt.auth", "true");
			// 繝晢ｿｽ?繝域欠螳夲ｼ医し繝悶Α�ｽ?�ｽ�ｽ繝ｧ繝ｳ繝晢ｿｽ?繝茨ｿｽ?
			props.put("mail.smtp.port", "587");
			// STARTTLS縺ｫ繧医ｋ證怜捷蛹厄ｼ医☆繧具ｿｽ?
			props.put("mail.smtp.starttls.enable", "true");

			// 繧ｻ�ｽ?�ｽ�ｽ繝ｧ繝ｳ縺ｮ蜿厄ｿｽ?
			Session session = Session.getInstance(props);

			// MimeMessage縺ｮ蜿門ｾ励→險ｭ�ｽ?
			Message msg = new MimeMessage(session);
			// 騾∽ｿ｡�ｽ?�ｽ�ｽ�ｽ?
			msg.setFrom(new InternetAddress("yosh1k104.yk@gmail.com"));
			// 螳幢ｿｽ?險ｭ�ｽ?
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(address, false));
			// 繧ｿ繧､繝医Ν險ｭ�ｽ?
			msg.setSubject("室内状況のおしらせ");

                        //if(sample.getLight() > 0){
//                        text = "�ｽ�ｽ�ｽﾔ擾ｿｽﾍ鯉ｿｽ�ｽﾝ空いてゑｿｽ�ｽﾜゑｿｽ�ｽﾌで、�ｽg�ｽp�ｽﾂ能�ｽﾅゑｿｽ�ｽB";
                        //}else {
                          //  text = "�ｽc�ｽO�ｽﾈゑｿｽ�ｽ迺難ｿｽﾔ擾ｿｽ�ｽ�ｽg�ｽp�ｽ�ｽ�ｽ驍ｱ�ｽﾆはでゑｿｽ�ｽﾜゑｿｽ�ｽ�ｽB";
                        //}
//                        setText(changeText);
                        text = changeText;


                        // 譛ｬ�ｽ?�ｽ�ｽ�ｽ?
			msg.setText(text);
			// 騾∽ｿ｡譌･譎りｨｭ�ｽ?
			msg.setSentDate(new Date());

			// 騾∽ｿ｡
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			try {
				t.connect("smtp.gmail.com", "yosh1k104.yk", "19921104");
				t.sendMessage(msg, msg.getAllRecipients());
			} finally {
				t.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//        public void setText(String changeText){
//            text = changeText;
//        }
}

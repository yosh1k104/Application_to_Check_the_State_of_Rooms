/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wip;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 * シンプルなメール受信サンプル。
 */
public class JavaMailReceive {

    private String address = "";
    private Date date;
    private int length;

    /*
     * public static void main(final String[] args) {
     *
     * while (true) { System.out.println("メール受信: 開始");
     *
     * new JavaMailReceive().process();
     *
     * System.out.println("メール受信: 終了"); } }
     */
    public void process() {
        final Properties props = new Properties();

        // 基本情報。ここでは gmailへの接続例を示します。
        props.setProperty("mail.pop3.host", "pop.gmail.com");
        props.setProperty("mail.pop3.port", "995");

        // タイムアウト設定
        props.setProperty("mail.pop3.connectiontimeout", "60000");
        props.setProperty("mail.pop3.timeout", "60000");

        // SSL関連設定
        props.setProperty("mail.pop3.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        final Session session = Session.getInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yosh1k104.yk@gmail.com",
                        "19921104");
            }
        });

        // デバッグを行います。標準出力にトレースが出ます。
        session.setDebug(true);

        Store store = null;
        try {
            try {
                store = session.getStore("pop3");
                // store = session.getStore("imaps");
            } catch (NoSuchProviderException e) {
//                System.out.println("メール受信: 指定プロバイダ[pop3]の取得に失敗しました。"
//                        + e.toString());
                return;
            }

            try {
                // store.connect("imap.gmail.com", "YOURMAILID@gmail.com",
                // "Zptij1j4");
                store.connect();
            } catch (AuthenticationFailedException e) {
//                System.out.println("メール受信: サーバ接続時に認証に失敗しました。" + e.toString());
                return;
            } catch (MessagingException e) {
//                System.out.println("メール受信: サーバ接続に失敗しました。" + e.toString());
                return;
            }

            Folder folder = null;
            try {
                try {
                    // INBOXは予約語です。
                    folder = store.getFolder("INBOX");
                    // folder = store.getFolder("All Mail");
                    // folder = store.getFolder("[Gmail]").getFolder("重要");
                    // folder =
                    // store.getFolder("[Gmail]").getFolder("All Mail");

                } catch (MessagingException e) {
//                    System.out.println("メール受信: INBOXフォルダ取得に失敗しました。"
//                            + e.toString());
                    return;
                }
                try {
                    folder.open(Folder.READ_ONLY);
                } catch (MessagingException e) {
//                    System.out.println("メール受信: フォルダオープンに失敗しました。" + e.toString());
                    return;
                }

                // メッセージ一覧を取得
                try {
                    final Message messages[] = folder.getMessages();

                    // for (int index = 0; index < messages.length; index++) {
                    // final Message message = messages[index];
                    length = messages.length;
                    final Message message = messages[messages.length - 1];
                    // final Message message = messages[0];

//                    System.out.println("length:" + messages.length);

                    // このAPI利用範囲であれば TOPコマンド止まりで、RETRコマンドは送出されない。

//                    System.out.println("Subject: " + message.getSubject());
//                    System.out.println("  Date: "
//                            + message.getSentDate().toString());

                    date = message.getSentDate();

                    // TODO 0番目の配列アクセスをおこなっている点に注意。
                    final InternetAddress addrFrom = (InternetAddress) message.getFrom()[0];

                    address = addrFrom.getAddress();

                    System.out.println("  From: " + addrFrom.getAddress());
                    // MimeUtility.decodeText(addrFrom.getPersonal())

                    // To: を表示。
                    final Address[] addrsTo = message.getRecipients(RecipientType.TO);
                    for (int loop = 0; loop < addrsTo.length; loop++) {
                        final InternetAddress addrTo = (InternetAddress) addrsTo[loop];
//                        System.out.println("  To: " + addrTo.getAddress());
                    }

                    // Cc:は割愛

                    // なお、例えば message.getContentを呼び出すと RETRコマンドが送出される。
                    // }
                } catch (MessagingException e) {
//                    System.out.println("メール受信: メッセージ取得に失敗しました。" + e.toString());
                    return;
                }
            } finally {
                if (folder != null) {
                    try {
                        folder.close(false);
                    } catch (MessagingException e) {
//                        System.out.println("メール受信: フォルダクローズに失敗しました。"
//                                + e.toString());
                    }
                }
            }
        } finally {
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
//                    System.out.println("メール受信: サーバ切断に失敗しました。" + e.toString());
                }
            }
        }
    }

    public String getAddress() {
        return address;
    }

    public int getLength() {
        return length;
    }

    public Date getDate() {
        return date;
    }
}

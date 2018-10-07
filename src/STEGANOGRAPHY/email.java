package STEGANOGRAPHY;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class email extends JFrame{
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	
	public email() {
		super("STEGANOGRAPHY");
		setSize(500,400);
		setLocationRelativeTo(this);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel lblSendersMail = new JLabel("Sender's mail");
		
		JLabel lblPassword = new JLabel("Password");
		
		JLabel lblRecipientMail = new JLabel("Recipient mail");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblSubject = new JLabel("Subject");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JLabel lblBody = new JLabel("Body");
		
		JTextArea textArea = new JTextArea();
		
		JButton btnSend = new JButton("SEND");
		
		passwordField = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(43, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPassword)
								.addComponent(lblSendersMail)
								.addComponent(lblRecipientMail)
								.addComponent(lblSubject)
								.addComponent(lblBody))
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField_3)
								.addComponent(textField)
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
								.addComponent(passwordField)
								.addComponent(textArea))
							.addGap(38))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnSend)
							.addGap(165))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSendersMail)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRecipientMail)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubject)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBody)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnSend)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		setVisible(true);
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// SMTP info
				String host = "smtp.gmail.com";
				String port = "587";
				String mailFrom = textField.getText();
				String password = new String(passwordField.getPassword());

				// message info
				String mailTo = textField_2.getText();
				String subject = textField_3.getText();
				String message = textArea.getText();
				String filePath = steganographyview.depath;
				
				
				

				try {
					sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
						subject, message, filePath);
					JOptionPane.showMessageDialog(new encode(), "Sent!!","Success",JOptionPane.INFORMATION_MESSAGE);
					dispose();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(new decode(), "Error!!", "Error!",JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
				
			
		});
	}
	
	public static void sendEmailWithAttachments(String host, String port,
			final String userName, final String password, String toAddress,
			String subject, String message, String filePath)
			throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
		

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}
}
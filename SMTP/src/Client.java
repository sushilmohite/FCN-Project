import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Client {

	private JFrame frame;
	private JFrame authenticator;
	
	private JLabel receiverlbl;
	private JLabel subjectlbl;
	
	private JLabel receiver;
	private JLabel subject;
	private JLabel content;
	
	private DefaultListModel<Email> listModel;

	private String username;
	private String password;

	private static final String FROM_STR    = "From        :";
	private static final String TO_STR      = "To          :";
	private static final String SUBJECT_STR = "Subject     :";

	public Client() {

		
		authenticator = new JFrame("Login");
		
		Component authenticatorContents = createAuthenticatorComponents();
		authenticator.getContentPane().add(authenticatorContents);
		
		authenticator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		authenticator.pack();
		authenticator.setLocationRelativeTo(null);
		authenticator.setVisible(true);
		
	}

	private Component createAuthenticatorComponents() {
		String lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {

		}

		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		// Add Username
		JPanel uPanel = new JPanel();
		uPanel.setLayout(new BoxLayout(uPanel, BoxLayout.X_AXIS));
		
		JTextField dummy = new JTextField("123456789012345@aaaaaaaa.com");
		
		JLabel unamelbl = new JLabel("Username");
		unamelbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		uPanel.add(unamelbl);
		final JTextField uname = new JTextField();
		uname.setPreferredSize(dummy.getPreferredSize());
		uPanel.add(uname);

		main.add(uPanel);
		
		// Add Password
		JPanel pPanel = new JPanel();
		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.X_AXIS));
		
		JLabel passlbl = new JLabel("Password");
		passlbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		pPanel.add(passlbl);
		final JPasswordField pass = new JPasswordField();
		pass.setPreferredSize(dummy.getPreferredSize());
		pPanel.add(pass);
		
		main.add(pPanel);
		
		// Add buttons
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.X_AXIS));
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username = uname.getText();
				password = pass.getText();
				
				if(isAuthenticated(username, password)) {
					authenticator.dispose();
					frame = new JFrame("Email Client");
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					Component contents = createComponents();
					frame.getContentPane().add(contents);
			
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Username / password");
				}
			}
		});
		bPanel.add(login);
		
		main.add(bPanel);
		
		return main;
	}

	public boolean isAuthenticated(String username, String password) {
		if(username.equals("Kedar") && password.equals("Kedar")) {
			return true;
		} else {
			return false;
		}
	}

	private Component createComponents() {
		
		String lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {

		}

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		Border border = BorderFactory.createEtchedBorder();
		main.setBorder(new EmptyBorder(10, 10, 10, 10) );

		// Add hello label
		JPanel helloLabels = new JPanel();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		// constraints.weightx = 10;
		constraints.weighty = 2;
		main.add(helloLabels, constraints); 

		JLabel helloMessage = new JLabel("Hello, " + getUserName() + "!");
		helloMessage.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		helloLabels.add(helloMessage);

		// Add buttons
		JPanel buttons = new JPanel();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		// constraints.weightx = 5;
		constraints.weighty = 2;

		JButton dummy1 = new JButton("XXXXXXXX");
		JButton compose = new JButton("Compose");
		compose.setPreferredSize(dummy1.getPreferredSize());
		compose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		buttons.add(compose);
		JButton reply = new JButton("Reply");
		reply.setPreferredSize(dummy1.getPreferredSize());
		reply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		buttons.add(reply);
		JButton forward = new JButton("Forward");
		forward.setPreferredSize(dummy1.getPreferredSize());
		forward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		buttons.add(forward);
		JButton delete = new JButton("Delete");
		delete.setPreferredSize(dummy1.getPreferredSize());
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		buttons.add(delete);
		JButton refresh = new JButton("Refresh");
		refresh.setPreferredSize(dummy1.getPreferredSize());
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		buttons.add(refresh);

		main.add(buttons, constraints);

		// Add List of emails and details view
		listModel = new DefaultListModel<Email>();
		getEmail();
		
		JScrollPane msgPane = new JScrollPane();

		msgPane.setBorder(BorderFactory.createTitledBorder(border, "Inbox"));

		// list of emails
		final JList<Email> listOfEmails = new JList<>(listModel);
		listOfEmails.setCellRenderer(new CustomCellRenderer());
		listOfEmails.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		    	Email email = listOfEmails.getSelectedValue();
		    	setContent("" + email.getHTMLContent());
		    	setReceiver(email.getFrom());
		    	setSubject(email.getSubject());
		    }
		});
		
		msgPane.setViewportView(listOfEmails);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 15;
		constraints.weighty = 96;

		main.add(msgPane, constraints);

		JPanel detailsView = new JPanel();
		detailsView.setLayout(new GridBagLayout());
		detailsView.setBorder(BorderFactory.createTitledBorder(border, "Details"));
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 85;
		constraints.weighty = 94;

		main.add(detailsView, constraints);
		
		// Add From line
		receiverlbl = new JLabel(FROM_STR);
		receiverlbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 5;
		constraints.weighty = 2;
		detailsView.add(receiverlbl, constraints);
		
		receiver = new JLabel("Kedar");
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 95;
		constraints.weighty = 2;
		detailsView.add(receiver, constraints);
		
		// Add Subject line
		subjectlbl = new JLabel(SUBJECT_STR);
		subjectlbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 5;
		constraints.weighty = 2;
		detailsView.add(subjectlbl, constraints);
		
		subject = new JLabel("Hello");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 95;
		constraints.weighty = 2;
		detailsView.add(subject, constraints);		
		
		// Add content
		content = new JLabel();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTH;
//		constraints.weightx = 90;
		constraints.weighty = 96;
		JScrollPane contentPane = new JScrollPane();
		contentPane.setViewportView(content);
		detailsView.add(contentPane, constraints);
		
		return main;
	}
	
	private String getUserName() {
		return this.username;
	}

	public void setReceiver(String username) {
		this.receiver.setText(username);
	}
	
	public void setSubject(String subject) {
		this.subject.setText(subject);
	}
	
	public void setContent(String stringContent) {
		this.content.setText(stringContent);
	}

	public void getEmail() {
		listModel.clear();
		for(int i = 0; i < 100; i++) {
			listModel.addElement(new Email("Sender" + (i + 1) * 8, "TO" + (i + 1), "" + (i + 1) * 9561, "04-12-2014", "someContent" + i));
		}
	}
	
	public static void main(String[] args) {
		new Client();
	}

}

class CustomCellRenderer extends JLabel implements ListCellRenderer {

	public CustomCellRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		if(isSelected) {
			setBackground(new Color(57, 105, 138));
			setForeground(Color.WHITE);
		} else {
			setBackground(index % 2 == 0 ? Color.WHITE : new Color(220, 233, 241));
			setForeground(Color.BLACK);
		}
		return this;
	}
}
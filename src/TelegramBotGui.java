//Imports are listed in full to show what's being used 
//could just import javax.swing.* and java.awt.* etc.. 
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JList; 
import java.awt.BorderLayout; 
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;  
public class TelegramBotGui {  //Note: Typically the main method will be in a 
	//separate class. As this is a simple one class 
	//example it's all in the one class. 
	
	
	private SeleniumUtility seleniumUtility;
	
	private ArrayList<String> responsesArrayList = new ArrayList<String>();
	
	public static void main(String[] args) {  
		
		new TelegramBotGui(); 
		
		
	}  
	
	
	public TelegramBotGui() { 
		responsesArrayList.add("nice!");
		responsesArrayList.add("cool!");
		responsesArrayList.add("i like it!");
		responsesArrayList.add("awesome man!");
		
		JFrame guiFrame = new JFrame();  
		
		//make sure the program exits when the frame closes 
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		guiFrame.setTitle("Telegram Bot"); 
		guiFrame.setSize(500,200);  
		
		//This will center the JFrame in the middle of the screen 
		guiFrame.setLocationRelativeTo(null);  
		
		
		//phoene number panel
		final JPanel phonenumberpanel = new JPanel(); 
		JLabel phonenumberlabel = new JLabel("Enter your telephone number:"); 
		JTextField phonenumberarea = new JTextField(20);
		phonenumberpanel.add(phonenumberlabel); 
		phonenumberpanel.add(phonenumberarea);  
		
		
		//verification number panel
		final JPanel verificationnumberpanel = new JPanel(); 
		verificationnumberpanel.setVisible(false); 
		JLabel verificationnumberlabel = new JLabel("Enter your verification code (sent by telegram):"); 
		JTextField verificationnumberarea = new JTextField(20);
		verificationnumberpanel.add(verificationnumberlabel);
		verificationnumberpanel.add(verificationnumberarea);  
		
		//chat name panel
		final JPanel chatnamepanel = new JPanel(); 
		chatnamepanel.setVisible(false); 
		JLabel chatnamelabel = new JLabel("Enter the name of the chat you want to bot (dont fuck this up)"); 
		JTextField chatnamearea = new JTextField(20);
		chatnamepanel.add(chatnamelabel);
		chatnamepanel.add(chatnamearea);  
		
		JPanel logpanel = new JPanel ();
		logpanel.setLayout(new BoxLayout(logpanel, BoxLayout.Y_AXIS));
		logpanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Log Panel" ) );
		JLabel loglabel = new JLabel("Chat logs"); 
	    JTextArea display = new JTextArea ( 16, 30 );
	    display.setEditable ( false ); // set textArea non-editable
	    JScrollPane scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    logpanel.add(loglabel);
	    logpanel.add ( scroll );
		
		
	    JPanel responsespanel = new JPanel ();
	    responsespanel.setLayout(new BoxLayout(responsespanel, BoxLayout.Y_AXIS));
	    responsespanel.setBorder ( new TitledBorder ( new EtchedBorder (), "RESPONSES" ) );
		JLabel responselabel = new JLabel("Current responses"); 
		responsespanel.add(responselabel);
		String[] responses = new String[responsesArrayList.size()];
		responses = responsesArrayList.toArray(responses);
		JList<String> responseList = new JList<String>(responses);
		JScrollPane responsesScrollPane = new JScrollPane();
		responsesScrollPane.setViewportView(responseList);
		responsespanel.add(responsesScrollPane);
		JTextField addResponseField = new JTextField(20);
		addResponseField.setMaximumSize( addResponseField.getPreferredSize() );
		JButton addResponseButton = new JButton( "Add Response");  
		JButton removeResponseButton = new JButton( "Remove Selected Response");  
		
		responsespanel.add(addResponseField);
		responsespanel.add(addResponseButton);
		responsespanel.add(removeResponseButton);
		
		
		JButton vegFruitBut = new JButton( "Login");  
		JButton submitVerificationButton = new JButton( "Submit"); 
		JButton startBottingButton = new JButton( "Start Botting"); 
		
		//The ActionListener class is used to handle the 
		//event that happens when the user clicks the button. 
		//As there is not a lot that needs to happen we can  
		//define an anonymous inner class to make the code simpler. 
		vegFruitBut.addActionListener(new ActionListener() { 
			@Override public void actionPerformed(ActionEvent event) { 
				String number =phonenumberarea.getText();
				System.out.println(number);
				if(number.length()!=10){
					
				}else if(!isInteger(number)){
					
				}else { 
					System.out.println("ok");
					phonenumberpanel.setVisible(false); 
					verificationnumberpanel.setVisible(true);  
					guiFrame.remove(vegFruitBut);
					guiFrame.add(submitVerificationButton,BorderLayout.SOUTH);  
					
					seleniumUtility = new SeleniumUtility();
					seleniumUtility.connectWithPhoneNumber(number);
					seleniumUtility.setResponseList(responsesArrayList);
				}
				
				
				
				
			} 
		});  
		
		submitVerificationButton.addActionListener(new ActionListener() { 
			@Override public void actionPerformed(ActionEvent event) { 
				String number =verificationnumberarea.getText();
				System.out.println(number);
				if(number.length()!=5){
					
				}else if(!isInteger(number)){
					
				}else { 
					System.out.println("ok");
					
					verificationnumberpanel.setVisible(false);  
					guiFrame.remove(verificationnumberpanel);
					guiFrame.add(chatnamepanel, BorderLayout.CENTER); 
					chatnamepanel.setVisible(true);  
					guiFrame.remove(submitVerificationButton);
					guiFrame.add(startBottingButton,BorderLayout.SOUTH); 
					
					seleniumUtility.sendVerificationKey(number);
				}
				
				
				
				
			} 
		});  
		
		
		
		
		startBottingButton.addActionListener(new ActionListener() { 
			@Override public void actionPerformed(ActionEvent event) { 
				String chatname = chatnamearea.getText();
				if(chatname.length()>0){
					guiFrame.setSize(700,500);
					chatnamepanel.setVisible(false);  
					guiFrame.remove(chatnamepanel);
					guiFrame.add(logpanel, BorderLayout.WEST); 
					logpanel.setVisible(true);
					guiFrame.add(responsespanel,BorderLayout.EAST);
					
					
					seleniumUtility.botChatName(chatname, display);
				}
				
				
				
			} 
		});  
		
		addResponseButton.addActionListener(new ActionListener() { 
			@Override public void actionPerformed(ActionEvent event) { 
				
				if(addResponseField.getText().length()>0){
					responsesArrayList.add(addResponseField.getText());
					String[] responses = new String[responsesArrayList.size()];
					responses = responsesArrayList.toArray(responses);
					responseList.setListData(responses);
					addResponseField.setText("");
					seleniumUtility.setResponseList(responsesArrayList);
				}
				
			} 
		});  
		
		removeResponseButton.addActionListener(new ActionListener() { 
			@Override public void actionPerformed(ActionEvent event) { 
				
				if(responseList.getSelectedValue()!= null){
					responsesArrayList.remove(responsesArrayList.indexOf(responseList.getSelectedValue()));
					String[] responses = new String[responsesArrayList.size()];
					responses = responsesArrayList.toArray(responses);
					responseList.setListData(responses);
					addResponseField.setText("");
					seleniumUtility.setResponseList(responsesArrayList);
				}
				
			} 
		});  
		
		//The JFrame uses the BorderLayout layout manager. 
		//Put the two JPanels and JButton in different areas. 
		guiFrame.add(phonenumberpanel, BorderLayout.NORTH); 
		guiFrame.add(verificationnumberpanel, BorderLayout.CENTER); 
		guiFrame.add(vegFruitBut,BorderLayout.SOUTH);  
		//make sure the JFrame is visible 
		guiFrame.setVisible(true); 
		
		
	} 

	
	
	
	
	public  boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public  boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	

}


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumUtility {
	
	
	private static ArrayList<String> responsesList = new ArrayList<String>();
	
	private WebDriver driver;
	
	private Date lastDate;
	private Date lastResponse;
	
	public SeleniumUtility(){
		driver = new FirefoxDriver();
		driver.manage().window().setPosition(new Point(0,0));
		driver.manage().window().setSize(new Dimension(600,600));
		lastDate=null;
		
		responsesList.add("yea bullllZHIT!");
    	responsesList.add("za brah");
    	responsesList.add("za");
    	responsesList.add("nice!");
    	responsesList.add("fuckit");
    	responsesList.add("yo rado run?");
    	responsesList.add("im starving need some turkey chili");
    	responsesList.add("fiending to dip work");
    	responsesList.add("cha bra");
    	responsesList.add("yah wiz kaleave ya yahhh");
    	responsesList.add("yo basscenter?");
    	responsesList.add("what you niks doin this weekend");
    	responsesList.add("i could use some shaterl at the moment");
    	responsesList.add("yo discjam?");
    	responsesList.add("plese secure your own personal cash flow");
    	responsesList.add("try me try me");
    	responsesList.add("glooooooooBUP!");
    	responsesList.add("mr kmongi");
    	responsesList.add("wax on wax off");
    	responsesList.add("islanders are the best team in hockey");
    	responsesList.add("sushi?");
    	responsesList.add("i could use a brew atm");
    	responsesList.add("u guys posted?");
    	responsesList.add("jd jdj d");
    	responsesList.add("ya stook in da mood");
    	responsesList.add("howards");
    	responsesList.add("360");
    	responsesList.add("medium raw");
	}

    public  void sendEnter(WebElement e){
    	e.sendKeys(Keys.RETURN);
    }
    
    
    public  void enterAutoResponseLoop(WebDriver driver, JTextArea display){
    	
	    	
    			List<WebElement> messageDates = driver.findElements(By.className("im_message_date"));
				WebElement e = messageDates.get(messageDates.size()-1);
				
				
				String[] parts = e.getText().split(":");
				int hours = Integer.parseInt(parts[0]);
				int minutes = Integer.parseInt(parts[1]);
				int seconds = Integer.parseInt(parts[2].split(" ")[0]);
				
				if("PM".equals(parts[2].split(" ")[1])){
					hours+=12;
				}
				
				
				
				 Calendar cal = Calendar.getInstance();
				 Date currenttime = cal.getTime();
				 cal.set(Calendar.HOUR_OF_DAY, hours);
				 cal.set(Calendar.MINUTE, minutes);
				 cal.set(Calendar.SECOND, seconds);
				 
				 Date messagedate = cal.getTime();
				 
				 
				
		        if(lastDate==null){
		        	
		        	//int responseChoice = (int) (Math.random() * (responsesList.size()-1));
		        	//sendTelegramMessage(responsesList.get(responseChoice), driver);
		        	
		        	messageDates = driver.findElements(By.className("im_message_date"));
					e = messageDates.get(messageDates.size()-1);
					
					
					parts = e.getText().split(":");
					hours = Integer.parseInt(parts[0]);
					minutes = Integer.parseInt(parts[1]);
					seconds = Integer.parseInt(parts[2].split(" ")[0]);
					
					if("PM".equals(parts[2].split(" ")[1])){
						hours+=12;
					}
					cal = Calendar.getInstance();
					currenttime = cal.getTime();
					cal.set(Calendar.HOUR_OF_DAY, hours);
					cal.set(Calendar.MINUTE, minutes);
					cal.set(Calendar.SECOND, seconds);
					 
					lastDate = cal.getTime();
					lastResponse = cal.getTime();
					lastResponse.setTime(lastResponse.getTime()-60);
					
		        	
		        }else if((lastDate.getHours()!=messagedate.getHours()) || (lastDate.getMinutes()!=messagedate.getMinutes()) || (lastDate.getSeconds()!=messagedate.getSeconds())){
		        	long secondsdiff = (messagedate.getTime()-lastResponse.getTime())/1000;
		        	System.out.println("newmessageseen - lastresponse was -" + secondsdiff +" ago");
		        	display.setText(display.getText()+"new message seen - last response was -" + secondsdiff +"s ago\n");
		        	lastDate = messagedate;
		        	if(secondsdiff>60){
		        		System.out.println("replying");
		        		display.setText(display.getText()+"replying\n");
		        		lastResponse = messagedate;
			        	int responseChoice = (int) (Math.random() * (responsesList.size()-1));
			        	sendTelegramMessage(responsesList.get(responseChoice), driver);
		        	}
		        	
		        }
		        
		       // try{
		       // 	Thread.sleep(5000);
		       // }catch(Exception za){
		        //	za.printStackTrace();
		       // }
		        
			
    		
    	
    }
    
    
    public void setResponseList(ArrayList<String> responses){
    	responsesList=responses;
    }
    
    public  void sendTelegramMessage(String messageToSend,WebDriver driver ){
    	WebElement chatArea = driver.findElement(By.className("composer_rich_textarea"));
    	chatArea.sendKeys(messageToSend);
    	WebElement sendButton = driver.findElement(By.xpath("//button[@my-i18n='im_submit_message']"));
    	sendButton.click();
    	
    }
    
    
    public void connectWithPhoneNumber(String phonenumber){
    	driver.get("https://web.telegram.org/#/im");
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        WebElement phoneNumberArea = driver.findElement(By.xpath("//input[@ng-model='credentials.phone_number']"));
        
        phoneNumberArea.sendKeys(phonenumber);
        
        sendEnter(phoneNumberArea);
        
        WebElement okbutton = driver.findElement(By.className("btn-md-primary"));
        
        sendEnter(okbutton);
    	
    }
    
    public void sendVerificationKey(String verificationKey){
    	
    	
        WebElement securityCodeArea = driver.findElement(By.xpath("//input[@ng-model='credentials.phone_code']"));
        securityCodeArea.sendKeys(verificationKey);
        sendEnter(securityCodeArea);
    }
    
    
    public void botChatName(String chatName, JTextArea display){
    	List<WebElement> queryList = driver.findElements(By.xpath("//span[@my-peer-link='dialogMessage.peerID']"));
        for(WebElement e : queryList){
        	System.out.println("name "+e.getText());
        	if(chatName.equals(e.getText())){
        		System.out.println("aha");
        		e.click();
        	}
        	
        }
        
        new java.util.Timer().scheduleAtFixedRate( 
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                    	enterAutoResponseLoop(driver, display);
                    	
                    }
                }, 
                0, 500 
        );
        
        
    }
}
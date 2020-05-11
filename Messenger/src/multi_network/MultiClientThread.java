package multi_network;

import java.awt.Font;
import java.util.Scanner;

public class MultiClientThread extends Thread{
    private MultiClient mc;
   
    
    public MultiClientThread(MultiClient mc){
        this.mc = mc;
    }
    
    
    public void run(){
        String message = null;
        String[] receivedMsg = null;
        Talk talk = null;
        
        boolean isStop = false;
        while(!isStop){
            try{
                message = (String)mc.getOis().readObject();
                receivedMsg = message.split("#");
                
                talk = new Talk();
                talk.setUserId(receivedMsg[0]);
                talk.setBody(receivedMsg[1]);
                talk.setDate("2020-05-11");
                
                TalkSvc talkSvc = new TalkSvc();
                boolean insertResult = talkSvc.insertTalk(talk);

            }catch(Exception e){
                e.printStackTrace();
                isStop = true;
            }
            
            System.out.println(receivedMsg[0]+" : "+receivedMsg[1]);
//            0: id, 1: message
            
            if(receivedMsg[1].equals("exit")){
                if(receivedMsg[0].equals(mc.getId())){
                    mc.exit();
                }else{
                    mc.getJta().append(
                    receivedMsg[0] +"���� ���� �ϼ̽��ϴ�."+
                    System.getProperty("line.separator"));
                    mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                }
            }else if(receivedMsg[1].equals("change")){               
                mc.changepower=true;
                mc.getJta().append("�ٲ� ���̵� �Է��ϼ���"+ System.getProperty("line.separator"));
                String name = receivedMsg[1];
                mc.SetName(name);
            }else if(receivedMsg[1].equals("clear")){               
            	mc.Clear();
            }else if(receivedMsg[0].equals(mc.getId())){
            	mc.getJta().append(
            			receivedMsg[0] +" : "+receivedMsg[1]+
                        System.getProperty("line.separator"));
                        mc.getJta().setCaretPosition(
                            mc.getJta().getDocument().getLength());  
            	
            }else if(receivedMsg[1].equals("/r")) { //
                if (receivedMsg[2].equals(mc.getId())) { 
                    mc.getJta().append("����"+receivedMsg[0]+" : "+receivedMsg[3] + System.getProperty("line.separator"));
                 }
                 /*if (receivedMsg[0].equals(mc.getId())) {
                    mc.getJta().append("����"+receivedMsg[2]+" : "+receivedMsg[3] + System.getProperty("line.separator"));
                 }*/

            }else if(receivedMsg[0].equals("list")){            	
            	int len =receivedMsg.length-1;
            	String numStr2 = String.valueOf(len);
            	mc.getJta().append("���������ο� :"+numStr2+System.getProperty("line.separator"));
            	for(int i=0;i<receivedMsg.length;i++){
            		mc.getJta().append(receivedMsg[i]+System.getProperty("line.separator"));
            	}       
            }else if(receivedMsg[1].equals("Į��")){               
                mc.getJta().append("�� �޽����� �������մϴ�."+ System.getProperty("line.separator"));
                
            }else{               
                mc.getJta().append(
                receivedMsg[0] +" : "+receivedMsg[1]+
                System.getProperty("line.separator"));
                mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());     
            }
        }
    }
}
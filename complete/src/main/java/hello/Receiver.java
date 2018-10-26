package hello;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
       

    public void receiveMessage(byte[] message) {    	
    	System.out.println(new String(message));  
    	System.out.println("------------------------------------------------------------");     	    
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}

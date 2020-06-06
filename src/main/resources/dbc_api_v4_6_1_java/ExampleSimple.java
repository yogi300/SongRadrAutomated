import com.DeathByCaptcha.AccessDeniedException;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.DeathByCaptcha.SocketClient;
import com.DeathByCaptcha.Captcha;

import java.io.IOException;


class ExampleSimple
{
    public static void main(String[] args)
        throws Exception
    {
        // Put your DBC username & password here:
        System.out.println(args.length);
        Client client;
        // using http API
/*
        if(args.length == 3){
            client = (Client)(new HttpClient(args[0], args[1]));
        }else if(args.length == 2){
            client = (Client)(new HttpClient(args[0]));
        }else{
            System.out.println("Wrong number of arguments");
            System.out.println("You must use username/password combination");
            System.out.println("Or API key");
            return;
        }
*/
        // using sockets API
        if(args.length == 3){
            client = (Client)(new SocketClient(args[0], args[1]));
        }else if(args.length == 2){
            client = (Client)(new SocketClient(args[0]));
        }else{
            System.out.println("Wrong number of arguments");
            System.out.println("You must use username/password combination");
            System.out.println("Or API key");
            return;
        }

        client.isVerbose = true;

        try {
            try {
                System.out.println("Your balance is " + client.getBalance() + " US cents");
            } catch (IOException e) {
                System.out.println("Failed fetching balance: " + e.toString());
                return;
            }

            Captcha captcha = null;
            try {
                // Upload a CAPTCHA and poll for its status with 120 seconds timeout.
                // Put you CAPTCHA image file name, file object, input stream, or
                // vector of bytes, and optional solving timeout (in seconds) here.
                if (args.length == 3){
                    captcha = client.decode(args[2], 120);
                }else if (args.length == 2){
                    captcha = client.decode(args[1], 120);
                }else{
                    System.out.println("Failed uploading CAPTCHA - args");
                    return;    
                }
                
            } catch (IOException e) {
                System.out.println("Failed uploading CAPTCHA");
                return;
            }
            if (null != captcha) {
                System.out.println("CAPTCHA " + captcha.id + " solved: " + captcha.text);

                // Report incorrectly solved CAPTCHA if necessary.
                // Make sure you've checked if the CAPTCHA was in fact incorrectly
                // solved, or else you might get banned as abuser.
                /*try {
                    if (client.report(captcha)) {
                        System.out.println("Reported as incorrectly solved");
                    } else {
                        System.out.println("Failed reporting incorrectly solved CAPTCHA");
                    }
                } catch (IOException e) {
                    System.out.println("Failed reporting incorrectly solved CAPTCHA: " + e.toString());
                }*/
            } else {
                System.out.println("Failed solving CAPTCHA");
            }
        } catch (com.DeathByCaptcha.Exception e) {
            System.out.println(e);
        }
    }
}

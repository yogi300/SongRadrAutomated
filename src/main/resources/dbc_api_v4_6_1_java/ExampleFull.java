import com.DeathByCaptcha.AccessDeniedException;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.DeathByCaptcha.SocketClient;
import com.DeathByCaptcha.Captcha;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;


class ExampleFullDecoder implements Runnable
{
    protected Client _client = null;
    protected String _captchaFilename = null;

    ExampleFullDecoder(Client client, String captchaFilename)
    {
        this._client = client;
        this._captchaFilename = captchaFilename;
    }

    public void run()
    {
        Captcha captcha = null;

        try {
            // Put your CAPTCHA image file, file object, input stream,
            // or vector of bytes here:
            captcha = this._client.upload(this._captchaFilename);
            if (null != captcha) {
                int intvl_idx = 0;
                int intvl = 0;
                int[] results = {0, 0};

                System.out.println("CAPTCHA " + this._captchaFilename + " uploaded: " + captcha.id);

                // Poll for the uploaded CAPTCHA status.
                while (captcha.isUploaded() && !captcha.isSolved()) {
                    results = Client.getPollInterval(intvl_idx);
                    intvl = results[0];
                    intvl_idx = results[1];
                    Thread.sleep(intvl * 1000);
                    captcha = this._client.getCaptcha(captcha);
                }

                if (captcha.isSolved()) {
                    System.out.println("CAPTCHA " + this._captchaFilename + " solved: " + captcha.text);

                    // Report incorrectly solved CAPTCHA if neccessary.
                    // Make sure you've checked if the CAPTCHA was in fact
                    // incorrectly solved, or else you might get banned as
                    // abuser.
                    /*if (this._client.report(captcha)) {
                        System.out.println("CAPTCHA " + this._captchaFilename + " reported as incorrectly solved");
                    } else {
                        System.out.println("Failed reporting incorrectly solved CAPTCHA");
                    }*/
                } else {
                    System.out.println("Failed solving CAPTCHA");
                }
            }
        } catch (java.lang.Exception e) {
            System.err.println(e.toString());
        }
    }
}


class ExampleFull
{
    public static void main(String[] args)
        throws java.lang.Exception
    {
        String authType = args[0];
        System.out.println(authType);
        int authTypeArgsNum = 0;
        Client client;
        if (authType.equals("username")){
            // Put your DBC username & password here:
            // Client client = (Client)(new HttpClient(args[0], args[1]));
            client = (Client)(new SocketClient(args[1], args[2]));
            authTypeArgsNum = 2;
        } else if (authType.equals("token")){
            // Client client = (Client)(new HttpClient(args[0], args[1]));
            client = (Client)(new SocketClient(args[1]));
            authTypeArgsNum = 1;
        } else {
            System.out.println("First argument must be authType = [username/token]");
            System.out.println("Second argument is username or token");
            System.out.println("Thisrt argument is password or captcha file");
            System.out.println("All other arguments are captcha files");
            return;
        }
        client.isVerbose = true;

        System.out.println("Your balance is " + client.getBalance() + " US cents");

        Vector threads = new Vector(args.length - authTypeArgsNum);
        for (int i = authTypeArgsNum; i < args.length; i++) {
            Thread t = new Thread(new ExampleFullDecoder(client, args[i]));
            t.start();
            threads.add(t);
        }
        for (Iterator i = threads.iterator(); i.hasNext(); ) {
            Thread t = (Thread)i.next();
            t.join();
        }

        System.out.println("Your balance is " + client.getBalance() + " US cents");
    }
}

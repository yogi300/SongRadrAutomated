package com.DeathByCaptcha;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;


/**
 * Base Death by Captcha API client.
 *
 */
abstract public class Client
{
    final static public String API_VERSION = "DBC/Java v4.6";
    final static public int SOFTWARE_VENDOR_ID = 0;

    final static public int DEFAULT_TIMEOUT = 60;
    final static public int DEFAULT_TOKEN_TIMEOUT = 120;
    final static public int[] POLLS_INTERVAL = {1, 1, 2, 3, 2, 2, 3, 2, 2};
    final static public int DFLT_POLL_INTERVAL = 3;
    final static public int LEN_POLLS_INTERVAL = POLLS_INTERVAL.length;


    /**
     * Client verbosity flag.
     *
     * When it's set to true, the client will dump API calls for debug purpose.
     */
    public boolean isVerbose = false;


    protected String _username = "";
    protected String _password = "";
    protected String _authtoken = "";


    protected void log(String call, String msg)
    {
        if (this.isVerbose) {
            System.out.println((System.currentTimeMillis() / 1000) + " " +
                               call + (null != msg ? ": " + msg : ""));
        }
    }

    protected void log(String call)
    {
        this.log(call, null);
    }

    protected JSONObject getCredentials()
    {
        try {
            if(_username == ""){
                System.out.println("Using authtoken");
                return new JSONObject().put("authtoken", this._authtoken);
            }else{
                return new JSONObject().put("username", this._username).put("password", this._password);
            }
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    protected byte[] load(InputStream st)
        throws IOException
    {
        int n = 0, offset = 0;
        byte[] img = new byte[0];
        while (true) {
            try {
                n = st.available();
            } catch (IOException e) {
                n = 0;
            }
            if (0 < n) {
                if (offset + n > img.length) {
                    img = java.util.Arrays.copyOf(img, img.length + n);
                }
                offset += st.read(img, offset, n);
            } else {
                break;
            }
        }
        return img;
    }

    protected byte[] load(File f)
        throws IOException, FileNotFoundException
    {
        InputStream st = new FileInputStream(f);
        try {
            return this.load(st);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            st.close();
        }
    }

    public byte[] load(String fn)
        throws IOException, FileNotFoundException
    {
        return this.load(new File(fn));
    }


    /**
     * Closes opened connections (if any), cleans up resources.
     */
    abstract public void close();

    /**
     * Opens API-specific connection if not opened yet.
     *
     * @throws IOException IO exception
     * @return true on success
     */
    abstract public boolean connect()
        throws IOException;


    /**
     * @param username DBC account username
     * @param password DBC account password
     */
    public Client(String username, String password)
    {
        this._username = username;
        this._password = password;
        this._authtoken = "";
    }

    /**
     * @param authtoken DBC account authtoken
     */
    public Client(String authtoken){
        this._username = "";
        this._password = "";
        this._authtoken = authtoken;
    }


    /**
     * Fetches user details.
     *
     * @return user details object
     * @throws IOException IO exception
     * @throws Exception Own exception
     */
    abstract public User getUser()
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;

    /**
     * Fetches user balance (in US cents).
     *
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return user balance
     */
    public double getBalance()
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.getUser().balance;
    }


    /**
     * Uploads a CAPTCHA to the service.
     *
     * @param img CAPTCHA image byte vector
     * @param grid grid
     * @param challenge challenge
     * @param type type
     * @param banner banner
     * @param banner_text banner_text
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return CAPTCHA object on success, null otherwise
     */
    abstract public Captcha upload(byte[] img, String challenge, int type, byte[] banner, String banner_text, String grid)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;

    abstract public Captcha upload(byte[] img, String challenge, int type, byte[] banner, String banner_text)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;

    abstract public Captcha upload(byte[] img, int type, byte[] banner, String banner_text)
        throws IOException, Exception;

    abstract public Captcha upload(byte[] img)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;

    /**
     * @see com.DeathByCaptcha.Client#upload
     * @param st CAPTCHA image stream
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return this.upload
     */
    public Captcha upload(InputStream st)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.upload(this.load(st));
    }

    /**
     * @see com.DeathByCaptcha.Client#upload
     * @param f CAPTCHA image file
     * @throws IOException IO exception
     * @throws FileNotFoundException File not found
     * @throws Exception Own exception
     * @return this.upload
     */
    public Captcha upload(File f)
            throws IOException, FileNotFoundException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.upload(this.load(f));
    }

    /**
     * @see com.DeathByCaptcha.Client#upload
     * @param fn CAPTCHA image file name
     * @throws IOException IO exception
     * @throws FileNotFoundException File not found
     * @throws Exception Own exception
     * @return this.upload
     */
    public Captcha upload(String fn)
            throws IOException, FileNotFoundException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.upload(this.load(fn));
    }

    /**
     * @see com.DeathByCaptcha.Client#upload
     * @param type CAPTCHA type
     * @param proxy User proxy
     * @param proxytype User proxy type
     * @param sitekey Site Google Key
     * @param pageurl Site url
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return this.upload
     */
    public Captcha upload(int type, String proxy, String proxytype, String sitekey, String pageurl)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
            JSONObject json = new JSONObject();
                try{
                json.put("proxy",proxy);
                json.put("proxytype",proxytype);
                json.put("googlekey",sitekey);
                json.put("pageurl",pageurl);
            } catch (JSONException e) {
                //System.out.println(e);
            }
            return this.upload(type,json);
        }
    abstract public Captcha upload(int type,JSONObject json)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;
 
        
    /**
     * Fetches an uploaded CAPTCHA details.
     *
     * @param id CAPTCHA ID
     * @return CAPTCHA object if found, null otherwise
     * @throws IOException IO exception
     * @throws Exception Own exception
     */
    abstract public Captcha getCaptcha(int id)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;

    /**
     * @see com.DeathByCaptcha.Client#getCaptcha
     * @param captcha CAPTCHA object
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return CAPTCHA
     */
    public Captcha getCaptcha(Captcha captcha)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.getCaptcha(captcha.id);
    }


    /**
     * Fetches an uploaded CAPTCHA text.
     *
     * @param id CAPTCHA ID
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return CAPTCHA text if solved, null otherwise
     */
    public String getText(int id)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.getCaptcha(id).text;
    }

    /**
     * @see com.DeathByCaptcha.Client#getText
     * @param captcha CAPTCHA object
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return this.getText text if solved, null otherwise
     */
    public String getText(Captcha captcha)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.getText(captcha.id);
    }


    /**
     * Reports an incorrectly solved CAPTCHA
     *
     * @param id CAPTCHA ID
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return true on success
     */
    abstract public boolean report(int id)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException;

    /**
     * @see com.DeathByCaptcha.Client#report
     * @param captcha CAPTCHA object
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @return this.report captcha report
     */
    public boolean report(Captcha captcha)
            throws IOException, Exception, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.report(captcha.id);
    }


    /**
     * Tries to solve a CAPTCHA by uploading it and polling for its status
     * and text with arbitrary timeout.
     *
     * @param img CAPTCHA image byte vector
     * @param grid grid
     * @param challenge challenge
     * @param type type
     * @param banner banner
     * @param banner_text banner_text
     * @param timeout Solving timeout (in seconds)
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @throws InterruptedException interruption error
     * @return CAPTCHA object if uploaded and correctly solved, null otherwise
     */
    public Captcha decode(byte[] img, String challenge, int type, byte[] banner, String banner_text, String grid, int timeout)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        long deadline = System.currentTimeMillis() + (0 < timeout ? timeout : Client.DEFAULT_TIMEOUT) * 1000;
        Captcha captcha = this.upload(img, challenge, type, banner, banner_text, grid);

        if (null != captcha) {
            int intvl_idx = 0;
            int intvl = 0;
            int[] results = {0, 0};

            while (deadline > System.currentTimeMillis() && !captcha.isSolved()) {
                results = Client.getPollInterval(intvl_idx);
                intvl = results[0];
                intvl_idx = results[1];
                Thread.sleep(intvl * 1000);
                captcha = this.getCaptcha(captcha.id);
            }
            if (captcha.isSolved() && captcha.isCorrect()) {
                return captcha;
            }
        }
        return null;
    }
    public Captcha decode(byte[] img, String challenge, int type, byte[] banner, String banner_text, int timeout)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img, challenge, type, banner, banner_text, "", 0);
    }


    public Captcha decode(byte[] img, int type, byte[] banner, String banner_text)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img, "", type, banner, banner_text, 0);
    }

    public Captcha decode(byte[] img, int type)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img, "", type, null, "", 0);
    }

    public Captcha decode(byte[] img, String challenge)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img,challenge, 0, null, "", 0);
    }

    public Captcha decode(byte[] img, int type, int timeout)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img, "", type, null, "", timeout);
    }

    public Captcha decode(byte[] img, String challenge, int timeout)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img,challenge, 0, null, "", timeout);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode(byte[], int)
     * @param img CAPTCHA image
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @throws InterruptedException interruption error
     * @return this.decode decoded captcha
     */
    public Captcha decode(byte[] img)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(img, "", 0,null, "", 0);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode
     * @param st CAPTCHA image stream
     * @param timeout Solving timeout (in seconds)
     * @param grid grid
     * @param timeout Solving timeout (in seconds)
     * @param challenge challenge
     * @param type type
     * @param banner_st banner_st
     * @param banner_text banner_text
     * @param timeout Solving timeout (in seconds)
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @throws InterruptedException interruption error
     * @return this.decode decoded captcha
     */
     public Captcha decode(InputStream st, String challenge, int type, InputStream banner_st, String banner_text, String grid, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(st),challenge,type,this.load(banner_st),banner_text, grid, timeout);
     }

     public Captcha decode(InputStream st, String challenge, int type, InputStream banner_st, String banner_text, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(st),challenge,type,this.load(banner_st),banner_text, "", timeout);
     }

     public Captcha decode(InputStream st, int type, InputStream banner_st, String banner_text, String grid)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(st, "", type, banner_st, banner_text, grid, 0);
     }

     public Captcha decode(InputStream st, int type, InputStream banner_st, String banner_text)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(st, "", type, banner_st, banner_text, "", 0);
     }

     public Captcha decode(InputStream st, int type, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(st), "", type, null, "", timeout);
     }

     public Captcha decode(InputStream st, String challenge)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(st),challenge, 0, null, "", 0);
     }

     public Captcha decode(InputStream st, String challenge, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(st),challenge, 0, null, "", timeout);
     }
  
    public Captcha decode(InputStream st, int timeout)
            throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(st), timeout);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode(InputStream, int)
     * @param st stream
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @throws InterruptedException interruption error
     * @return this.decode decoded captcha
     */
    public Captcha decode(InputStream st)
            throws IOException, Exception, InterruptedException,
            AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(st, 0);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode
     * @param f CAPTCHA image file
     * @param timeout Solving timeout (in seconds)
     * @param challenge challenge
     * @param type type
     * @param banner_f banner_f
     * @param banner_text banner_text
     * @param timeout Solving timeout (in seconds)
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @throws InterruptedException interruption error
     * @return this.decode decoded captcha
     */

     public Captcha decode(File f, String challenge, int type, File banner_f, String banner_text, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(f),challenge,type,this.load(banner_f),banner_text,timeout);
     }

     public Captcha decode(File f, int type, File banner_f, String banner_text)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(f, "", type, banner_f, banner_text, 0);
     }

     public Captcha decode(File f, int type, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(f), "", type, null, "", timeout);
     }

     public Captcha decode(File f, String challenge)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(f),challenge, 0, null, "", 0);
     }

     public Captcha decode(File f, String challenge, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(f),challenge, 0, null, "", timeout);
     }

    public Captcha decode(File f, int timeout)
            throws IOException, FileNotFoundException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(f), timeout);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode(File, int)
     * @param f CAPTCHA file
     * @throws IOException IO error
     * @throws FileNotFoundException File not found error
     * @throws Exception Own Exception
     * @throws InterruptedException Interrupted error
     * @return this.decode decoded CAPTCHA
     */
    public Captcha decode(File f)
            throws IOException, FileNotFoundException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(f, 0);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode
     * @param fn CAPTCHA image file name
     * @param challenge challenge
     * @param type type
     * @param banner_fn banner_fn
     * @param banner_text banner_text
     * @param timeout Solving timeout (in seconds)
     * @throws IOException IO exception
     * @throws Exception Own exception
     * @throws InterruptedException interruption error
     * @return this.decode decoded captcha
     */

     public Captcha decode(String fn, String challenge, int type, String banner_fn, String banner_text, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(fn),challenge,type,this.load(banner_fn),banner_text,timeout);
     }

     public Captcha decode(String fn, int type, String banner_fn, String banner_text)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(fn, "", type, banner_fn, banner_text, 0);
     }

     public Captcha decode(String fn, int type, String banner_fn, String banner_text, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(fn, "", type, banner_fn, banner_text, timeout);
     }

     public Captcha decode(String fn, int type, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(fn), "", type, null, "", timeout);
     }

     public Captcha decode(String fn, String challenge)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(fn),challenge, 0, null, "", 0);
     }

     public Captcha decode(String fn, String challenge, int timeout)
             throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         return this.decode(this.load(fn),challenge, 0, null, "", timeout);
     }

    public Captcha decode(String fn, int timeout)
            throws IOException, FileNotFoundException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(this.load(fn), timeout);
    }

    /**
     * @see com.DeathByCaptcha.Client#decode(String, int)
     * @param fn CAPTCHA image file
     * @throws IOException IO error
     * @throws InterruptedException Interruption error
     * @throws Exception Own exception
     * @throws FileNotFoundException File exception
     * @return this.decode decoded string
     */
    public Captcha decode(String fn)
            throws IOException, FileNotFoundException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(fn, 0);
    }

public Captcha decode(String proxy, String proxytype, String sitekey, String pageurl)
        throws IOException, FileNotFoundException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode( proxy,proxytype,sitekey,pageurl, 0);
    }
      public Captcha decode(String proxy, String proxytype, String sitekey, String pageurl, int timeout)
              throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         long deadline = System.currentTimeMillis() + (0 < timeout ? timeout : Client.DEFAULT_TOKEN_TIMEOUT) * 1000;
        Captcha captcha = this.upload(4, proxy, proxytype, sitekey, pageurl);

        if (null != captcha) {
            int intvl_idx = 0;
            int intvl = 0;
            int[] results = {0, 0};

            while (deadline > System.currentTimeMillis() && !captcha.isSolved()) {
                results = Client.getPollInterval(intvl_idx);
                intvl = results[0];
                intvl_idx = results[1];
                Thread.sleep(intvl * 1000);
                captcha = this.getCaptcha(captcha.id);
            }
            if (captcha.isSolved() && captcha.isCorrect()) {
                return captcha;
            }
        }
        return null;
    }
public Captcha decode(JSONObject json)
        throws IOException, FileNotFoundException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
        return this.decode(json, 0);
    }
 public Captcha decode(JSONObject json, int timeout)
         throws IOException, Exception, InterruptedException, AccessDeniedException, InvalidCaptchaException, ServiceOverloadException {
         long deadline = System.currentTimeMillis() + (0 < timeout ? timeout : Client.DEFAULT_TOKEN_TIMEOUT) * 1000;
        Captcha captcha = this.upload(4, json);

        if (null != captcha) {
            int intvl_idx = 0;
            int intvl = 0;
            int[] results = {0, 0};

            while (deadline > System.currentTimeMillis() && !captcha.isSolved()) {
                results = Client.getPollInterval(intvl_idx);
                intvl = results[0];
                intvl_idx = results[1];
                Thread.sleep(intvl * 1000);
                captcha = this.getCaptcha(captcha.id);
            }
            if (captcha.isSolved() && captcha.isCorrect()) {
                return captcha;
            }
        }
        return null;
    }

    /**
     * @param idx index of POLLS_INTERVAL
     * @return position of POLLS_INTERVAL or default
     * polling time if index out of range
     */
    public static int[] getPollInterval(int idx)
    {
        int intvl = 0;

        if (Client.LEN_POLLS_INTERVAL > idx ) {
            intvl = Client.POLLS_INTERVAL[idx];
        }

        else {
            intvl = Client.DFLT_POLL_INTERVAL;
        }

        return new int[]{intvl, ++idx};
    }
}

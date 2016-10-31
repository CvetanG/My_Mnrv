package com.minerva.itv.messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import communicator.Constants;
import communicator.Constants.StbCommand;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.minerva.itv.Logger;
import com.minerva.itv.adminclient.ParamBase;
import com.minerva.itv.adminclient.daoBase;
import com.minerva.itv.adminclient.daoLogger;
import com.minerva.itv.adminclient.db.dbBase;
import com.minerva.itv.headers.msg_board;
import com.minerva.itv.rmi.ApplicationConfig;
import com.minervanetworks.qoe.model.GenericITVSTBCommand;
import com.minervanetworks.qoe.model.ResyncByCustomerIdCommand;
import com.minervanetworks.qoe.service.job.RemoteJobService;

public class MessageUtil  {
  public MessageUtil() {
  }
  
  public final static String RESYNC_FAVORITES = "1";
  public final static String RESYNC_EPG = "3";
  public final static String RESYNC_DEVICE= "4";
  public final static String RESYNC_ACCOUNT= "5";
  public final static String RESYNC_REGION = "8"; 
  
  public static final String provider_fav_changed = "provider_fav_changed";
  public static final String service_pkg_changed = "service_pkg_changed";
  public static final String service_pkg_approved = "service_pkg_approved";
  //ca messages
  public static final String ProvisionCustomer = "ProvisionCustomer";
  public static final String UnProvisionCustomer = "UnProvisionCustomer";
  public static final String AddPhysicalDevice = "AddPhysicalDevice";
  private final static Md5PasswordEncoder enc = new Md5PasswordEncoder();
  
//  @Autowired
//  private static StbrtCDK stbrt; 
  @Autowired
  private static RemoteJobService jobService;

  public static void SendMessageDirect(Hashtable hmsg, String logLoc) throws Exception {
    String ll = daoLogger.GetLoggerLocationByName(logLoc);
    if (!ll.equals(ParamBase.fail))
      SendMessage(hmsg, daoLogger.GetLoglogIP(ll), daoLogger.GetLoglogPort(ll));
    Logger.writeLog(logLoc + " does not exist.");
  }
  
  /**
   * @param hmsg
   * @param url
   * @param sc
   * @param adminInfo
   * @param deviceId
   * @param payload
   * @param sendToMSGBOARD - boolean, if true the message will be send AND to the MSGBOARD/Dispatcher
   * @return
   * @throws Exception
   */
  public static Object SendMessage(Hashtable hmsg, String url, ServletContext sc, 
      Hashtable adminInfo, String deviceId, String payload, boolean sendToMSGBOARD) throws Exception {

    Logger.writeLog("Sending msg, deviceId: "+ deviceId+ " , payload: "+payload +", msg code: "+(String) hmsg.get(msg_board.msg_code));
    String msg_code = (String) hmsg.get(msg_board.msg_code);
    if (msg_code.equals("provider_fav_changed") ||msg_code.equals("service_pkg_approved") || msg_code.equals("update_vault") || 
        msg_code.equals("userPreference") || msg_code.equals("reboot") || msg_code.equals("device_deactivated") ||
        msg_code.equals("customer_deactivated") || msg_code.equals("EPG") || msg_code.equals("Region") 
        || msg_code.equals("device") || msg_code.equals("Lineup") ) {
      //Send to itvmgrac to send it in unicast too
  
     
      try {
        if (jobService == null) 
          getJobService(sc);
        
        String adminUser = (String)(adminInfo).get("login");
        String adminPassword = enc.encodePassword((String)(adminInfo).get("password"), null); 
        
        jobService.authenticate(adminUser, adminPassword);

        GenericITVSTBCommand command = new GenericITVSTBCommand();
        
      //Set the proper recipient type - customer or device
        if(deviceId != null)
          command.setDeviceId(deviceId);
        else
          command.setCustomerId((String) hmsg.get(msg_board.payload));
        
        if (payload != null && ! payload.trim().equals(""))
          command.setPayload(payload);

        
        //Set the command
        if (msg_code.equals("provider_fav_changed"))  {
        	command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
        	command.setPayload(createResyncPayload(RESYNC_FAVORITES, null)); // resync sub command 
        }
        if (msg_code.equals("service_pkg_approved"))  {
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
          command.setPayload(createResyncPayload(RESYNC_ACCOUNT, null)); // resync sub command 
        }
        if (msg_code.equals("update_vault"))    
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
        if(msg_code.equals("userPreference")) {
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
          command.setPayload(createResyncPayload(RESYNC_ACCOUNT, null));// resync sub command 
        }
        if(msg_code.equals("device_deactivated"))
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_CUSTOMER_DEACTIVATED.getId());
        if(msg_code.equals("customer_deactivated"))
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_CUSTOMER_DEACTIVATED.getId());
        if(msg_code.equals("reboot"))
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_REBOOT.getId());
        if(msg_code.equals("EPG")) {
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
          command.setPayload(createResyncPayload(RESYNC_EPG, null));// resync sub command 
        }
        if(msg_code.equals("Lineup")) {
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
          command.setPayload(createResyncPayload(RESYNC_EPG, payload));// resync sub command 
        }
        if(msg_code.equals("Region")) {
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
          command.setPayload(createResyncPayload(RESYNC_REGION, null));// resync sub command 
        }
        if(msg_code.equals("device")) {
          command.setStbCommandId((long) StbCommand.ITVSTBCMD_STB_RESYNC_DATA.getId());
          command.setPayload(createResyncPayload(RESYNC_DEVICE, null));// resync sub command 
        }
        
        Logger.writeLog("command is: "+StbCommand.getCommand(command.getStbCommandId().intValue()) );
        
        command.setSize(100L); //some conservative values
        command.setDelay(10L);
        

        
        jobService.startGenericJob(command);
      } catch (Exception e) {
        Logger.writeLog("Something went wrong when sending to the QoE");

      }
    }
    //send the message also in multicast for the multicast enabled devices
    if(sendToMSGBOARD)
      return SendMessage(hmsg, "");
    else 
      return ParamBase.pass;
  }

  private static void getJobService(ServletContext sc) {
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
//    ApplicationContext ctx = ApplicationConfig.getContext();
    jobService =  (RemoteJobService) ctx.getBean("webservice.JobService");
  }

  public static Object SendMessage(Hashtable hmsg, String url) throws Exception{
    /*
    Hashtable hparam = new Hashtable();
    hparam.put(ParamBase.classname, "daoMessage");
    hparam.put(ParamBase.action, "RequestSend");
    hparam.put(ParamBase.message, hmsg);
    if (!hmsg.get(ParamBase.msg_code).equals("GetSysMonStatus")) Logger.writeLog("Sending Message package to -> " + url);
    Object ret = ServletComm.ServletServiceComm(hparam, new URL(url + "/messageQuery"));
    if (!hmsg.get(ParamBase.msg_code).equals("GetSysMonStatus")) Logger.writeLog(ret.toString());
    */
    
    Connection conn = daoBase.GetConnection();
    try{
      Object seq = daoBase.GetSeq(conn, "msg_id_seq.nextval");
      hmsg.put(ParamBase.table, "msg_board");
      hmsg.put(msg_board.msg_id, seq);
      new dbBase(conn, "insertTable", hmsg).InvokeDB();
      conn.commit();
      return ParamBase.pass;
    }catch(Exception ex){
      conn.rollback();
      Logger.writeLog(ex);
      return ParamBase.fail;
    }finally{
      dbBase.CloseConn(conn);
    }
  }

  public static Object SendMessage(Hashtable hparam, Socket socket) throws Exception {
    InputStream in = null;
    ObjectOutputStream oout = null;
    try{
      oout = new ObjectOutputStream(socket.getOutputStream());
      oout.writeObject(hparam);
      oout.flush();
      
      in = socket.getInputStream();
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      byte[] b = new byte[4096];
      int length = 0, index = 0;;
      while ((length = in.read(b, 0, b.length)) > -1) {
        bout.write(b, 0, length);
        index += length; 
      }
      
      b = bout.toByteArray();
      byte[] bo = new byte[b.length - 4];
      System.arraycopy(b, 4, bo, 0, bo.length);  //orginal data is padded with 4 bytes in the front, why??????
      ByteArrayInputStream bin = new ByteArrayInputStream(b);
  
      return (new ObjectInputStream(bin)).readObject();
    }finally{
      try{
        if (in != null) in.close();
        if (oout != null) oout.close();
      } catch (Exception ex){
        //com.minerva.itv.Logger.writeLog(ex);
      }
    }
  }
  
  public static Object SendMessage(Hashtable hparam, String ip, int port, int socketTimeout){
    Socket socket = null;
    InputStream in = null;
    ObjectOutputStream oout = null;
    try  {
      socket = new Socket(ip, port);
      socket.setSoTimeout(socketTimeout);
      
      oout = new ObjectOutputStream(socket.getOutputStream());
      oout.writeObject(hparam);
      oout.flush();
      
      in = socket.getInputStream();
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      byte[] b = new byte[4096];
      int length = 0, index = 0;;
      while ((length = in.read(b, 0, b.length)) > -1) {
        bout.write(b, 0, length);
        index += length; 
      }
      
      b = bout.toByteArray();
      byte[] bo = new byte[b.length - 4];
      System.arraycopy(b, 4, bo, 0, bo.length);  //orginal data is padded with 4 bytes in the front, why??????
      ByteArrayInputStream bin = new ByteArrayInputStream(b);

      return (new ObjectInputStream(bin)).readObject();
      
    } catch (Exception ex)  {
      //com.minerva.itv.Logger.writeLog("Failed on cmd - " + hparam.get(message.Cmd.command));
      //com.minerva.itv.Logger.writeLog(ex);
      ex.printStackTrace();
      return ParamBase.fail;
    } finally  {
      try{
        if (in != null) in.close();
        if (oout != null) oout.close();
        if (socket != null) socket.close();
      } catch (Exception ex){
        //com.minerva.itv.Logger.writeLog(ex);
      }
    }
  }
  
  public static Object SendMessage(Hashtable hparam, String ip, int port){
    return SendMessage(hparam, ip, port, 10000);
  }
  
  private static void test1() throws Exception {
    /*VideoServer vs = new VideoServer();
    vs.m_ip = "192.168.4.201";
    vs.m_login = "nvs";
    vs.m_passwd = "ncubesys";
    NCUBE ncube = new NCUBE(vs, null);

    Partition part = new Partition();
    part.m_path = "/mds/chicken1/";
    
    Asset asset = new Asset();
    asset.m_filename = "BillyMadisonbackup.mpg";
    asset.m_srcFilename = "BillyMadisonbackup.mpg";
    asset.m_srcLogin = "Anonymous";
    asset.m_srcPasswd = "";
    asset.m_srcUrl = "192.168.6.27";
    asset.m_srcPath = "ITVMGR_CONTENT";
    asset.m_partition = part;
    
    //ncube.GetAssetInfo(asset);
    //ncube.AddAsset(asset);
    //ncube.DeleteAsset(asset);
    //ncube.RegisterTagAsset(asset);
    //Vector parts = ncube.GetPartitionInfo();
    
    Hashtable hparam = new Hashtable();
    hparam.put(com.minerva.vsm.VSMLogger.MessageListener.command, com.minerva.vsm.VSMLogger.MessageListener.Action);
    hparam.put(com.minerva.vsm.VSMLogger.MessageListener.Action, "GetVideoServerInfo");
    hparam.put(com.minerva.vsm.VSMLogger.MessageListener.ACK, "true");
    hparam.put(com.minerva.vsm.VSM.VIDEOSERVER, vs);
    hparam.put(com.minerva.vsm.VSMLogger.MessageListener.data, asset);
    Object ret = com.minerva.itv.messaging.messageUtil.SendMessage(hparam, "127.0.0.1", 6972, 60 * 1000);
    System.out.println(ret);*/
  }
  
  private static Map<String, Object> getUsernameAndPassword(String sessionId) {
    
    Vector ret;
    try {
      ret = daoBase.GetRsByQueryStatic("SELECT admin_user.login, admin_user.password FROM admin_user,"
          + " admin_session WHERE admin_session.session_id='" +sessionId 
          +"' AND admin_session.status='A' AND admin_session.admin_user_id = admin_user.admin_user_id");
  
    if (ret!= null && ret.size()!= 0) {
      Hashtable hitem = (Hashtable)ret.get(0);
      return hitem;
    }
    
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }
  
  
  /**
   * method that generates payload for the resync command
   */
  private static String createResyncPayload (String commandId, String lineupId) {
    StringBuilder sb = new StringBuilder();
    sb.append("{ \n \"resyncType\" : ");
    sb.append(commandId);
    sb.append(" , \n");
    sb.append(" \"resyncData\" : {");
    if(lineupId != null) {
      sb.append("\"lineupId\" : "+lineupId);
    }
    sb.append("}\n }");
    
    return sb.toString();

  }
  
  
  public static void main(String[] args) {
    try  {
//      test1();
//      Hashtable hparam1 = new Hashtable();
//      hparam1.put(Logger.MessageListener.command, Logger.MessageListener.Action);
//      hparam1.put(Logger.MessageListener.Action, "ProcessPreEncryption");
//      hparam1.put(Logger.MessageListener.data, "Test");
//      Object status = com.minerva.itv.messaging.messageUtil.SendMessage(hparam1, "127.0.0.1", 6971);
//      System.out.println();
      System.out.println(createResyncPayload("3", null));
    } catch (Exception ex)  {
      ex.printStackTrace();
    } finally  {
      System.out.println("x");
    }
    
  }
}
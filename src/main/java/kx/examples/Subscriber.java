package kx.examples;

import java.util.logging.Logger;
import java.util.logging.Level;

import kx.Connection;

public class Subscriber{
  private static final Logger LOGGER = Logger.getLogger(Subscriber.class.getName());

  public static void main(String[] args){// example tick subscriber
    Connection Connection =null;
    try{
      Connection =new Connection("localhost",5010,System.getProperty("user.name")+":mypassword");
      Connection.ks(".u.sub","mytable","MSFT");
      boolean ok = true;
      while(ok){
        try {
          LOGGER.log(Level.INFO,"Received {0}", Connection.k());
        }
        catch(Exception e){
          LOGGER.log(Level.SEVERE,"Exception receiving msg {0}",e.toString());
          ok=false;
        }
      }
    }catch(Exception e){
      LOGGER.log(Level.SEVERE,e.toString());
    }finally{
      if(Connection !=null)
        try{
			Connection.close();}catch(java.io.IOException e){
          // ingnore exception
        }
    }
  }
}

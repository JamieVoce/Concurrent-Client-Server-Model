package systemssoftwareproject.ServerNicksVersion;
import java.net.Socket;
import java.io.*; 
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import systemssoftwareproject.DataStructures.SampleType;
import systemssoftwareproject.DataStructures.WeatherStationType;
import systemssoftwareproject.DataStructures.wscom;
import systemssoftwareproject.WeatherStation.WeatherStation;


public class ClientHandler implements Runnable { 
    private final Socket clientSocket; 
    private WeatherStation weatherStation;
    private Server server;
    // Constructor 
    public ClientHandler(Socket socket, Server server){ 
        this.clientSocket = socket; 
        this.weatherStation = new WeatherStation();
        this.server = server;
    } 

    @Override
    public void run(){ 
        try {    
            while(true){
                // Create IO Streams
                server.weatherStations.add(weatherStation);
                            //System.out.println("testing");
                ObjectInputStream inFromStation = new 
            ObjectInputStream(clientSocket.getInputStream());
                var outToStation = new PrintWriter(clientSocket.getOutputStream(), true);
                outToStation.println(wscom.DATA);
                //Will Request then print the Stations ID
                outToStation.println(wscom.SEND);
                //Will then request a sample
                 while (true) {
                        int type  = inFromStation.readInt();
                        if(type == 0){
                        SampleType sample = (SampleType)inFromStation.readObject();
                        System.out.println(sample.getHumid());
                        outToStation.println(wscom.SAMPLECONFIRM);
                        System.out.println("Waiting 20 seconds to continue");
                        TimeUnit.SECONDS.sleep(20);// replace with constant
                        outToStation.println(wscom.SEND);
                        }else{
                            WeatherStationType station = (WeatherStationType)inFromStation.readObject();
                            System.out.println(station.getID());

                        }
                }
                

            }
            
        } 

        catch (IOException e) { 
             System.out.println("ahhh");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
} 

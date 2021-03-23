package systemssoftwareproject.WeatherStation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import systemssoftwareproject.DataStructures.WeatherStationType;
import systemssoftwareproject.DataStructures.wscom;

public class WeatherStation extends WeatherInstruments {
    
        private Scanner in;
        private ObjectOutputStream  out;
        private WeatherStationType ws;
        public WeatherStation(){
            this.ws = new WeatherStationType();
            
}
        public static void main(String[] args) throws IOException {
        System.out.println("WS-Client\n");
        WeatherStation weatherStation = new WeatherStation();
        weatherStation.run();
    }
        public void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, 1234);
        
        //var scanner = new Scanner(System.in);
        in =  new Scanner(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        while(true){
        while (in.hasNextLine()) {
            RecieveRequest();
            }
        // Process all messages from server, according to the protocol. 
        }
    }

    private void RecieveRequest() throws IOException{
        String line = in.nextLine();
            if(line.startsWith(wscom.SEND)){
                    out.writeInt(0);
                out.writeObject(getSample());
               System.out.println("SAMPLE has been sent");
            }else if(line.startsWith(wscom.SAMPLECONFIRM)){
                System.out.println("Samplewas reviced");
            }else if(line.startsWith(wscom.DATA)){ 
                out.writeInt(1);
                out.writeObject(ws);
            }
            else{
                System.out.println(line);
                System.out.println("The client recived an invalid command!");
            }
    }
    
    
}

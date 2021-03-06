package bgu.spl181.net.impl.BBtpc;

import bgu.spl181.net.api.DataHandler;
import bgu.spl181.net.impl.DataHandling.MovieRentalService;
import bgu.spl181.net.impl.encoder.LineMessageEncoderDecoder;
import bgu.spl181.net.impl.protocol.MovieRentalProtocol;

public class TPCMain {
    public static void main(String[] args) {
        DataHandler service= new MovieRentalService();
        System.out.println("tpc");

        MovieRentalTPCServer server = new MovieRentalTPCServer(Integer.parseInt(args[0]),
                ()->new MovieRentalProtocol((MovieRentalService) service), //protocol factory
                LineMessageEncoderDecoder::new);
        server.serve();
    }
}



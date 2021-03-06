package bgu.spl181.net.impl.messages;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MessageTest {

    private Message register,balanceInfo,login,info1,info2,rent,changeprice,
            returnMovie,balanceAdd,signout,remmovie,addmovie;

    @Before
    public void beforeTest(){
        register = new Message("REGISTER shlomi tryingagain country=\"Russia\"");
        balanceInfo =new Message("REQUEST balance info");
        login =new Message("LOGIN shlomi mahpass");
        info1 =new Message("REQUEST info");
        info2 =new Message("REQUEST info \"The Notebook\"");
        rent =new Message("REQUEST rent \"The Notebook\"");
        changeprice =new Message("REQUEST changeprice \"The Notebook\" 22");
        returnMovie =new Message("REQUEST return \"The Notebook\"");
        balanceAdd =new Message("REQUEST balance add -12");
        signout =new Message("SIGNOUT");
        remmovie =new Message("REQUEST remmovie \"The Godfather\"");
        addmovie =new Message("REQUEST addmovie \"South Park: Bigger, Longer & Uncut\" 30 9 \"Israel\" \"Iran\" \"Italy\"");
    }

    @Test
    public void register(){
        register=register.unpackMessage().unpackMessage();
        RegisterMRS unpacked = (RegisterMRS)register;
        assertTrue("incorrect userName",unpacked.getUserName().equals("shlomi"));
        assertTrue("incorrect password, got: "+unpacked.getPassword()+" instead of tryingagain",unpacked.getPassword().equals("tryingagain"));
        assertTrue("incorrect country",unpacked.getCountry().equals("Russia"));
    }

    @Test
    public void login(){
        login=login.unpackMessage();
        Login unpacked = (Login) login;
        assertTrue("incorrect userName",unpacked.getUserName().equals("shlomi"));
        assertTrue("incorrect password",unpacked.getPassword().equals("mahpass"));
    }

    @Test
    public void rent(){
        rent=rent.unpackMessage().unpackMessage();
        RequestRent unpacked = (RequestRent) rent;
        assertTrue("incorrect movieName",unpacked.getMovieName().equals("The Notebook"));

    }
    @Test
    public void returnMovie(){
        returnMovie=returnMovie.unpackMessage().unpackMessage();
        RequestReturn unpacked = (RequestReturn) returnMovie;
        assertTrue("incorrect movieName",unpacked.getMovieName().equals("The Notebook"));
    }
    @Test
    public void remmovie(){
        remmovie=remmovie.unpackMessage().unpackMessage();
        RequestRemMovie unpacked = (RequestRemMovie) remmovie;
        assertTrue("incorrect movieName",unpacked.getMovieName().equals("The Godfather"));
    }
    @Test
    public void addmovie(){
        addmovie=addmovie.unpackMessage().unpackMessage();
        RequestAddMovie unpacked = (RequestAddMovie) addmovie;
        assertTrue("incorrect movieName",unpacked.getMovieName().equals("South Park: Bigger, Longer & Uncut"));
        assertTrue("incorrect amount",unpacked.getAmount()==30);
        assertTrue("incorrect price, got: "+unpacked.getPrice()+" instead of 9",unpacked.getPrice()==9);

        List<String> countries = new LinkedList<>();
        countries.add("Israel");
        countries.add("Iran");
        countries.add("Italy");
        assertTrue("incorrect bannedCountries",unpacked.getBannedCountries().containsAll(countries));
        assertTrue("incorrect bannedCountries",unpacked.getBannedCountries().size()==3);
    }

    @Test
    public void balanceAdd(){
        balanceAdd=balanceAdd.unpackMessage().unpackMessage();
        RequestBalanceAdd unpacked = (RequestBalanceAdd) balanceAdd;
        assertTrue("incorrect amount",unpacked.getAmount()==-12);
    }
    @Test
    public void changePrice(){
        changeprice=changeprice.unpackMessage().unpackMessage();
        RequestChangePrice unpacked = (RequestChangePrice) changeprice;
        assertTrue("incorrect movieName",unpacked.getMovieName().equals("The Notebook"));
        assertTrue("incorrect price",unpacked.getPrice()==22);
    }


    @Test
    public void unpack(){
        register=register.unpackMessage();
        assertTrue("register unpack failed",register instanceof Register);
        register=register.unpackMessage();
        assertTrue("RegisterMRS unpack failed",register instanceof RegisterMRS);

        login=login.unpackMessage();
        assertTrue("login unpack failed",login instanceof Login);

        signout=signout.unpackMessage();
        assertTrue("signout unpack failed",signout instanceof Signout);

        // requests first unpack
        info1=info1.unpackMessage();
        assertTrue("info1 first unpack failed",info1 instanceof Request);
        info2=info2.unpackMessage();
        assertTrue("info2 first unpack failed",info2 instanceof Request);
        balanceInfo=balanceInfo.unpackMessage();
        assertTrue("balanceInfo first unpack failed",balanceInfo instanceof Request);
        rent=rent.unpackMessage();
        assertTrue("rent first unpack failed",rent instanceof Request);
        changeprice=changeprice.unpackMessage();
        assertTrue("changeprice first unpack failed",changeprice instanceof Request);
        returnMovie=returnMovie.unpackMessage();
        assertTrue("returnMovie first unpack failed",returnMovie instanceof Request);
        balanceAdd=balanceAdd.unpackMessage();
        assertTrue("balanceAdd first unpack failed",balanceAdd instanceof Request);
        remmovie=remmovie.unpackMessage();
        assertTrue("remmovie first unpack failed",remmovie instanceof Request);
        addmovie=addmovie.unpackMessage();
        assertTrue("addmovie first unpack failed",addmovie instanceof Request);

        // requests second unpack
        info1=info1.unpackMessage();
        assertTrue("info1 second unpack failed",info1 instanceof RequestInfo);
        info2=info2.unpackMessage();
        assertTrue("info2 second unpack failed",info2 instanceof RequestInfo);
        balanceInfo=balanceInfo.unpackMessage();
        assertTrue("balanceInfo second unpack failed",balanceInfo instanceof RequestBalanceInfo);
        rent=rent.unpackMessage();
        assertTrue("rent second unpack failed",rent instanceof RequestRent);
        changeprice=changeprice.unpackMessage();
        assertTrue("changeprice second unpack failed",changeprice instanceof RequestChangePrice);
        returnMovie=returnMovie.unpackMessage();
        assertTrue("returnMovie second unpack failed",returnMovie instanceof RequestReturn);
        balanceAdd=balanceAdd.unpackMessage();
        assertTrue("balanceAdd second unpack failed",balanceAdd instanceof RequestBalanceAdd);
        remmovie=remmovie.unpackMessage();
        assertTrue("remmovie second unpack failed",remmovie instanceof RequestRemMovie);
        addmovie=addmovie.unpackMessage();
        assertTrue("addmovie second unpack failed",addmovie instanceof RequestAddMovie);
    }

}
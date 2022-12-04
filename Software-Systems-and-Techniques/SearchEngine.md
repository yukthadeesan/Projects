```
//search engine to find particular strings in a list.
//an implementation of this method can be used to traverse files. 

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {

    ArrayList<String> stringList= new ArrayList<>();

    public String handleRequest(URI url) {

        if (url.getPath().equals("/")) {
            String strToReturn= "";
            for(int i=0; i< stringList.size(); i++){
                strToReturn+=stringList.get(i);
            }
            return "String List: "+ strToReturn;
        } 
        else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].contains("s")){
            stringList.add((parameters[1]));
            return String.format("String added!");
            }
            else{
                return "invalid command";
            }
        }
        else if (url.getPath().contains("/search")){
            String strToReturnNew= "";
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].contains("s")){
            for(int i=0; i< stringList.size(); i++){
                if(stringList.get(i).contains(parameters[1])){
                strToReturnNew += (stringList.get(i)+" ");
                }    
            }
            if(strToReturnNew.isEmpty()){
                return "No strings with the given substring was found.";
            }
            else{
            return "Strings found are: "+strToReturnNew;}
            }                  
            else{
                return "invalid command";
            }
        }
        else{
        return "404 Not Found!";
        }
    }
}
class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
```

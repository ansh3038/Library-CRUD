import java.util.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class library{
    /**
     * @param args
     * @throws IOException
     */
    public static void print(Objects obj) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.printf("1. Add a Book\n2. Issue a Book\n3. Return a Book\n4. History\n5. Save & exit\n");
        switch(sc.nextInt()){
            case 1:
            obj.addBook();
            break;
            case 2:
            obj.issue();
            break;
            case 3:
            obj.returnBook();
            break;
            case 4:
            obj.history();
            break;
            case 5:
            obj.save();
            System.exit(0);
            break;


        }
    }
    public static void main(String args[]) throws IOException{
        Objects obj = new Objects();
        obj.open();
        while(true){
            print(obj);
            
        }

    }
}
class Objects{
    static HashMap<Integer,String> name = new HashMap<>();
    static HashMap<Integer,String> author = new HashMap<>();
    static HashMap<Integer,Integer> issueName = new HashMap<>();
    Scanner sc = new Scanner(System.in);
    // static HashMap<Integer,String> Name = new HashMap<>();
    public Objects(){
    }
    public static void addFile(String s){
      try {
      File myObj = new File(s);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        String arr[]=data.split(" ");
        name.put(Integer.parseInt(arr[0]),arr[1]);
        author.put(Integer.parseInt(arr[0]),arr[2]);
        if(!arr[3].equals("null")){
        issueName.put(Integer.parseInt(arr[0]),Integer.parseInt(arr[3]));
        }
    }
      myReader.close();
    } catch (FileNotFoundException e) {}
}
    public static void createFile(String s) throws IOException{
        File myObj = new File(s);    
        myObj.createNewFile();
    }
    public void open() throws IOException{
        Path path = Paths.get("book.txt");
        if (Files.exists(path)) {   addFile("book.txt");    }
    }
    public void addBook(){
        
        System.out.println("Enter Sr. No of Book");
        int a = sc.nextInt();
        if(name.containsKey(a)){
            System.out.println("Already Exists");
            addBook();
            return;
        }
        else{
            System.out.println("Enter Name of book ");
            String nameBook= sc.next();
            System.out.println("Enter Author");
            String nameAuthor = sc.next();
            name.put(a,nameBook);
            author.put(a,nameAuthor); 
        }
    }

    public String time(){
        LocalDateTime now = LocalDateTime.now(); 
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
        String formatDateTime = now.format(format);
        return formatDateTime;
    }
    
    public void log(int srno, int rollno, int status ) throws IOException{
        String str = status==0 ? "Issued" : "Returned" ;
        BufferedWriter out = new BufferedWriter(new FileWriter("history.txt",true));
        out.write(time()+" "+srno+" " +rollno+ " " +str+ "\n");
        out.close();

    }
    public void issue() throws IOException{
        System.out.println("Enter Srno.");
        int a = sc.nextInt();
        if(issueName.containsKey(a)){
            System.out.println("This Srno. is issued to "+issueName.get(a));
            issue();
        }
        System.out.println("Enter Roll No.");
        int b = sc.nextInt(); 
        issueName.putIfAbsent(a,b);
        log(a,b,0);
        

    }

    public void returnBook() throws IOException{
        System.out.println("Enter Srno.");
        int a = sc.nextInt();
        int b = 0;
        if(issueName.containsKey(a)){
            b = issueName.get(a);
            System.out.println("This Srno. is issued to "+b);
            issueName.remove(a);
        }
        else{
            System.out.println("This is not issued to anyone");
            returnBook();
        }
        log(a,b,1);        
    }

    public void history(){
        try {
            File myObj = new File("history.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              System.out.println(data);      
            }
            myReader.close();
          } catch (FileNotFoundException e) {
          }

    }
    public void save(){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("book.txt"));
            // Writing on output stream
            for (Map.Entry<Integer,String> entry : name.entrySet()) 
            out.write(entry.getKey()+" "+ entry.getValue()+" "+author.get(entry.getKey())+" "+issueName.getOrDefault(entry.getKey(), null)+"\n");
            // Closing the connection
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
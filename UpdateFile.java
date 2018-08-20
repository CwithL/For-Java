import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateFile {
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        public void run() {
            try{
                String fname = "Write1.txt";
                String childdir = "backup";
                File f1,f2,child;
                f1 = new File(fname);
                child = new File(childdir);
                if(f1.exists()) {
                    if(!child.exists()){
                        child.mkdir();
                    }
                    f2 = new File(child, fname);
                    if(!f2.exists()||f2.exists()&&(f1.lastModified() > f2.lastModified())) {
                        copy(f1, f2);
                    }
                    getinfo(f1);
                    getinfo(child);
                }
                else{
                    System.out.println(f1.getName() + " file not found!");
                }
            }catch (IOException e){
                System.out.println("error");
            }
        }
    };
    //start 方法不能少，主要是schedule方法
    public void start(int delay, int internal) {
        timer.schedule(task, delay * 1000, internal * 1000);
    }
    public static void main(String[] args) throws IOException{
        UpdateFile updateFile = new UpdateFile();
        updateFile.start(1, 60);
    }
    public void copy(File f1, File f2) throws IOException{
        FileInputStream rf = new FileInputStream(f1);
        FileOutputStream wf = new FileOutputStream(f2);
        int count,n = 512;
        byte buffer[] = new byte[n];
        count = rf.read(buffer,0,n);
        while (count != -1) {
            wf.write(buffer,0,count);
            count = rf.read(buffer,0,n);
        }
        System.out.println("CopyFile " + f2.getName() + "!");
        rf.close();
        wf.close();
    }
    public static void getinfo(File f1) throws IOException{
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy年 MM 月 dd 日 hh 时 mm 分");
        if(f1.isFile()) {
            System.out.println("<File>\t" + f1.getAbsolutePath() + "\t" + f1.length() + "\t" +
                    sdf.format(new Date()));
        }
        else{
            System.out.println("<Dir>\t" + f1.getAbsolutePath());
            File[] files = f1.listFiles();
            for(int i = 0;i<files.length; i++) {
                getinfo(files[i]);
            }
        }
    }
}
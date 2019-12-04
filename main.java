import java.util.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.scene.paint.*;
import javafx.scene.effect.*;
import java.util.*;
import java.lang.Math;

public class main extends Application{
  public static int width=750;
  public static int height=750;
  public static void main(String[] args){
    launch(args);
  }
  public double a=0.2;
  public double b=0.2;
  public double c=5;

  /*public double a=0.2;
  public double b=0.2;
  public double c=5.7;*/
  public double findDx(double x, double y, double z, double dt){
    return (-y-z)*dt;
    //return a*(y-x)*dt;
    //return dt*(-z-y);
  }public double findDy(double x, double y, double z, double dt){
    return (x+a*y)*dt;
    //return (b*x - y - x*z)*dt;
    //return dt*(x+a*y);
  }public double findDz(double x, double y, double z, double dt){
    return (b+z*(x-c))*dt;
    //return (x*y - c *z)*dt;
    //return dt*(b+z*(x-c));
  }
  public void start(Stage alphaStage){
    alphaStage.setTitle("Graphing");
    Group root=new Group();
    Scene scene=new Scene(root);
    Scanner scan=new Scanner(System.in);

    alphaStage.setScene(scene);

    Canvas canvas=new Canvas(width,height);
    root.getChildren().add(canvas);

    GraphicsContext gc=canvas.getGraphicsContext2D();
    final long startNanoTime=System.nanoTime();
    final int length=20000;
    double dataset[][]=new double[3][length];
    double dt=0.01;
    double x=0;
    double y=0;
    double z=0;
    dataset[0][0]=x;
    dataset[1][0]=y;
    dataset[2][0]=z;

    for(int i=1;i<length;i++){
      x=dataset[0][i-1];
      y=dataset[1][i-1];
      z=dataset[2][i-1];
      dataset[0][i]=x+findDx(x,y,z,dt);
      dataset[1][i]=y+findDy(x,y,z,dt);
      dataset[2][i]=z+findDz(x,y,z,dt);
      if(i>length-10){
        dataset[0][i]=length-i;
        dataset[1][i]=0;
        dataset[2][i]=0;
      }else if(i>length-20){
          dataset[0][i]=0;
          dataset[1][i]=length-i-10;
          dataset[2][i]=0;
      }else if(i>length-30){
        dataset[0][i]=0;
        dataset[1][i]=0;
        dataset[2][i]=length-i-20;
      }/*
      dataset[0][i]=0;
      dataset[1][i]=0;
      dataset[2][i]=0;
      */
    }
    /*
    dataset[0][0]=10;
    dataset[1][0]=0;
    dataset[2][0]=0;

    dataset[0][1]=0;
    dataset[1][1]=10;
    dataset[2][1]=0;

    dataset[0][2]=0;
    dataset[1][2]=0;
    dataset[2][2]=10;

    dataset[0][3]=5;
    dataset[1][3]=0;
    dataset[2][3]=0;

    dataset[0][4]=0;
    dataset[1][4]=5;
    dataset[2][4]=0;

    dataset[0][5]=0;
    dataset[1][5]=0;
    dataset[2][5]=5;
    */
    //final double data[][]=dataset;
    new AnimationTimer(){
      public double x=0;
      public double y=0;
      public double z=0;

      public double aa=3.14/2;
      public double bb=0;

      public double t=0;
      public double a=Math.PI/2;

      public double zoom=20;

      public double alpha=0.2;
      public double beta=0.2;
      public double charlie=1;

      public double findDx(double x, double y, double z, double dt){
        return (-y-z)*dt;
        //return a*(y-x)*dt;
        //return dt*(-z-y);
      }public double findDy(double x, double y, double z, double dt){
        return (x+alpha*y)*dt;
        //return (b*x - y - x*z)*dt;
        //return dt*(x+a*y);
      }public double findDz(double x, double y, double z, double dt){
        return (beta+z*(x-charlie))*dt;
        //return (x*y - c *z)*dt;
        //return dt*(b+z*(x-c));
      }
      public double[][] getData(){
        double output[][]=new double[3][length];
        double dt=0.01;
        double xa=0;
        double ya=0;
        double za=0;
        output[0][0]=x;
        output[1][0]=y;
        output[2][0]=z;

        for(int i=1;i<length;i++){
          xa=output[0][i-1];
          ya=output[1][i-1];
          za=output[2][i-1];
          output[0][i]=xa+findDx(xa,ya,za,dt);
          output[1][i]=ya+findDy(xa,ya,za,dt);
          output[2][i]=za+findDz(xa,ya,za,dt);
          if(i<3000){
            output[0][i-1]=0;
            output[1][i-1]=0;
            output[2][i-1]=0;
          }
          if(i>length-10){
            output[0][i]=length-i;
            output[1][i]=0;
            output[2][i]=0;
          }else if(i>length-20){
              output[0][i]=0;
              output[1][i]=length-i-10;
              output[2][i]=0;
          }else if(i>length-30){
            output[0][i]=0;
            output[1][i]=0;
            output[2][i]=length-i-20;
          }
        }
        return output;
      }

      public boolean boop=false;
      public void handle(long currentNanoTime){
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(0, 0, height, width);

        if(charlie<5&&!boop){
          charlie+=0.001;
        }if(charlie>=5||boop){
          boop=true;
          charlie-=0.001;
        }if(charlie<=1&&boop){
          boop=false;
          charlie+=0.001;
        }
        System.out.println(charlie);
        double dt=0.001;
        double shift=0.1;
        double shiftb=0.02;
        //Shift in other 2 axis
        //t+=shift;
        //a-=shift;
        //Shift in 1 axis
        aa+=shiftb;
        //Center points
        double cX=0;
        double cY=0;
        double cZ=0;
        double data[][]=getData();

        gc.setFill(Color.rgb(0,0,0));
        for(int i=0;i<length;i++){
          double min=Math.sin(aa);
          double max=1;
          x=data[0][i]*max*Math.sin(a)-data[1][i]*max*Math.sin(t);
          y=-data[1][i]*min*Math.cos(t)-data[0][i]*min*Math.cos(a)+(Math.cos(aa))*data[2][i];
          double xx=cX*max*Math.sin(a)-cY*max*Math.sin(t);
          double yy=-cY*min*Math.cos(t)-cX*min*Math.cos(a)+(Math.cos(aa))*cZ;

          gc.fillOval(width/2 -zoom*xx + zoom*x,height/2 + zoom*yy - zoom*y,1,1);
        }
      }
    }.start();
    alphaStage.show();
  }
}

import gnu.io.*;					
import java.awt.MouseInfo;
import java.awt.AWTException; 
import java.awt.Dimension;
import java.awt.Robot; 
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
 
public class RobotExp {

	public static int xAmount;
	public static int yAmount;
	public static int currentX;
	public static int currentY;
	public static Dimension screenSize;
	public static Robot robot;
	public static int cursor;
	public static int[] last;
	public static int calib;
	
	public static void testMove() throws AWTException { 
		for (int i = 1; i < 255; i++)
		{
			//eyeT(i, 255);
			robot.delay(100);
		}
		robot.delay(500);
		for (int i = 1; i < 255; i++)
		{
			gyro(i, i, 255, 255);
			robot.delay(100);
		}
		
		System.out.println("All done!"); 
	} 
	
	
	/* Move the mouse based on the eye signal
	 * Input: eye signal (0 - 255)
	 */
	public static void eyeT(int control, int controlMax) throws AWTException
	{
		if (control == 0 || controlMax == 0) return;
		
		// Straight -> Left
		if ((control < yAmount) && (controlMax > xAmount))
		{

			eyeL();
			System.out.println("1New Y = " + control);
			System.out.println("1New X = " + controlMax);
			if (!(last[2] > 0))
			{
				last[2] -= 1;
			}
			else 
			{
				last[0] += 1;
			}
		}
		// Straight -> Right
		else if ((control < yAmount) && (controlMax < xAmount))
		{
			System.out.println("2New Y = " + control);
			System.out.println("2New X = " + controlMax);
			eyeR();
			if (!(last[3] > 0))
			{
				last[3] -= 1;
			}
			else
			{
				last[1] += 1;
			}
			
		}
		// Left -> Straight
		else if ((control >= yAmount) && (controlMax < xAmount))
		{
			System.out.println("3New Y = " + control);
			System.out.println("3New X = " + controlMax);
			eyeR();
			if (!(last[0] > 0))
			{
				last[0] -= 1;
			}
			else
			{
				last[2] += 1;
			}
		}
		// Right -> Straight
		else if ((control >= yAmount) && (controlMax > xAmount)) 
		{
			System.out.println("4New Y = " + control);
			System.out.println("4New X = " + controlMax);
			eyeL();
			if (!(last[1] > 0))
			{
				last[1] -= 1;
			}
			else
			{
				last[3] += 1;
			}
		}

	}
	public static void eyeL() throws AWTException
	{
		for (int i = 1; i < 10; i++)
		{
			currentX = currentX - 10 - i;
			robot.delay(cursor);
			//System.out.println("Moving Mouse to: " + currentX);
			robot.mouseMove(currentX, currentY);
		}
	}
	public static void eyeR() throws AWTException
	{
		for (int i = 1; i < 10; i++)
		{
			currentX = currentX + 10 + i;
			robot.delay(cursor);
			//System.out.println("Moving Mouse to: " + currentX);
			robot.mouseMove(currentX, currentY);
		}
	}
	
	public static void leftClick(Robot robot) throws AWTException {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public static void setCursor(int delay)
	{
		cursor = delay;
	}
/*
 * CONNECT TO SERIAL PORT TO READ DATA FROM RECEIVER
 */
	void connect( String portName ) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier( portName );
		if( portIdentifier.isCurrentlyOwned() ) {
			System.out.println( "Error: Port is currently in use" );
		} else {
			int timeout = 2000;
			CommPort commPort = portIdentifier.open( this.getClass().getName(), timeout );
 
			if( commPort instanceof SerialPort ) {
				SerialPort serialPort = ( SerialPort )commPort;
				serialPort.setSerialPortParams( 9600,
                                        SerialPort.DATABITS_8,
                                        SerialPort.STOPBITS_1,
                                        SerialPort.PARITY_NONE );
				
				InputStream in = serialPort.getInputStream();
				
				( new Thread( new SerialReader( in ) ) ).start();
			} else {
				System.out.println( "Error: Only serial ports are handled by this example." );
			}
		}
	}
 
	public static class SerialReader implements Runnable {
		InputStream in;
		public SerialReader( InputStream in ) {
			this.in = in;
		}
 
		public void run() {
			byte[] buffer = new byte[ 1 ];
			int len = -1;
			String sum = "";
			int x = 0;
			int y = 0;
			int gyroY = 0;
			int gyroZ = 0;
			int gyroTilt = 0;
			int gyroNod = 0;
			int oldGyroNod = 0;

			try {
				while( ( len = this.in.read( buffer ) ) > -1 ) {
					String out = new String( buffer, 0, len );
					
					if(out.contains("l")){
						//
						int posi_x=MouseInfo.getPointerInfo().getLocation().x;
						int posi_y=MouseInfo.getPointerInfo().getLocation().y;
						posi_x+=1;
						robot.mouseMove(posi_x,posi_y );
						System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
				
						
					}
					
					if(out.contains("r")){
						//
						int posi_x=MouseInfo.getPointerInfo().getLocation().x;
						int posi_y=MouseInfo.getPointerInfo().getLocation().y;
						posi_x-=1;
						robot.mouseMove(posi_x,posi_y );
						System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
				
						
					}
					
					if(out.contains("c")){
						//
						int posi_x= (Toolkit.getDefaultToolkit().getScreenSize().width)/2;
						int posi_y=MouseInfo.getPointerInfo().getLocation().y;
											
						robot.mouseMove(posi_x,posi_y );
						System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
				
						
					}
					
					if(out.contains("u")){
						//
						int posi_x=MouseInfo.getPointerInfo().getLocation().x;
						int posi_y=MouseInfo.getPointerInfo().getLocation().y;
						posi_y-=1;					
						robot.mouseMove(posi_x,posi_y );
						System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
				
						
					}
					
					if(out.contains("d")){
						//
						int posi_x=MouseInfo.getPointerInfo().getLocation().x;
						int posi_y=MouseInfo.getPointerInfo().getLocation().y;
						posi_y+=1;					
						robot.mouseMove(posi_x,posi_y );
						System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
				
						
					}
					
					if(out.contains("z")){
						
						robot.mousePress(InputEvent.BUTTON1_MASK);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
					
						System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
				
						
					}
					
					if(out.contains("a")){
											
											robot.mousePress(InputEvent.BUTTON1_MASK);
											robot.mouseRelease(InputEvent.BUTTON1_MASK);
											robot.mousePress(InputEvent.BUTTON1_MASK);
											robot.mouseRelease(InputEvent.BUTTON1_MASK);
										
											System.out.println("(" + MouseInfo.getPointerInfo().getLocation().x + ", " + MouseInfo.getPointerInfo().getLocation().y + ")");
									
											
										}

				}
			} catch( IOException e ) {
			}
		}
	}

	public static void main(String[] args) throws AWTException { 
		try {
			
			( new RobotExp() ).connect("COM3");
		} catch( Exception e ) {
			e.printStackTrace();
		}
		try {
			robot = new Robot();
			Point start = MouseInfo.getPointerInfo().getLocation();
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setCursor(50);
			currentX = start.x;
			currentY = start.y;
			last = new int[4];
			last[0] = 1;
			last[1] = 1;
			last[2] = 1;
			last[3] = 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


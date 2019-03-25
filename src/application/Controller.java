package application;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 */


public class Controller implements Initializable{
	
	//@Variables
	Timer timer;
	TimerTask task;
	private static Robot robot;
	
	@FXML private Button btn_start;
	@FXML private Button btn_clicked_coordinat;
	@FXML private Spinner<Integer> hour;
	@FXML private Spinner<Integer> minute;
	@FXML private Spinner<Integer> second;
	@FXML private Spinner<Integer> milisecond;
	@FXML private Pane pane;
	@FXML private Label info_label;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		SpinnerValueFactory<Integer> hourValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);
		SpinnerValueFactory<Integer> secondValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);
		SpinnerValueFactory<Integer> minuteValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);
		SpinnerValueFactory<Integer> milisecondValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999);
		
		this.hour.setValueFactory(hourValue);
		this.minute.setValueFactory(minuteValue);
		this.second.setValueFactory(secondValue);
		this.milisecond.setValueFactory(milisecondValue);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timer = new Timer();
	}
	
	
	@FXML
	public void btn_start(ActionEvent event) {
		LocalTime time = LocalTime.now();
		LocalDate dates = LocalDate.now();
		
		
		//Sistem Tarihini Milisecond Olarak Tutan Deðiþken
		
		long localtimes = System.currentTimeMillis();
		//Kullanýcýnýn Ýstediði Tarihi Milisecond Olarak Tutan Deðiþken
		long comeTime = ConverToMiliSecond(dates.getYear(),dates.getMonthValue(),dates.getDayOfMonth(),time.getHour(), minute.getValue(), second.getValue(), milisecond.getValue());
		//System.out.println("Local Time : " + localtimes);
		System.out.println("Baþlansgýç zamaný: " + (time));
		if(localtimes < comeTime) {
			info_label.setText("Baþladý");
			robot.mouseMove(MouseCoordinate.coordinate[0].getX(), MouseCoordinate.coordinate[0].getY());
			robot.mousePress(MouseEvent.BUTTON1_MASK);
			
			
			 task = new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
						if(System.currentTimeMillis() == comeTime) {
							try {
								int k = MouseInfo.getPointerInfo().getLocation().x;
								int z = MouseInfo.getPointerInfo().getLocation().y;
								robot.mouseMove(MouseCoordinate.coordinate[0].getX(), MouseCoordinate.coordinate[0].getY());
								robot.mousePress(MouseEvent.BUTTON1_MASK);
								robot.mouseRelease(MouseEvent.BUTTON1_MASK);
								robot.mouseMove(k,z);
								return;
							} catch (Exception e) {
								// TODO: handle exception
							}
				}
			}};
			
			timer.schedule(task, 0,1);
			
		}else if(localtimes > comeTime) {
			info_label.setText("Yerel zamandan ileri zaman seçiniz...");
		}
		
	}
	
	
	public void btn_clicked_coordinat(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("selected.fxml"));
			int height = Toolkit.getDefaultToolkit().getScreenSize().height;
			int width = Toolkit.getDefaultToolkit().getScreenSize().width;
			Stage stage = new Stage();
			Scene scene = new Scene(root,width,height);
			stage.setScene(scene);
			stage.setOpacity(0.01);
			stage.show();
			
			scene.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

				@Override
				public void handle(javafx.scene.input.MouseEvent event) {
					// TODO Auto-generated method stub
					int x = (int)event.getX();
					int y = (int)event.getY();
					
					if(event.getX() > 0 && event.getY() > 0) {
						MouseCoordinate point = new MouseCoordinate();
						point.setX(x);
						point.setY(y);
						MouseCoordinate.coordinate[0] = point;
						setDisabler(false);
						stage.close();
					}
				}
			
			});
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Date convert to milisecond function
	public long ConverToMiliSecond(int year,int mounth,int day, int hour,int minute, int second, int milisecond) {
		long result = 0;
		
		String Date = year+"/"+mounth+"/"+day+" "+hour+":"+minute+":"+second+":"+milisecond;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
		
		Date dt;
		try {
			dt = sdf.parse(Date);
			result = dt.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//Spinners Disable Controller
	public void setDisabler(boolean x) {
		this.hour.setDisable(x);
		this.minute.setDisable(x);
		this.second.setDisable(x);
		this.milisecond.setDisable(x);
	}
}

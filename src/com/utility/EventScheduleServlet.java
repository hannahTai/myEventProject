package com.utility;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns = "/EventScheduleServlet", 
		loadOnStartup = 100
		)
public class EventScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Timer timer; 
	
	public void destroy() {
		timer.cancel();
	}
  
	public void init() {	          		
		timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {			
				Set<String> deleteTempFileDirs = new HashSet<>();
				deleteTempFileDirs.add("/tempEventTitle");
				deleteTempFileDirs.add("/tempEvent");
				deleteTempFileDirs.add("/tempVenue");
				
				for(String dirName : deleteTempFileDirs) {
					File dir = new File(getServletContext().getRealPath(dirName));
					if(dir.exists()) {
						String[] contents = dir.list();
				    	for (int i = 0; i < contents.length; i++) {
				    		File file = new File(dir, contents[i]);
				    		file.delete();
				    	}
					}					
				}
				System.out.println("EventScheduleServlet is working! : " + new java.util.Date());
			}			 
		};		
//		Calendar now = Calendar.getInstance();
//		Calendar midnight = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)+1, 0, 0, 0);
//		long waitTime = midnight.getTimeInMillis() - now.getTimeInMillis();
//		timer.schedule(task, waitTime, 1000*60*60*24);
		timer.schedule(task, 1000*60, 1000*60*60);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

package com.bayside.app.opinion.war.systeminfo.controller;

import java.util.Timer;
import java.util.TimerTask;

public class TaskByTimer extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//定时执行的代码
		
		System.out.println("ssss");
	}
    public static void main(String args[]){
    	Timer timer = new Timer(true);
    	TaskByTimer task = new TaskByTimer();
    	timer.schedule(task,0,1*60*1000);//每四个小时执行一次task的run
    }
	
}

package main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TestProcess{
	
  	private InputStream in = null;   	
  	
  	private InputStream ein = null;
  	
  	private OutputStream out = null;
  	
  	private BufferedReader br = null;
  	
  	private BufferedReader ebr = null;
	
  	private Process process = null;
	
  	private String line = null;
	
  	private String errLine = null;
	
  	private Thread stdRun  = null;
    
  	private Thread errRun  = null;    
    

  public TestProcess() {	
  }	
	
  private void execCmd() throws IOException, InterruptedException{	 
		
	String[] cmd = {"java","-version"};
	
	process = Runtime.getRuntime().exec(cmd);
	
	/* 1 �T�u�v���Z�X�̓��̓X�g���[�����擾 */
	in = process.getInputStream(); 
	
	/* 2 �T�u�v���Z�X�̃G���[�X�g���[�����擾 */
	ein = process.getErrorStream();
	
	/* 3 �T�u�v���Z�X�̏o�̓X�g���[�����擾 �����ł͎g�p���܂���B*/
	out = process.getOutputStream();
	
	/* ��L��3�̃X�g���[���� finally �ŃN���[�Y���܂��B */
			
	try {
	/*��L 1 �̃X�g���[����ʃX���b�h�ŏo�͂��܂��B*/
	Runnable inputStreamThread = new Runnable(){
	  public void run(){		
	  	try {
	  	  System.out.println("Thread stdRun start");
	  	br = 
	  	  new BufferedReader(new InputStreamReader(in));
	  	while ((line = br.readLine()) != null) {
	  	  System.out.println(line);
	  	}
	  	System.out.println("Thread stdRun end");
	  	} catch (Exception e) {		
	  	  e.printStackTrace();      	
	 	}
	  }
	};
		
	/*��L 2 �̃X�g���[����ʃX���b�h�ŏo��*/
	Runnable errStreamThread = new Runnable(){
	  public void run(){		
	  	try {
	  	  System.out.println("Thread errRun start");
	  	ebr =
	  	  new BufferedReader(new InputStreamReader(ein));
	  	while ((errLine = ebr.readLine()) != null) {
	  	  System.err.println(errLine);
	  	}          	
	  	System.out.println("Thread errRun end");
	  	} catch (Exception e) {		
	  	  e.printStackTrace();      	
	  	}          
	  }
	};
		
	stdRun = new Thread(inputStreamThread);
	errRun = new Thread(errStreamThread);
		
	/* �X���b�h���J�n���܂��B */
	stdRun.start();        
	errRun.start();
		
	/*�v���Z�X���I�����Ă��Ȃ���ΏI������܂őҋ@*/
	int c = process.waitFor();
		
	/* �T�u�X���b�h���I������̂�ҋ@ */
	stdRun.join();
	errRun.join();
		
	/*�v���Z�X�I���R�[�h�o�� */
	System.out.println(c);
		
	} catch (Exception e) {		
	  	e.printStackTrace();		
	}    
	finally{
	  	if(br!=null)br.close();
	  	if(ebr!=null)ebr.close();
		
	  	/* �q�v���Z�X */
	  	if(in!=null)in.close();
	  	if(ein!=null)ein.close();
	  	if(out!=null)out.close();		
	}
  }

  public static void main(String[] args) {
	 
	  TestProcess SampleRunTime = new TestProcess();
	try {			
		SampleRunTime.execCmd();	
	} catch (Exception e) {		
		e.printStackTrace();
	}
  }
}
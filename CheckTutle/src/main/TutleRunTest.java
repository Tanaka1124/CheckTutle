package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TutleRunTest {
	String java = "javac";
	String blibPath = ".;C:\\forkCRiPS\\RonproEditor\\testbase\\lib\\blib.jar;C:\\forkCRiPS\\RonproEditor\\testbase\\lib\\obpro.jar;";
//	String className = "Test";
	String classPath = "C:\\Users\\sakailab\\Desktop\\testフォルダ\\阿慶田 眞子_lecture02_482";
	String className = "C:\\Users\\sakailab\\Desktop\\testフォルダ\\阿慶田 眞子_lecture02_482\\Confetti.java";
	Process process = null;
	private Thread stdRun = null;
	private BufferedReader br = null;
	private String line = null;
	private InputStream in = null;

	void run() throws IOException, InterruptedException {
		String[] commands = { java, "-classpath", blibPath+classPath, className };
		// String[] commands = { "java", "-version" };
		process = Runtime.getRuntime().exec(commands);
		in = process.getInputStream();
		in = process.getErrorStream();

		try {
			Runnable StreamThread = new Runnable() {
				public void run() {
					try {
						br = new BufferedReader(new InputStreamReader(in));
						while ((line = br.readLine()) != null) {
							System.out.println(line);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			stdRun = new Thread(StreamThread);
			stdRun.start();
//			int c = process.waitFor();
			stdRun.join();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();

			/* 子プロセス */
			if (in != null)
				in.close();
		}
	}

	public static void main(String[] args) {
		TutleRunTest trt = new TutleRunTest();
		try {
			trt.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Compile {
	String blibPath = ".;C:\\forkCRiPS\\RonproEditor\\testbase\\lib\\blib.jar;C:\\forkCRiPS\\RonproEditor\\testbase\\lib\\obpro.jar;";
	Process process = null;
	private InputStream in = null;
	private BufferedReader br = null;
	private String line = null;
	private Thread stdRun = null;

	void doCompile(File f) throws IOException {

		String[] commands = { "javac", "-classpath", blibPath + f.getParent(),
				f.toString() };
		process = Runtime.getRuntime().exec(commands);
		in = process.getInputStream();// 標準出力を出力
		in = process.getErrorStream();// エラー出力（コンパイルエラーはこっち）
		try {// 出力を受け取る
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
			stdRun.join();
			System.out.println("compiled " + f.getName().toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

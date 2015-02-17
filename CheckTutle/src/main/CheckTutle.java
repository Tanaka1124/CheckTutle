package main;

import javax.swing.UIManager;

public class CheckTutle {

	public static void main(String[] args) {
		initializeLookAndFeel();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame mf = new MainFrame();
				mf.init();
			}
		});
	}

	private static void initializeLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

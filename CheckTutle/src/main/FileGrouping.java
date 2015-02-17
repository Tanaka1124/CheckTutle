package main;

import java.io.File;

import java.util.ArrayList;

public class FileGrouping {

	String[] taskSearcher(File[] jf) {
		ArrayList<String> tasks = new ArrayList<>();
		for (File fileName : jf) {
			if (!tasks.contains(fileName.getName())) {
				tasks.add(fileName.getName());
			}
		}
		return (String[]) tasks.toArray(new String[0]);
	}

	String[] studentSercher(File FilesPath) {
		File[] studentFiles = FilesPath.listFiles();
		ArrayList<String> student = new ArrayList<>();
		for (File f : studentFiles) {
			student.add(f.getName());
		}

		return (String[]) student.toArray(new String[0]);
	}

	boolean[] taskGrouping(File[] jf, String tasks) {
		boolean[] group = new boolean[jf.length];
		for (int i = 0; i < jf.length; i++) {
			if (jf[i].getName().toString().equals(tasks)) {
				group[i] = true;
			} else {
				group[i] = false;
			}
		}

		return group;
	}

	public static void main(String[] args) {
		ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("test.java");
		String filename = "test.java";
		System.out.println(tasks.contains(filename));
	}
}

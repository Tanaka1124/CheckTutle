package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel mainPane = new JPanel();
	private JPanel bottomPane = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JProgressBar pbar = new JProgressBar();
	private JLabel bottomText = new JLabel();
	private JPanel studentPane = new JPanel();
	private JButton[] studentButtons;
	private SearchJavaFile sjf = new SearchJavaFile();
	private FileGrouping fg = new FileGrouping();
	private Compile cp = new Compile();

	private File selectedDir;
	private String[] tasks;
	private String[] students;
	private boolean[][] groups;
	private File[] javaFiles = null;

	public String DESKTOP_PASS;

	private int progess = 0;

	void init() {
		setBounds(100, 100, 800, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("CheckTutle");

		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		JMenuItem openDir = new JMenuItem("OpenFolder");
		menu.add(openDir);
		openDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// �f�B���N�g���w��
				DESKTOP_PASS = new File(System.getProperty("user.home"),
						"Desktop").getPath();
				JFileChooser filechooser = new JFileChooser(DESKTOP_PASS);
				filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int selected = filechooser.showOpenDialog(null);
				if (selected == JFileChooser.APPROVE_OPTION) {
					selectedDir = filechooser.getSelectedFile();// chooser

					// java�t�@�C�����擾����D
					sjf.clear();
					javaFiles = sjf.listFiles(selectedDir.toString(), "*.java");

					// ���k�ꗗ���쐬����
					students = fg.studentSercher(selectedDir);

					studentButtons = new JButton[students.length];

					for (int i = 0; i < students.length; i++) {
						studentButtons[i] = new JButton(students[i]);
						studentPane.add(studentButtons[i]);
						studentButtons[i]
								.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										// �{�^�����������炻�̊w���̃t�H���_����\���H
										// ����Ƃ�class�t�@�C���̂݁H
										// ���������R���p�C���͍ŏ��Ɉꊇ�ōs���ׂ����ǂ���

									}
								});
					}

					pbar.setValue(progess);// progeress�o�[
					pbar.setMaximum(javaFiles.length);
					pbar.setMinimum(0);

					int option = JOptionPane.showConfirmDialog(menuBar,
							"�S�Ă�Java�t�@�C�����R���p�C�����܂����H�i���\���Ԃ�������܂��j", "�ăR���p�C��",
							JOptionPane.YES_NO_OPTION);

					if (option == JOptionPane.YES_OPTION) {
						try {
							for (File f : javaFiles) {
								if(!cp.doCompile(f))
									JOptionPane.showMessageDialog(menuBar, "blib���C�u������������܂���");
								progess++;
								pbar.setValue(progess);

							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if (option == JOptionPane.NO_OPTION) {

					}
					setVisible(true);
					// �R���p�C������

					// for (File i : javaFiles) {// �擾�����t�@�C���̊m�F
					// System.out.println(i);
					// }

					if (javaFiles.length != 0) {
						tasks = fg.taskSearcher(javaFiles); // ���݂���ۑ��������
						groups = new boolean[tasks.length][javaFiles.length];
						for (int i = 0; i < tasks.length; i++) { // �ۑ育�Ƃɕ��ނ���
							groups[i] = fg.taskGrouping(javaFiles, tasks[i]);
						}
					}

					for (String t : students) {
						System.out.println(t);
					}
					// for(String t:tasks){
					// System.out.println(t);
					// }
					// System.out.println("------------------------------------------------------");
					// for(int i = 0;i<groups[1].length;i++){
					// System.out.println(groups[0][i]);
					// }

				}
			}
		});
		JMenuItem compile = new JMenuItem("AllFileCompile");
		menu.add(compile);
		compile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(menuBar,
						"�S�Ă�Java�t�@�C�����R���p�C�����܂����H�i���\���Ԃ�������܂��j", "�ăR���p�C��",
						JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					try {
						if (javaFiles != null) {
							for (File f : javaFiles) {
								if(!cp.doCompile(f))
									JOptionPane.showMessageDialog(menuBar, "blib���C�u������������܂���");
								progess++;
								pbar.setValue(progess);
							}
						}else{
							JOptionPane.showMessageDialog(menuBar, "�R���p�C������Java�t�H���_���������t�@�C�����w�肵�Ă�������");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (option == JOptionPane.NO_OPTION) {

				}

			}
		});

		add(mainPane, BorderLayout.CENTER);
		// bottomText.setText("�R���p�C���͌��\���Ԃ�����܂��D���삵�Ă��邩�ǂ����̓R���\�[���Ŋm�F���ĉ������@��������");
		bottomPane.add(bottomText);
		bottomPane.add(pbar);
		add(bottomPane, BorderLayout.PAGE_END);
		studentPane.setLayout(new BoxLayout(studentPane, BoxLayout.Y_AXIS));
		add(studentPane, BorderLayout.WEST);

		setVisible(true);
	}
}

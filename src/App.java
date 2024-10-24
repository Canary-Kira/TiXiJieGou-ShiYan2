import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    private JFrame frame;
    private JComboBox<String> styleComboBox;
    private JTextArea resultArea;
    private JTextArea codeArea;
    private JLabel diagramLabel;
    private JTextField filePathField;

    private File selectedFile; // 记录用户选择的文件
    private String typeName = "";//记录用户选择的体系风格

    public App() {
        initialize();
    }

    private void initialize() {
        // 创建主窗口
        frame = new JFrame("软件体系结构实验二");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());


        // 顶部选择体系结构风格
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("选择体系结构风格:");
        styleComboBox = new JComboBox<>(new String[]{"请选择...", "主程序-子程序", "面向对象", "事件系统", "管道-过滤器"});
        styleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDisplay();
            }
        });
        topPanel.add(label);
        topPanel.add(styleComboBox);

        // 文件选择部分
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout());
        filePathField = new JTextField(30);
        JButton fileButton = new JButton("选择文件");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
        JButton processButton = new JButton("处理文件");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    kwicFile(); // 处理文件
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        filePanel.add(filePathField);
        filePanel.add(fileButton);
        filePanel.add(processButton); // 添加处理文件按钮

        // 结果展示区域
        resultArea = new JTextArea(5, 50);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // 代码展示区域
        codeArea = new JTextArea(5, 50);
        codeArea.setEditable(false);
        JScrollPane codeScrollPane = new JScrollPane(codeArea);

        // 原理图展示区域
        diagramLabel = new JLabel();
        diagramLabel.setHorizontalAlignment(JLabel.CENTER);

        // 将所有组件添加到窗口中
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(filePanel, BorderLayout.SOUTH);
        frame.add(resultScrollPane, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.add(codeScrollPane);
        rightPanel.add(diagramLabel);
        frame.add(rightPanel, BorderLayout.CENTER);

        // 显示窗口
        frame.setVisible(true);


    }

    private void updateDisplay() {
        typeName = (String) styleComboBox.getSelectedItem();
        System.out.println(typeName);
        if ( typeName != null && ! typeName.equals("请选择...")) {
            resultArea.setText("处理结果：\n" + "您选择的体系结构风格是: " + typeName);
            updateCodeArea();
            // 显示原理图
            String imagePath = "src//" + typeName + ".png";
            diagramLabel.setIcon(new ImageIcon(imagePath));

        }
        if(selectedFile!=null){
            updateResultArea();
            System.out.println(selectedFile.getAbsolutePath());
        }

    }

    //更新核心代码
    private void updateCodeArea(){
        File codeTxt = new File("src//code.txt");
        ArrayList<String> codeList = new ArrayList<>();
        if(typeName != null){
            try (BufferedReader reader = new BufferedReader(new FileReader(codeTxt))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.equals("-------------------------------------------------")){
                        codeList.add(content.toString());
                        //清空容器
                        content.setLength(0);
                    }else{
                        content.append(line).append("\n");
                    }
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "无法读取文件: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            if(typeName.equals("主程序-子程序")){
                codeArea.setText(codeList.get(0));
            }else if(typeName.equals("面向对象")){
                codeArea.setText(codeList.get(1));
            }else if(typeName.equals("事件系统")){
                codeArea.setText(codeList.get(2));
            }else if(typeName.equals("管道-过滤器")){
                codeArea.setText(codeList.get(3));
            }
        }

    }

    //更新输出结果
    private void updateResultArea(){
        if(selectedFile == null){
            JOptionPane.showMessageDialog(frame, "当前没有选择任何文件", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        // 读取文件内容并显示在结果区域中
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            resultArea.setText(typeName+"风格:\n"+content.toString()); // 将文件内容显示在结果区域
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "无法读取文件: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile(); // 保存选择的文件
            filePathField.setText(selectedFile.getAbsolutePath());//更新文件路径
            updateResultArea();

        }
    }

    //处理文件
    private void  kwicFile() throws IOException {
        if (selectedFile != null && typeName != null) {
           if(typeName.equals("主程序-子程序")){
                Main_Sub.excute(selectedFile.getAbsolutePath(),"src//output.txt");
                showOutput();
           }else if(typeName.equals("面向对象")){
                OOP.Main.excute(selectedFile.getAbsolutePath(),"src//output.txt");
                showOutput();
           }else if(typeName.equals("事件系统")){
                EventSystem.Main.excute(selectedFile.getAbsolutePath(),"src//output.txt");
                showOutput();
           }else if(typeName.equals("管道-过滤器")){
                Pipe_Filter.Main.excute(selectedFile.getAbsolutePath(),"src//output.txt");
                showOutput();
           }
        } else {
            JOptionPane.showMessageDialog(frame, "请先选择文件和体系风格", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showOutput(){
        //用于更新输出文件显示结果
        File temp = selectedFile;
        selectedFile = new File("src//output.txt");
        updateResultArea();
        selectedFile = temp;
        temp = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}
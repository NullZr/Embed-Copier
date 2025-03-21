package com.nekos;

import com.nekos.JDA.EmbedCopier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class EmbedCopierGUI extends JFrame {
    private JTextField tokenField;
    private JTextField channelIdField;
    private JTextField messageIdField;
    private JTextField messageIndexField;
    private JTextArea jsonArea;
    private JButton connectButton;
    private JButton fetchButton;
    private JButton copyButton;
    private EmbedCopier embedCopier;

    // Цвета
    private static final Color BACKGROUND_COLOR = new Color(248, 241, 241); // #F8F1F1
    private static final Color PINK_ACCENT = new Color(245, 198, 203);     // #F5C6CB
    private static final Color TEXT_COLOR = new Color(74, 74, 74);         // #4A4A4A

    // Панели
    private JPanel tokenPanel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel jsonPanel;

    public EmbedCopierGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Embed Copier");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Панель ввода токена (видна изначально)
        tokenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        tokenPanel.setBackground(BACKGROUND_COLOR);
        JLabel tokenLabel = new JLabel("Bot Token:");
        tokenLabel.setForeground(TEXT_COLOR);
        tokenPanel.add(tokenLabel);
        tokenField = new JTextField(20);
        tokenField.setBorder(BorderFactory.createLineBorder(PINK_ACCENT, 1, true));
        tokenPanel.add(tokenField);
        connectButton = new JButton("Connect");
        styleButton(connectButton);
        connectButton.addActionListener(e -> connectBot());
        tokenPanel.add(connectButton);

        // Панель ввода ID (скрыта изначально)
        inputPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        inputPanel.setVisible(false);

        JLabel channelLabel = new JLabel("Channel ID:");
        channelLabel.setForeground(TEXT_COLOR);
        inputPanel.add(channelLabel);
        channelIdField = new JTextField();
        channelIdField.setBorder(BorderFactory.createLineBorder(PINK_ACCENT, 1, true));
        inputPanel.add(channelIdField);

        JLabel messageLabel = new JLabel("Message ID:");
        messageLabel.setForeground(TEXT_COLOR);
        inputPanel.add(messageLabel);
        messageIdField = new JTextField();
        messageIdField.setBorder(BorderFactory.createLineBorder(PINK_ACCENT, 1, true));
        inputPanel.add(messageIdField);

        // Панель ввода индекса (скрыта изначально)
        JLabel messageIndexLabel = new JLabel("Message Index:");
        messageLabel.setForeground(TEXT_COLOR);
        inputPanel.add(messageIndexLabel);
        messageIndexField = new JTextField();
        messageIndexField.setText("0");
        messageIndexField.setBorder(BorderFactory.createLineBorder(PINK_ACCENT, 1, true));
        inputPanel.add(messageIndexField);

        // Панель кнопок (скрыта изначально)
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setVisible(false);

        fetchButton = new JButton("Fetch Embed");
        styleButton(fetchButton);
        fetchButton.addActionListener(e -> {
            String channelId = channelIdField.getText();
            String messageId = messageIdField.getText();
            String messageIndex = messageIndexField.getText();
            String json = embedCopier.getEmbedJson(channelId, messageId, messageIndex);
            jsonArea.setText(json);
        });
        buttonPanel.add(fetchButton);

        copyButton = new JButton("Copy JSON");
        styleButton(copyButton);
        copyButton.addActionListener(e -> {
            String json = jsonArea.getText();
            if (!json.isEmpty()) {
                StringSelection selection = new StringSelection(json);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                JOptionPane.showMessageDialog(null, "JSON скопирован в буфер обмена");
            }
        });
        buttonPanel.add(copyButton);

        // Панель для inputPanel и buttonPanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(tokenPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Область JSON (скрыта изначально)
        jsonArea = new JTextArea();
        jsonArea.setEditable(false);
        jsonArea.setBackground(BACKGROUND_COLOR);
        jsonArea.setForeground(TEXT_COLOR);
        jsonArea.setBorder(BorderFactory.createLineBorder(PINK_ACCENT, 1, true));
        JScrollPane scrollPane = new JScrollPane(jsonArea);
        scrollPane.setBorder(null);
        jsonPanel = new JPanel(new BorderLayout());
        jsonPanel.setBackground(BACKGROUND_COLOR);
        jsonPanel.add(scrollPane, BorderLayout.CENTER);
        jsonPanel.setVisible(false);

        // Размещение панелей
        add(topPanel, BorderLayout.NORTH);
        add(jsonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(PINK_ACCENT);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Кастомный UI для скругления
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g2, c);
                g2.dispose();
            }
        });

        // Эффект при наведении и нажатии
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(230, 180, 180)); // Темнее при наведении
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PINK_ACCENT); // Оригинальный цвет
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(200, 150, 150)); // Еще темнее при нажатии
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(PINK_ACCENT);
            }
        });
    }

    private void connectBot() {
        String token = tokenField.getText().trim();
        if (token.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter the bot's token", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            embedCopier = new EmbedCopier(token);
            tokenPanel.setVisible(false);
            inputPanel.setVisible(true);
            jsonPanel.setVisible(true);
            buttonPanel.setVisible(true);
            revalidate();
            repaint();
            JOptionPane.showMessageDialog(this, "Bot successfully connected");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmbedCopierGUI::new);
    }
}
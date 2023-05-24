package bsu.rfe.java.group6.lab6.Kuksa.varA1;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    // Константы, задающие размер окна приложения, если оно не распахнуто на весь экран
    private static final int WIDTH = 700, HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem pauseMenuSpecialItem;
    private JMenuItem resumeMenuItem;

    private Field field = new Field(); // Конструктор главного окна приложения

    // Главный метод приложения
    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);// Отцентрировать окно приложения на экране
        setExtendedState(MAXIMIZED_BOTH); // Установить начальное состояние окна развёрнутым на весь экран
        JMenuBar menuBar = new JMenuBar(); // Создать меню
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
                    pauseMenuItem.setEnabled(true); // Ни один из пунктов меню не являются доступными - сделать доступным "Паузу"
                }
                pauseMenuSpecialItem.setEnabled(true);
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение"){
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
                pauseMenuSpecialItem.setEnabled(false);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);

        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
                pauseMenuSpecialItem.setEnabled(true);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);

        Action pauseSpecialAction = new AbstractAction("Приостановить движение шаров с R < 10"){
            public void actionPerformed(ActionEvent event) {
                field.pauseSpecial();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(true);
                pauseMenuSpecialItem.setEnabled(false); ///
            }
        };
        pauseMenuSpecialItem = controlMenu.add(pauseSpecialAction);
        pauseMenuSpecialItem.setEnabled(false);

        getContentPane().add(field, BorderLayout.CENTER); // Добавить в центр граничной компоновки поле Field
    }

    public static void main(String[] args) {
        // Создать и сделать видимым главное окно приложения
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
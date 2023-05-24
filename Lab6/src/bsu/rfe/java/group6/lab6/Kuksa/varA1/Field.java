package bsu.rfe.java.group6.lab6.Kuksa.varA1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class Field extends JPanel {
    private boolean paused, pauseSpecial; // Флаг приостановленности движения
    //private boolean pausedSpecial; // Динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
// При создании его экземпляра используется анонимный класс, реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
// Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });
    // Конструктор класса BouncingBall
    public Field() {
        setBackground(Color.BLACK); // Установить цвет заднего фона белым
        repaintTimer.start(); // Запустить таймер
    }

    public void paintComponent(Graphics g) { // Унаследованный от JPanel метод перерисовки компонента
        super.paintComponent(g); // Вызвать версию метода, унаследованную от предка
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball: balls) { // Последовательно запросить прорисовку от всех мячей из списка
            ball.paint(canvas);
        }
    }

    // Метод добавления нового мяча в список
    public void addBall() {
//Заключается в добавлении в список нового экземпляра BouncingBall
// Всю инициализацию положения, скорости, размера, цвета
// BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBall(this));
    }
    // Метод синхронизированный, т.е. только один поток может одновременно быть внутри
    public synchronized void pause() {
        paused = true; // Включить режим паузы
    }

    public synchronized void pauseSpecial() {
        pauseSpecial = true; // Включить режим паузы для R < 10
    }

    // Метод синхронизированный, т.е. только один поток может одновременно быть внутри
    public synchronized void resume() {
        paused = false; // Выключить режим паузы
        pauseSpecial = false;
        notifyAll(); // Будим все ожидающие продолжения потоки
    }

    // Синхронизированный метод проверки, может ли мяч двигаться (не включен ли режим паузы?)
    public synchronized void canMove(BouncingBall ball) throws
            InterruptedException {
        if (paused) {
            wait(); // Если режим паузы включен, то поток, зашедший внутрь данного метода, засыпает
        }
        if (pauseSpecial && (ball.getRadius() < 10)){
            wait();
        }
    }
}

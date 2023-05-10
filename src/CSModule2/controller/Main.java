package CSModule2.controller;

import CSModule2.sevice.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        int choose;

        do {
            showMenu();
            choose = Integer.parseInt(scan.nextLine());

            switch(choose) {
                case 1:
                    inputQuestions();
                    break;
                case 2:
                    showGame();
                    break;
                case 3:
                    System.out.println("Thoat Game!!!");
                    break;
                default:
                    System.out.println("Nhap sai!!!");
                    break;
            }
        } while(choose != 3);
    }

    static void showGame() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<Question> questions = new ArrayList<>();

        try {
            fis = new FileInputStream("C:\\Users\\Admin BVCN88 02\\IdeaProjects\\Module2\\src\\CSModule2\\data\\question.txt");
            ois = new ObjectInputStream(fis);

            for(;;) {
                Object obj;
                try {
                    obj = ois.readObject();
                } catch(Exception e) {
                    obj = null;
                }
                if(obj == null) {
                    break;
                }
                questions.add((Question) obj);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //du lieu cau hoi
        int correct = 0;
        int total = questions.size();

        //sinh ngau nhien 15 cau hoi.
        if(questions.size() > 15) {
            List<Question> testList = new ArrayList<>();
            Random random = new Random();

            for (int i = 1; i <= 15; i++) {
                int index = random.nextInt(questions.size());
                testList.add(questions.remove(index));
            }

            questions = testList;
        }

        for (Question question : questions) {
            question.showQuestion();
            int result = Integer.parseInt(scan.nextLine());
            if(question.checkResult(result)) {
                correct++;
            }
        }

        System.out.format("\nKet qua test : %d/%d\n", correct, total);
    }

    static void inputQuestions() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream("C:\\Users\\Admin BVCN88 02\\IdeaProjects\\Module2\\src\\CSModule2\\data\\question.txt", true);
            oos = new ObjectOutputStream(fos);

            for(;;) {
                Question question = new Question();
                question.input();

                oos.writeObject(question);

                System.out.println("Tiếp tu nhập câu hỏi Có/Không");
                String option = scan.nextLine();
                if(option.equalsIgnoreCase("No")) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    static void showMenu() {
        System.out.println("MENU");
        System.out.println("1. Nhập câu hỏi:");
        System.out.println("2. Game");
        System.out.println("3. Thoat");
    }
}

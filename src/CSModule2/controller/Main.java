package CSModule2.controller;

import CSModule2.account.AccountManage;
import CSModule2.sevice.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void adminMenu(AccountManage accountManage, Question question) {
        int choice = -1;

        do {


            System.out.println("ADMIN");
            System.out.println("1.Xóa tài khoản");
            System.out.println("2. Thêm câu hỏi");
            System.out.println("3. Hiển thị tất cả tài khoản");
            System.out.println("4. Xóa tất cả tài khoản");
            System.out.println("0.Thoát");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            switch (choice) {
                case 1:
                    accountManage.deleteAccount();
                    break;
                case 2:
                    question.input();
                    break;
                case 3:
                    accountManage.displayAllAccount();
                    break;
                case 4:
                    accountManage.deleteAccount();
                    break;
                case 0:
                    choice = 0;
                    break;


            }


        } while (choice != 0);
    }


    public static void userMenu(AccountManage accountManage, Question question) {
        int choice = -1;
        do {
            System.out.println("User");
            System.out.println("1.Chơi game:");
            System.out.println("2.Thêm tài khoản của bạn");
            System.out.println("3. Thoát");
            System.out.println("Nhập lựa chọn của bạn");
            try {
                choice = Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            switch (choice) {
                case 1:
                    showGame();
                    break;
                case 2:
                    accountManage.updateAccount();
                    break;
                case 3:
                    choice = 0;
                    break;
                default:
                    System.out.println("Lựa chọn không đúng");
                    break;
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        AccountManage accountManage = new AccountManage();
        Question question = new Question();
        int choice = -1;
        do {
            System.out.println("-------- MENU ---------");
            System.out.println("1. Đăng kí");
            System.out.println("2. Đăng nhập");
            System.out.println("3. Quên mật khẩu");
            System.out.println("0. Thoát");
            System.out.println("Mời nhập lựa chọn :");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error " + e.getMessage());
                continue;
            }
            switch (choice) {
                case 1:
                    accountManage.createAccount();
                    break;
                case 2:
                    String userName = accountManage.login();
                    if (userName != null) {
                        if (userName.equals("chu123")) {
                            adminMenu(accountManage, question);
                        } else userMenu(accountManage, question);
                    }
                    break;
                case 3:
                    accountManage.findPassWord();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không phù hợp");
                    break;
            }

        } while (choice != 0);


    }

    int choose;


    public static void showGame() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<Question> questions = new ArrayList<>();

        try {
            fis = new FileInputStream("C:\\Users\\Admin BVCN88 02\\IdeaProjects\\Module2\\src\\CSModule2\\data\\question.txt");
            ois = new ObjectInputStream(fis);

            for (; ; ) {
                Object obj;
                try {
                    obj = ois.readObject();
                } catch (Exception e) {
                    obj = null;
                }
                if (obj == null) {
                    break;
                }
                questions.add((Question) obj);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (fis != null) {
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
        if (questions.size() > 15) {
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
            int result = Integer.parseInt(scanner.nextLine());
            if (question.checkResult(result)) {
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

            for (; ; ) {
                Question question = new Question();
                question.input();

                oos.writeObject(question);

                System.out.println("Tiếp tu nhập câu hỏi Có/Không");
                String option = scanner.nextLine();
                if (option.equalsIgnoreCase("No")) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}




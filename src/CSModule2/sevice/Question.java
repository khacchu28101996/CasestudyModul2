package CSModule2.sevice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question implements Serializable {
    private static final long serialVersionUID = 2906642554793891381L;
    String title;
    List<String> options = new ArrayList<>();
    int result;

    public Question() {
    }

    public void input() throws NumberFormatException{
        Scanner scan = new Scanner(System.in);

        System.out.println("Nhập câu hỏi ");
        title = scan.nextLine();

        System.out.println("Nhập lựa chọn: ");

        for (int index=1;index<=4 ;++index ) {
            System.out.format("\nOption %d: ", index++);
            String option = scan.nextLine();
            options.add(option);

        }
        System.out.println("Nhập đáp án đúng: ");
        result = Integer.parseInt(scan.nextLine());
    }

    public void showQuestion() {
        System.out.println("Question: " + title);
        int index = 1;
        for (String option : options) {
            System.out.println((index++) + ". " + option);
        }
        System.out.println("Đáp án: ");
    }

    public boolean checkResult(int result) {
        return this.result == result;
    }
}

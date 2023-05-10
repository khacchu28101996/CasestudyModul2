package CSModule2.account;


import model.IOFileInterface;
import model.ValidateData;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountManage implements IOFileInterface<Account> {
    private final ArrayList<Account> accounts;
    private final Scanner scanner;

    private final String accountPath = "C:\\Users\\Admin BVCN88 02\\IdeaProjects\\Module2\\src\\CSModule2\\data\\account.txt";
    private final String loggingUserPath = "C:\\Users\\Admin BVCN88 02\\IdeaProjects\\Module2\\src\\CSModule2\\data\\login.txt";

    public AccountManage() {
        accounts = readFile(accountPath);
        scanner = new Scanner(System.in);
    }

    @Override
    public void writeFile(ArrayList<Account> accounts, String accountPath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(accountPath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Account> readFile(String accountPath) {
        ArrayList<Account> newAccounts = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(accountPath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            newAccounts = (ArrayList<Account>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newAccounts;
    }

    public Account createAccount() {
        int id = getLastAccountId() + 1;
        String userName = getValidUserName();
        String passWord = getValidPassWord();
        String phoneNumber = getValidPhoneNumber();
        String email = getValidEmail();
        Account account = new Account(id, userName, passWord, phoneNumber, email);
        accounts.add(account);
        writeFile(accounts, accountPath);
        System.out.println("You have successfully created an account");
        return account;
    }

    private String getValidPhoneNumber() {
        boolean check;
        String phoneNumber;
        do {
            System.out.println("Mời bạn nhập số điện thoại (10 số, bắt đầu bằng số 0)");
            phoneNumber = scanner.nextLine();
            check = ValidateData.validatePhone(phoneNumber);
            if (!check) System.out.println("Nhập sai vui lòng nhập lại");
        } while (!check);
        return phoneNumber;
    }

    private String getValidPassWord() {
        boolean check;
        String passWord;
        do {
            System.out.println("Nhập mật khẩu (Tối thiểu 6 k tự, Bắt đầu bằng chữ in hoa)");
            passWord = scanner.nextLine();
            check = ValidateData.validatePassWord(passWord);
            if (!check) System.out.println("Nhập sai vui lòng nhập lại");
        } while (!check);
        return passWord;
    }

    private String getValidUserName() {
        boolean checkRegex;
        boolean checkUserNameExisted;
        String userName;
        do {
            System.out.println("Nhập tài khoản (i nhất 6 kí tự)");
            userName = scanner.nextLine();
            checkRegex = ValidateData.validateUserName(userName);
            checkUserNameExisted = checkUserNameExisted(userName);
            if (!checkRegex || checkUserNameExisted)
                System.out.println("Nhập lại! Tên không hợp lệ hoặc không tồn tại");
        } while (!checkRegex || checkUserNameExisted);
        return userName;
    }

    private String getValidEmail() {
        boolean check;
        String email;
        do {
            System.out.println("Nhập Email (example: example@gmail.com)");
            email = scanner.nextLine();
            check = ValidateData.validateEmail(email);
            if (!check) System.out.println("Không hợp lệ vui lòng nhập lại");
        } while (!check);
        return email;
    }

    public int getLastAccountId() {
        if (accounts.size() == 0) return 0;
        else return accounts.get(accounts.size() - 1).getId();
    }

    public void displayAllAccount() {
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    public boolean checkUserNameExisted(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equalsIgnoreCase(userName)) return true;
        }
        return false;
    }

    public boolean checkLogin(String userName, String passWord) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName) && account.getPassWord().equals(passWord)) {
                return true;
            }
        }
        return false;
    }

    public String login() {
        boolean checkLogin;
        String userName;
        String passWord;
        int count = 0;
        do {
            count++;
            System.out.println("Nhập tài khoản");
            userName = scanner.nextLine();
            System.out.println("Nhập mật khẩu");
            passWord = scanner.nextLine();
            checkLogin = checkLogin(userName, passWord);
            if (count == 3) {
                System.out.println("Bạn nhập sai quá 3 lần " +
                        "Nếu chưa có tài khoản vui longf nhấn đăng kí!Nếu có tài khoản nhâ quên mật khâu");
                return null;
            }
            if (checkLogin) {
                System.out.println("Đăng nhập thành công ! Xin Chào " + userName);
            } else {
                System.out.println("Vui lòng nhập lại tên người dùng hoặc mật khẩu không đúng!!");
            }
        } while (!checkLogin);
        ArrayList<Account> loggingUserList = new ArrayList<>();
        loggingUserList.add(getAccountByUserName(userName));
        writeFile(loggingUserList, loggingUserPath);
        return userName;
    }

    public Account getAccountById() {
        displayAllAccount();
        int id = -1;
        try {
            System.out.println("Nhập ID tài khoản bạn muốn");
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (Account account : accounts) {
            if (account.getId() == id) return account;
        }
        return null;
    }

    public void deleteAccount() {
        Account deleteAccount = getAccountById();
        if (deleteAccount == null) System.out.println("Không có ID");
        else {
            accounts.remove(deleteAccount);
            System.out.println("Xóa thành công");
        }
        writeFile(accounts, accountPath);
    }

    public Account getAccountByUserName(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) return account;
        }
        return null;
    }

    public void updateAccount() {
        ArrayList<Account> loggingUserList = readFile(loggingUserPath);
        for (Account account : accounts) {
            if (account.getUserName().equals(loggingUserList.get(0).getUserName())) {
                String passWord = getValidPassWord();
                String phoneNumber = getValidPhoneNumber();
                String email = getValidEmail();
                account.setPassWord(passWord);
                account.setPhoneNumber(phoneNumber);
                account.setEmail(email);
                System.out.println("Thêm thông tin thành công");
                writeFile(accounts, accountPath);
                break;
            }
        }
    }

    public void findPassWord() {
        System.out.println("Nhập tài khoản");
        String userName = scanner.nextLine();
        Account account = getAccountByUserName(userName);
        if (account != null) {
            System.out.println("Nhập Số điện thoại của bạn");
            String phoneNumber = scanner.nextLine();
            if (account.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Mật khẩu của bạn là: " + account.getPassWord());
            } else {
                System.out.println("Số điện thoại không đúng !!");
            }
        } else {
            System.out.println("Tài khoản không đúng !!");
        }
    }
    public void deleteAllAccount(){
        accounts.removeIf(account -> !account.getUserName().equals("chu123"));
        writeFile(accounts,accountPath);
        System.out.println("Xóa thành công !!");
    }

}

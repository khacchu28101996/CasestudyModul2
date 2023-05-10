package model;
    import java.util.regex.Pattern;

    public class ValidateData {
        private static Pattern pattern;
        private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        private static final String PHONE_REGEX = "^0[0-9]{9}$";
        private static final String ACCOUNT_REGEX = "^[A-Za-z0-9_]{6,}$";
        private static final String PASSWORD_REGEX = "^[A-Z][A-Za-z0-9]{5,}$";

        public static boolean validateEmail(String email) {
            pattern = Pattern.compile(EMAIL_REGEX);
            return pattern.matcher(email).matches();
        }

        public static boolean validatePhone(String phone) {
            pattern = Pattern.compile(PHONE_REGEX);
            return pattern.matcher(phone).matches();
        }

        public static boolean validateUserName(String userName) {
            pattern = Pattern.compile(ACCOUNT_REGEX);
            return pattern.matcher(userName).matches();
        }

        public static boolean validatePassWord(String passWord) {
            pattern = Pattern.compile(PASSWORD_REGEX);
            return pattern.matcher(passWord).matches();
        }
    }


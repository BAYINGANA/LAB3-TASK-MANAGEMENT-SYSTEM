package utils;

import utils.exceptions.InvalidInputException;

public class ValidationUtils {

	public static boolean isNonEmpty(String s) {
		return s != null && !s.trim().isEmpty();
	}

	public static boolean isPositiveInt(int n) {
		return n > 0;
	}

	public static boolean isValidEmail(String email) {
		if (email == null) return false;
		email = email.trim();
		if (email.length() < 3) return false;
		int at = email.indexOf('@');
		if (at <= 0 || at == email.length() - 1) return false;
		if (email.indexOf('@', at + 1) != -1) return false;
		String domain = email.substring(at + 1);
		if (!domain.contains(".")) return false;
		return true;
	}

    public static void isValidName(String name) throws InvalidInputException {
        if (name.matches(".*\\d.*")){
            throw new InvalidInputException("Name CANNOT contain numbers");
        }
        if(name.isEmpty() || name == null){
            throw new InvalidInputException("Name CANNOT be empty");
        }
    }

}


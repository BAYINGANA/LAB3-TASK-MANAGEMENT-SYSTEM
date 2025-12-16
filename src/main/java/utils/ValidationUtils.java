package utils;

import utils.exceptions.InvalidInputException;

public class ValidationUtils {
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

    public static boolean isValidName(String name) throws InvalidInputException {
        if (name.matches(".*\\d.*")){
            throw new InvalidInputException("Name CANNOT contain numbers");
        }
        else if(name.matches(".*[^a-zA-Z0-9]")){
            throw new InvalidInputException("Name CANNOT contain symbols");
        }
        else if(name.isEmpty()){
            throw new InvalidInputException("Name CANNOT be empty");
        }
        return false;
    }

    public static boolean isNotEmpty(String userInput){
        boolean valid = !userInput.isEmpty();
        if(!valid){
            System.out.println("This field CANNOT be empty");
        }
        return valid;
    }

    public static boolean isValidPassword(String password) throws InvalidInputException{
        if(!password.matches(".*[a-zA-Z].*")){
            throw new InvalidInputException("Please include alphabetic characters!!");
        }
        else if(!password.matches(".*\\d.*")){
            throw new InvalidInputException("Password not Strong. try including numbers!!");
        }
        else if(!password.matches(".*[^a-zA-Z0-9].*")){
            throw new InvalidInputException("Password is not Strong.try including symbols!!");
        }
        return true;
    }

    public static boolean isValidDate(String date) throws InvalidInputException{
        String pattern = "^\\d{2}/\\d{2}/\\d{4}$";

        if (!date.matches(pattern)) {
            throw new InvalidInputException("Invalid date format! Expected dd/MM/yyyy");
        }

        return true;
    }
}

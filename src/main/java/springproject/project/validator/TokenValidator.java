package springproject.project.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenValidator implements
        ConstraintValidator<TokenConstraint, String> {

    private String auth = "this-is-token";

    @Override
    public void initialize(TokenConstraint token) {

    }

    @Override
    public boolean isValid(String token,
                           ConstraintValidatorContext cxt) {
        return token.equals(auth);
    }
}
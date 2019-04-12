import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@FacesValidator("mailValidator")
public class MailValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        String mail = value.toString();
        try {
            InternetAddress internetAddress = new InternetAddress(mail);
            internetAddress.validate();
        } catch (AddressException e) {
            FacesMessage msg =
                    new FacesMessage(mail + " to nie jest poprawny e-mail!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }
}
package tp.eni_store.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocaleHelper {

    @Autowired
    MessageSource messageSource;

    public String i18n(String key){
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
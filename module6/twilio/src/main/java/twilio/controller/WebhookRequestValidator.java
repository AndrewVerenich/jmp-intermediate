package twilio.controller;

import com.twilio.security.RequestValidator;
import org.springframework.stereotype.Component;
import twilio.config.TwilioProperties;

import java.util.Map;

@Component
public class WebhookRequestValidator
{
    public static final String X_TWILIO_SIGNATURE = "x-twilio-signature";
    public static final String HOST = "host";
    public static final String X_FORWARDED_PROTO = "x-forwarded-proto";
    private final RequestValidator requestValidator = new RequestValidator(TwilioProperties.AUTH_TOKEN);

    public void validateRequest(Map<String, String> params, Map<String, String> headers)
    {
        String twilioSignatureValue = headers.getOrDefault(X_TWILIO_SIGNATURE, "");
        boolean validationResult = requestValidator.validate(getWebhookUrl(headers), params,
                twilioSignatureValue);
        if (!validationResult)
        {
            throw new RequestValidationException("Request is invalid!");
        }
    }

    private String getWebhookUrl(Map<String, String> headers)
    {
        String twilioHost = headers.getOrDefault(HOST, "");
        String twilioProtocolHeader = headers.getOrDefault(X_FORWARDED_PROTO, "");
        return twilioProtocolHeader + "://" + twilioHost + "/";
    }
}

package twilio.controller;

public class RequestValidationException extends RuntimeException
{
    public RequestValidationException(String message)
    {
        super(message);
    }
}

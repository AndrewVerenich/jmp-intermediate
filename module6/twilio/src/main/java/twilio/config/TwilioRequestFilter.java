package twilio.config;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class TwilioRequestFilter implements Filter
{

    public static final String EVENT_TYPE_PARAM_KEY = "EventType";
    public static final String WORKER_ACTIVITY_EVENT_TYPE = "worker.activity.update";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException
    {
        String contentType = request.getContentType();
        String requestEventType = getRequestParameterValue(request, EVENT_TYPE_PARAM_KEY);
        if (!StringUtils.isEmpty(contentType)
                && contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                && !WORKER_ACTIVITY_EVENT_TYPE.equals(requestEventType))
        {
            return;
        }
        chain.doFilter(request, response);
    }

    private String getRequestParameterValue(ServletRequest request, String paramKey)
    {
        return Arrays.stream(request.getParameterMap().getOrDefault(paramKey, new String[]{}))
                .filter(value -> !StringUtils.isEmpty(value))
                .findFirst().orElse("");
    }
}

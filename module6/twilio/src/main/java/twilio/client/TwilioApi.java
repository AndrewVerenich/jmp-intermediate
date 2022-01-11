package twilio.client;

import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.taskrouter.v1.Workspace;
import com.twilio.rest.taskrouter.v1.WorkspaceFetcher;
import com.twilio.rest.taskrouter.v1.workspace.Event;
import com.twilio.rest.taskrouter.v1.workspace.EventFetcher;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;

@Configuration
@AllArgsConstructor
@EnableRetry
public class TwilioApi
{
    private TwilioRestClient restClient;

    @Retryable(value = ApiException.class, maxAttempts = 5)
    public Event fetchEvent(String workspaceSid, String eventSid)
    {
        return new EventFetcher(workspaceSid, eventSid).fetch(restClient);
    }

    public Workspace fetchWorkspace(String workspaceSid)
    {
        return new WorkspaceFetcher(workspaceSid).fetch(restClient);
    }
}

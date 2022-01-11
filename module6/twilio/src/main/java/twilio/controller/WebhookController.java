package twilio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.rest.taskrouter.v1.workspace.Event;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twilio.client.TwilioApi;
import twilio.model.AgentActivityEvent;
import twilio.service.activity.AgentActivityProcessor;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class WebhookController
{
    private WebhookRequestValidator requestValidator;
    private AgentActivityProcessor agentActivityProcessor;
    private TwilioApi twilioApi;
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public ResponseEntity<Void> postWebhook(@RequestParam Map<String, String> params, @RequestHeader Map<String,
//            String> headers)
//    {
//        requestValidator.validateRequest(params, headers);
//
//        String eventType = params.getOrDefault("EventType", "");
//        String workspaceSid = params.getOrDefault("WorkspaceSid", "");
//        String eventSid = params.getOrDefault("Sid", "");
//
//        if (!StringUtils.isEmpty(eventType) && eventType.equals("worker.activity.update"))
//        {
//            Event event = twilioApi.fetchEvent(workspaceSid, eventSid);
//            log.info(event.toString());
//        }
//        return getResponseEntity();
//    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> postWebhook(@RequestParam Map<String, String> params, @RequestHeader Map<String,
            String> headers)
    {
        requestValidator.validateRequest(params, headers);
        AgentActivityEvent event = mapToAgentActivityEvent(params);
        log.info(event.toString());
        agentActivityProcessor.process(event);
        return getResponseEntity();
    }

    @GetMapping("/h2-console")
    public String h2()
    {
        return "You are in H2 database";
    }

    private ResponseEntity<Void> getResponseEntity()
    {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    private AgentActivityEvent mapToAgentActivityEvent(Map<String, String> params)
    {
        return objectMapper.convertValue(params, AgentActivityEvent.class);
    }
}

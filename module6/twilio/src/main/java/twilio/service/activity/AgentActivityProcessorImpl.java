package twilio.service.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twilio.model.AgentActivityEvent;

@Component
@Slf4j
public class AgentActivityProcessorImpl implements AgentActivityProcessor
{
    @Override
    public void process(AgentActivityEvent event)
    {
        log.info("Processing agent activity event {}", event);
    }
}

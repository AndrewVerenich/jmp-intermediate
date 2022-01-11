package twilio.service.activity;

import twilio.model.AgentActivityEvent;

public interface AgentActivityProcessor
{
    void process(AgentActivityEvent event);
}

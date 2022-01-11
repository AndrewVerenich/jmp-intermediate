package twilio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AgentActivityEvent
{
    @JsonProperty("WorkerActivityName")
    String workerActivityName;
    @JsonProperty("EventType")
    String eventType;
    @JsonProperty("ResourceType")
    String resourceType;
    @JsonProperty("WorkerTimeInPreviousActivityMs")
    String workerTimeInPreviousActivityMs;
    @JsonProperty("Timestamp")
    String timestamp;
    @JsonProperty("WorkerActivitySid")
    String workerActivitySid;
    @JsonProperty("WorkerPreviousActivitySid")
    String workerPreviousActivitySid;
    @JsonProperty("WorkerTimeInPreviousActivity")
    String workerTimeInPreviousActivity;
    @JsonProperty("AccountSid")
    String accountSid;
    @JsonProperty("WorkerName")
    String workerName;
    @JsonProperty("Sid")
    String sid;
    @JsonProperty("TimestampMs")
    String timestampMs;
    @JsonProperty("WorkerVersion")
    String workerVersion;
    @JsonProperty("WorkerSid")
    String workerSid;
    @JsonProperty("WorkspaceSid")
    String workspaceSid;
    @JsonProperty("WorkspaceName")
    String workspaceName;
    @JsonProperty("WorkerPreviousActivityName")
    String workerPreviousActivityName;
    @JsonProperty("EventDescription")
    String eventDescription;
    @JsonProperty("ResourceSid")
    String resourceSid;
    @JsonProperty("WorkerAttributes")
    String workerAttributes;
}

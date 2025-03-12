package alekssandher.barber_shop_api.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ErrorDetails {
    private final int status;
    private final String title;
    private final String detail;
    private final String type;
    private final String instance;
    private final LocalDateTime timestamp;

    protected ErrorDetails(int status, String title, String detail, String type, String instance) {
        this.status = status;
        this.title = title;
        this.detail = detail;
        this.type = type;
        this.instance = instance;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getInstance() {
        return instance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

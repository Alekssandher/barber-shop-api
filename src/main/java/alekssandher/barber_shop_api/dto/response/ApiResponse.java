package alekssandher.barber_shop_api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiResponse<T> {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("status")
    private final int status;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("detail")
    private final String detail;

    @JsonProperty("instance")
    private final String instance;

    @JsonProperty("data")
    private final T data;

    protected ApiResponse(int status, String type, String title, String detail, String instance, T data) {
        this.status = status;
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.instance = instance;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstance() {
        return instance;
    }

    public T getData() {
        return data;
    }
}

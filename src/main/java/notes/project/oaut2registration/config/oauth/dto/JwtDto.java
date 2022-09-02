package notes.project.oaut2registration.config.oauth.dto;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import liquibase.exception.DatabaseException;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JwtDto {
    private Long exp;
    @SerializedName("user_name")
    private String userName;
    private String[] authorities;
    private String jti;
    @SerializedName("client_id")
    private String clientId;
    private String[] scope;
    private Date expiresAt;
}

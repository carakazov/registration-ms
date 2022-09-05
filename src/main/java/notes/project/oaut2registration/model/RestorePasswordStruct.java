package notes.project.oaut2registration.model;

import javax.persistence.*;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "restore_password_structs")
public class RestorePasswordStruct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private OauthClientDetails details;

    private String restoreCode;

    private String newPassword;

    private Boolean inProcess = Boolean.TRUE;
}

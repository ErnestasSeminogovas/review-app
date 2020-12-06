package lt.reviewapp.entities.user;

import lombok.Getter;
import lombok.Setter;
import lt.reviewapp.entities.common.CommonEntity;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends CommonEntity implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}

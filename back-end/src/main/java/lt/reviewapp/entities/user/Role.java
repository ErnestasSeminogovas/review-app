package lt.reviewapp.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lt.reviewapp.entities.common.CommonEntity;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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

package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import com.usb.pss.ipaservice.inventory.model.entity.Airport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adm_user")
public class User extends BaseAuditorEntity implements UserDetails {

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private String companyCode;
    private String userCode;
    private boolean active;
    private boolean is2faEnabled;
    private LocalDateTime passwordExpiryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
    @ManyToOne(fetch = FetchType.LAZY)
    private PointOfSale pointOfSale;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_user_access_level_mapping",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "access_level_id"))
    private Set<AccessLevel> accessLevels = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_user_airport_mapping",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "airport_id"))
    private Set<Airport> airports = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_user_currency_mapping",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "currency_id"))
    private Set<Currency> allowedCurrencies = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private UserType userType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PersonalInfo personalInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_user_additional_action_permission",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id"))
    private Set<Action> additionalActions = new HashSet<>();

    private Set<Action> getPermittedActionsFromGroup() {
        return getGroup() == null ? new HashSet<>()
            : new HashSet<>(getGroup().getPermittedActions());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Action> permittedActions = getPermittedActionsFromGroup();
        permittedActions.addAll(getAdditionalActions());

        return permittedActions
            .stream()
            .map(action -> new SimpleGrantedAuthority(action.getName()))
            .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

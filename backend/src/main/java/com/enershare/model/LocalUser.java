//package com.enershare.model;
//
//import com.enershare.model.user.User;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.management.relation.Role;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//public class LocalUser extends org.springframework.security.core.userdetails.User implements OAuth2User, OidcUser {
//    private static final long serialVersionUID = -2845160792248762779L;
//    private final OidcIdToken idToken;
//    private final OidcUserInfo userInfo;
//    private Map<String, Object> attributes;
//    private User user;
//    private List<Role> roles;
//
//    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
//                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final User user, List<Role> roles) {
//        this(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, user, null, null, roles);
//    }
//
//    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
//                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final User user, OidcIdToken idToken,
//                     OidcUserInfo userInfo, List<Role> roles) {
//        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//        this.roles = roles;
//        this.user = user;
//        this.idToken = idToken;
//        this.userInfo = userInfo;
//    }
//
//    public static LocalUser create(User user, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
//        LocalUser localUser = new LocalUser(user.getEmail(),
//                user.getPassword(),
//                user.isEnabled(), true, true, true,
//                GeneralUtils.buildSimpleGrantedAuthorities(user.getRolesSet()),
//                user, idToken, userInfo, user.getRoles());
//        localUser.setAttributes(attributes);
//        return localUser;
//    }
//
//    @Override
//    public String getName() {
//        return this.user.getUsername();
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return this.attributes;
//    }
//
//    public void setAttributes(Map<String, Object> attributes) {
//        this.attributes = attributes;
//    }
//
//    @Override
//    public Map<String, Object> getClaims() {
//        return this.attributes;
//    }
//
//    @Override
//    public OidcUserInfo getUserInfo() {
//        return this.userInfo;
//    }
//
//    @Override
//    public OidcIdToken getIdToken() {
//        return this.idToken;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public List<Role> getRoles() {
//        return roles;
//    }
//
//}

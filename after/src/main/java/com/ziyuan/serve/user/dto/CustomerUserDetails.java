package com.ziyuan.serve.user.dto;


import com.ziyuan.serve.user.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author 70913
 * @version 1.0
 * @Description 2024/7/16
 */
public class CustomerUserDetails implements UserDetails {
    private final Customer customer;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;

    public CustomerUserDetails(Customer customer, Collection<? extends GrantedAuthority> authorities) {
        this(customer, true, true, true, authorities);
    }

    public CustomerUserDetails(Customer customer, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, Collection<? extends GrantedAuthority> authorities) {
        this.customer = customer;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回用户的权限列表
        return Collections.emptyList(); // 示例中使用空列表，实际应用中应根据用户角色返回权限
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getMail();
    }

    public int getUserId() {
        return customer.getId();
    }

    public Customer getCustomer(){
        return customer;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return customer.isEnable();
    }


    // 可以添加其他与Customer相关的getter方法，以便在其他地方使用Customer的信息
}

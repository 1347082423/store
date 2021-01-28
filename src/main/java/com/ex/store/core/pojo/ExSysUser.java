package com.ex.store.core.pojo;

import com.ex.store.core.dto.MenuDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ExSysUser implements UserDetails {

  private String name;
  private Long id;
  private String username;
  private String password;
  private String phone;
  private String address;
  private String email;
  private Collection<? extends GrantedAuthority> authorities;
  private boolean enabled;
  private Long isForbid;
  private List<MenuDto> menus;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
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
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Long getIsForbid() {
    return isForbid;
  }

  public void setIsForbid(Long isForbid) {
    this.isForbid = isForbid;
  }

  public List<MenuDto> getMenus() {
    return menus;
  }

  public void setMenus(List<MenuDto> menus) {
    this.menus = menus;
  }
}

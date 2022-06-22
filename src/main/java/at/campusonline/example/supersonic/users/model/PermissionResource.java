package at.campusonline.example.supersonic.users.model;

public class PermissionResource {

  private String permission;
  private boolean allowed;

  public PermissionResource() {
  }

  public PermissionResource(String permission, boolean allowed) {
    this.permission = permission;
    this.allowed = allowed;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public boolean isAllowed() {
    return allowed;
  }

  public void setAllowed(boolean allowed) {
    this.allowed = allowed;
  }
}

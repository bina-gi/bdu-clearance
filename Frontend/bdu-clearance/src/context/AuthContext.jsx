import { createContext, useState, useEffect, useMemo } from "react";
import authService from "../services/authService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const currentUser = authService.getCurrentUser();
    setUser(currentUser);
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    try {
      const data = await authService.login(username, password);
      setUser(data);
      return data;
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  const isAuthenticated = !!user;

  // Derived user info
  // The backend LoginResponse: { username, roles[], jwt, expiresAt }
  // We assume roles is an array like ["ROLE_STUDENT"] or ["ROLE_STAFF"]
  // Organizational unit info would need to come from a /me or /profile endpoint
  // For now, we'll parse roles and provide a placeholder for orgUnit

  const userInfo = useMemo(() => {
    if (!user)
      return {
        role: null,
        roles: [],
        organizationalUnit: null,
        fullName: null,
      };

    const roles = user.roles || [];
    // Get primary role (remove ROLE_ prefix if present)
    const primaryRole = roles.length > 0 ? roles[0].replace("ROLE_", "") : null;

    return {
      role: primaryRole,
      roles: roles.map((r) => r.replace("ROLE_", "")),
      organizationalUnit: user.organizationalUnit || null,
      organizationalUnitType: user.organizationalUnitType || null,
      fullName: user.fullName || user.username,
      username: user.username,
    };
  }, [user]);

  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        logout,
        isAuthenticated,
        loading,
        ...userInfo,
      }}
    >
      {!loading && children}
    </AuthContext.Provider>
  );
};

export default AuthContext;

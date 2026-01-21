import api from "./api";

const login = async (username, password) => {
  const response = await api.post("/auth/signin", {
    username,
    password,
  });

  console.log("Login API Response:", response); // Debug log

  if (response.data.accessToken) {
    localStorage.setItem("user", JSON.stringify(response.data));
    console.log("User stored in localStorage:", response.data); // Debug log
  } else {
    console.warn("No accessToken found in response:", response.data);
  }

  return response.data;
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  try {
    const userStr = localStorage.getItem("user");
    if (!userStr) return null;

    const user = JSON.parse(userStr);

    // Check if token is expired
    if (user.expiresAt && Date.now() > user.expiresAt) {
      localStorage.removeItem("user");
      return null;
    }

    return user;
  } catch (error) {
    console.error("Error reading user from localStorage:", error);
    localStorage.removeItem("user");
    return null;
  }
};

const authService = {
  login,
  logout,
  getCurrentUser,
};

export default authService;

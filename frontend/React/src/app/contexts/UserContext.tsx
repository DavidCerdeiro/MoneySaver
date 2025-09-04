import { createContext, useContext, useState, useEffect, type ReactNode } from "react";
import { fetchWithRefresh } from "../domains/auth/application/authService";
const API_URL = import.meta.env.VITE_API_URL;
type UserContextType = {
  isAuthenticated: boolean | null; // null = loading
  setIsAuthenticated: (auth: boolean) => void;
  checkAuth: () => Promise<void>; // <- añadimos checkAuth
};

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

  const checkAuth = async () => {
    try {
      const res = await fetchWithRefresh(`${API_URL}/api/auth/sessions/me`, {
        credentials: "include",
      });
      setIsAuthenticated(res.ok);
    } catch (err) {
      console.error("Autenticación fallida:", err);
      setIsAuthenticated(false);
    }
  };

  useEffect(() => {
    checkAuth();
  }, []);

  return (
    <UserContext.Provider value={{ isAuthenticated, setIsAuthenticated, checkAuth }}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => {
  const ctx = useContext(UserContext);
  if (!ctx) throw new Error("useUser must be used inside UserProvider");
  return ctx;
};

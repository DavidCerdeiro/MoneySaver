import React, { createContext, useContext, useState, useEffect } from "react";

export type User = {
  id: number;
  name: string;
  surname: string;
  email: string;
  isAuthenticated: boolean;
};

type UserContextType = {
  user: User | null;
  setUser: (user: User | null) => void;
  logout: () => void;
};

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUserState] = useState<User | null>(null);

  // Cargar user de localStorage al montar
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUserState(JSON.parse(storedUser));
    }
  }, []);

  // Función para actualizar user y guardar en localStorage
  const setUser = (user: User | null) => {
    setUserState(user);
    if (user) {
      localStorage.setItem("user", JSON.stringify(user));
    } else {
      localStorage.removeItem("user");
    }
  };

  // Logout: limpiar usuario y localStorage
  const logout = () => {
    setUser(null);
  };

  return (
    <UserContext.Provider value={{ user, setUser, logout }}>
      {children}
    </UserContext.Provider>
  );
};

// Hook para usar UserContext
export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) throw new Error("useUser debe usarse dentro de <UserProvider>");
  return context;
};

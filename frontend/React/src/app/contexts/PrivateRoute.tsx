import { Navigate, Outlet } from "react-router-dom";
import { useUser } from "../contexts/UserContext";

export const PrivateRoute = () => {
  const { isAuthenticated } = useUser();

  if (isAuthenticated === null) {
    return <p>Cargando...</p>; // 👈 mientras comprueba sesión
  }

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

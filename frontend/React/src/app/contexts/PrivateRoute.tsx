import { Navigate, Outlet } from "react-router-dom";
import { useUser } from "../contexts/UserContext";
import ClipLoader from "react-spinners/ClipLoader";

export const PrivateRoute = () => {
  const { isAuthenticated } = useUser();

  if (isAuthenticated === null) {
    return (
      <div className="fixed inset-0 flex items-center justify-center bg-black z-50">
        <ClipLoader color="#fff" size={60} />
      </div>
    );
  }

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

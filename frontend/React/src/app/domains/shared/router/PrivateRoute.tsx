import { useUser } from "@/app/contexts/UserContext";
import { Navigate } from "react-router-dom";
import type { ReactNode } from "react";

type PrivateRouteProps = {
  children: ReactNode;
};

export function PrivateRoute({ children }: PrivateRouteProps) {
  const { user } = useUser();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}

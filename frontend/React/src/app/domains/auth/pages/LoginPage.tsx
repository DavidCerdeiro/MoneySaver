import { LoginForm } from "../components/LoginForm";
import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";

export function LoginPage() {
  return (
    <AuthPageLayout>
      <LoginForm />
    </AuthPageLayout>
  );
}

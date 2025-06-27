import { ForgotPasswordForm } from "../components/ForgotPasswordForm";
import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";

export function ForgotPasswordPage() {
  return (
    <AuthPageLayout>
      <ForgotPasswordForm />
    </AuthPageLayout>
  );
}

import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";
import { SignUpForm } from "../components/SignUpForm";

export function SignUpPage() {
  return (
    <AuthPageLayout>
      <SignUpForm />
    </AuthPageLayout>
  );
}

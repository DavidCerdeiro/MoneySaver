import { useLocation } from "react-router-dom";
import { VerificationCodeForm } from "../components/VerificationCodeForm";
import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";

export function VerificationEmailPage() {
  const { pathname } = useLocation();

  let source: "login" | "signup" | "forgot" = "signup";
  if (pathname.includes("login")) source = "login";
  else if (pathname.includes("forgot")) source = "forgot";

  return (
    <AuthPageLayout>
      <VerificationCodeForm source={source} />
    </AuthPageLayout>
  );
}

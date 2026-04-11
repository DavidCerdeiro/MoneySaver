import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";
import { SignUpForm } from "../components/SignUpForm";
import { useTranslation } from "react-i18next";

export function SignUpPage() {
  const { t } = useTranslation();

  return (
    <AuthPageLayout>
      <p className="page-description">{t('app.WaitingDisclaimer')}</p>
      <SignUpForm />
    </AuthPageLayout>
  );
}

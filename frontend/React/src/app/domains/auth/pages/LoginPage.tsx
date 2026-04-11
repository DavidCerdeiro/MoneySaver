import { useTranslation } from "react-i18next";
import { LoginForm } from "../components/LoginForm";
import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";

export function LoginPage() {
  const { t } = useTranslation();
  return (
    <AuthPageLayout>
      <p className="page-description">{t('app.WaitingDisclaimer')}</p>
      <LoginForm />
    </AuthPageLayout>
  );
}

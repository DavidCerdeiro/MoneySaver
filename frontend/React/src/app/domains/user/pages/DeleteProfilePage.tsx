import { useTranslation } from "react-i18next";
import { VerificationCodeForm } from "../../auth/components/VerificationCodeForm";
import { AuthPageLayout } from "../../shared/layouts/AuthPageLayout";
import { Link } from "react-router-dom";

export function DeleteProfilePage() {
    const { t } = useTranslation();
    return (
        <AuthPageLayout>
            <VerificationCodeForm source="delete-profile" />
            <Link to="/user/profile/edit" className="text-link">{t('domains.user.delete.backtoProfile')}</Link>
        </AuthPageLayout>
    );
}

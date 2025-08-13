import { VerificationCodeForm } from "../../auth/components/VerificationCodeForm";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";

export function DeleteProfilePage() {
    return (
        <DefaultPageLayout>
            <VerificationCodeForm source="delete-profile" />
        </DefaultPageLayout>
    );
}

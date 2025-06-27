
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createResetPasswordSchema } from "../schemas/ResetPassword";
import type { ResetPasswordData } from "../schemas/ResetPassword";
import { resetPassword } from '../application/AuthService';
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from "@/app/domains/shared/components/card.js";;
import { Input } from "@/app/domains/shared/components/input.js";
import { Button } from "@/app/domains/shared/components/button.js";;
import { Label } from "@/app/domains/shared/components/label.js";
import { useEffect } from 'react';
import { toast } from "sonner";
import { AuthPageLayout } from "@/app/domains/shared/layouts/AuthPageLayout";

export function ResetPasswordForm() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const { i18n } = useTranslation();
    // Retrieve the email from localStorage, which was set during the forgot password process
    const email = localStorage.getItem('otpEmail') || "";
    // If email is not set, redirect to the home page
    // This is to ensure that the user has an email set before proceeding with verification.
    useEffect(() => {
            if (!email) {
                navigate("/");
            }
        }, [email, navigate]);
    const schema = createResetPasswordSchema(t);
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm({
        resolver: zodResolver(schema),
    });

    const onSubmit = async (formData: ResetPasswordData) => {
            try {
                const languageTag = i18n.language || "en";
                const [language, country = ""] = languageTag.split("-");
                // Ensure the email is included in the request body
                const requestBody: ResetPasswordData = {
                    ...formData,
                    email: `${email}`,
                    locale: `${language}_${country}`,
                };
                await resetPassword(requestBody);
                
                toast.success(t('resetPassword.success'));
                navigate('/login');
            } catch (error) {
                toast.error(t('resetPassword.error'));
            }
        };

    return (
        <AuthPageLayout>
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('resetPassword.title')}</CardTitle>
                    <CardDescription className="card-description">{t('resetPassword.description')}</CardDescription>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="newPassword">{t('resetPassword.newPassword')}</Label>
                            <Input id="password" type="password" {...register("newPassword")} className="input-dark" />
                            {errors.newPassword && <p className="text-red-500 text-sm">{errors.newPassword.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="confirmPassword">{t('resetPassword.confirmNewPassword')}</Label>
                            <Input id="confirmPassword" type="password" {...register("confirmPassword")} className="input-dark" />
                            {errors.confirmPassword && <p className="text-red-500 text-sm">{errors.confirmPassword.message}</p>}
                        </div>
                            <Button type="submit" className="button-green" disabled={isSubmitting}>
                                {t('resetPassword.submit')}
                            </Button>
                    </form>
                </CardContent>
                <CardFooter className="flex justify-center">
                </CardFooter>
            </Card>
        </AuthPageLayout>
    )
}
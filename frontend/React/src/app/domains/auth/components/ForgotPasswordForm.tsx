import '@/styles/utilities.css';

import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/components/card";
import { Input } from "@/app/domains/shared/components/input";
import { Button } from "@/app/domains/shared/components/button";
import { Label } from "@/app/domains/shared/components/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createForgotPasswordSchema } from "../schemas/ForgotPassword";
import type { ForgotPasswordData } from "../schemas/ForgotPassword";
import { forgotPassword } from '../application/AuthService';
import { useNavigate } from 'react-router-dom';
import { toast } from "sonner";

export function ForgotPasswordForm() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const { i18n } = useTranslation();

    const schema = createForgotPasswordSchema(t);
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<ForgotPasswordData>({
        resolver: zodResolver(schema),
    });
    const onSubmit = async (formData: ForgotPasswordData) => {
        try {
            // Extract the language and country from the i18n language tag with the aim of sending the verification email in the user's preferred language.
            const languageTag = i18n.language || "en";
            const [language, country = ""] = languageTag.split("-");
            const requestBody: ForgotPasswordData = {
                ...formData,
                locale: `${language}_${country}`,
            };

            await forgotPassword(requestBody);
            //In the next direction, we'll need the email, so we store it in localStorage.
            localStorage.setItem('forgotEmail', formData.email);
            navigate('/forgot-password/authUser');
        } catch (error) {
            toast.error(t('forgotPassword.error'));
            console.error("Forgot password error:", error);
        }
    };

    return (
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('forgotPassword.title')}</CardTitle>
                    <CardDescription className="card-description">{t('forgotPassword.description')}</CardDescription>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('domains.user.email')}</Label>
                            <Input id="email" type="email" {...register("email")} className="input-dark"/>
                            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
                        </div>
                            <Button type="submit" className="button-green" disabled={isSubmitting}>
                                {t('forgotPassword.submit')}
                            </Button>
                    </form>
                </CardContent>
                <CardFooter className="flex justify-center">
                    <CardAction>
                        <Link to="/login" className="back-link">{t('forgotPassword.backToLogin')}</Link>
                    </CardAction>
                </CardFooter>
            </Card>
    )
}
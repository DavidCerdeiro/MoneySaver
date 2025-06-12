import '@/styles/utilities.css';

import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/card";
import { Input } from "@/app/domains/shared/input.js";
import { Button } from "@/app/domains/shared/button.js";
import { Label } from "@/app/domains/shared/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createForgotPasswordSchema } from "../schemas/forgotpassword";
import type { ForgotPasswordData } from "../schemas/forgotpassword";
import { forgotPassword } from '../application/authService';

export function ForgotPasswordForm() {
    const { t } = useTranslation();

    const schema = createForgotPasswordSchema(t);
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm({
        resolver: zodResolver(schema),
    });
    const onSubmit = async (data: ForgotPasswordData) => {
            try {
                const result = await forgotPassword(data);
                console.log("Success in login", result);
            } catch (error) {
                console.error("Login error:", error);
            }
        };

    return (
        <div className="form-background">
            <h1 className="card-title">{t('appName')}</h1>
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('forgotPassword.title')}</CardTitle>
                    <CardDescription className="card-description">{t('forgotPassword.description')}</CardDescription>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('user.email')}</Label>
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
    </div>
    )
}